package com.inyoon.bookkureomi.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inyoon.bookkureomi.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.Delivery;
import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.sale.SaleService;
import com.inyoon.bookkureomi.user.MyAuthentication;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/book")
@Api(value = "OrderController", description = "주문 API")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SaleService saleService;
	@Autowired
	private PointService pointService;
	@Autowired
	private DeliveryService deliveryService;
	
	
	@ApiOperation(value="나의 주문 화면 이동", notes="나의 주문 화면으로 이동한다.")
	@GetMapping("/order/view")
    public String viewOrder() {	
        return "mypage/order";
    }
	
	//order에 추가, orderDetail에 추가, 포인트 판매 세팅, 포인트 사용 내역 추가, state 변경
	@ApiOperation(value="중고 책 구매 ", notes="중고거래 책을 구매한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/order/create")
	public Map<String, Object> createOrder(
			@RequestParam("saleNoList[]") ArrayList<Integer> saleNoList,
			@RequestParam("pAddress") String pAddress,
			@RequestParam("rName") String rName,
			@RequestParam("rPhone") String rPhone,
			@RequestParam("rAddress") String rAddress,
			@AuthenticationPrincipal Login principal) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = (User) principal.getUser();
			int userNo = user.getUserNo();
		
			boolean isCart;
			
			int orderNo = orderService.getOrderNo();
			int odNo = orderService.getODNo();
		
			int nowPoint = pointService.checkPoint(user.getUserNo());	//현재 포인트 확인
			int salePrice = 0;

			List<Sale> saleList = new ArrayList<>();
			if(saleNoList.size() == 1) {	//일반 주문
				isCart = false;
				
				Sale sale = new Sale();
				sale = saleService.getSale(saleNoList.get(0));
				
				saleList.add(sale);
				
				salePrice = sale.getSalePrice();
			} else{		//카트 주문
				isCart = true;
				
				for(int i = 0; i < saleNoList.size(); i++) {
					Sale sale = new Sale();
					sale = saleService.getSale(saleNoList.get(i));
					
					saleList.add(sale);
					
					salePrice += sale.getSalePrice();
				}
			}
			
			//point와 금액 비교 후 주문	
			if(nowPoint < salePrice) {		
				map.put("result", "fail");
				map.put("reason", "※주문실패※\n주문 금액보다 충전된 포인트가 적습니다.");
			} else {		
				//주문
				Order order = new Order();
				order.setOrderNo(orderNo);
				order.setPAddress(pAddress);
				order.setRName(rName);
				order.setRPhone(rPhone);
				order.setRAddress(rAddress);
				order.setTotal(salePrice);
				order.setUser(user);
				//order info
				if(saleList.size() == 1) {
					order.setInfo(saleList.get(0).getTitle());
				}else {
					order.setInfo(saleList.get(0).getTitle() + " 외 " + String.valueOf(saleList.size()-1) + "권");
				}
				
				
				//주문 상세
				List<OrderDetail> orderDetailList = new ArrayList<>();
				if(saleList.size() == 1) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOdNo(odNo);
					orderDetail.setOrder(order);
					orderDetail.setSale(saleList.get(0));
					
					orderDetailList.add(orderDetail);
				}else {
					for(int i = 0; i < saleList.size(); i++) {
						OrderDetail orderDetail = new OrderDetail();
						orderDetail.setOdNo(odNo+i);
						orderDetail.setOrder(order);
						orderDetail.setSale(saleList.get(i));
						
						orderDetailList.add(orderDetail);
					}
				}

				
				//충전 - 이용
				Recharge rechargeUsing = new Recharge();
				rechargeUsing.setRechargeNo(pointService.getRechargeNo(userNo));
				rechargeUsing.setTotalPoint(nowPoint - salePrice);	
				rechargeUsing.setRcType("using");
				rechargeUsing.setRcPoint(-1 * salePrice);
				rechargeUsing.setUser(user);
				
				
				//충전 - 판매
				List<Recharge> rechargeSellingList = new ArrayList<>();
				if(saleList.size() == 1) {	//일반 주문
					int sellerNo = saleList.get(0).getUser().getUserNo();
					int saleUserPoint = pointService.checkPoint(sellerNo);
					User seller = new User();
					seller.setUserNo(sellerNo);
					Recharge rechargeSelling = new Recharge();
					rechargeSelling.setTotalPoint(saleUserPoint + (int)(0.9 * salePrice));			
					rechargeSelling.setRechargeNo(pointService.getRechargeNo(sellerNo));
					rechargeSelling.setRcType("recharging");
					rechargeSelling.setRcMethod("selling");
					rechargeSelling.setRcPoint((int)(0.9 * salePrice));
					rechargeSelling.setUser(seller);
					
					rechargeSellingList.add(rechargeSelling);
				} else{		//카트 주문
					Map<Integer, Integer> rechargeMap = new HashMap<>();
					Map<Integer, Integer> pointMap = new HashMap<>();
					
					for(int i = 0; i < saleList.size(); i++) {
						int sellerNo = saleList.get(i).getUser().getUserNo();
						int saleUserPoint = pointService.checkPoint(sellerNo);
						
						//RechargeNo
						if(rechargeMap.containsKey(sellerNo)) {
							rechargeMap.replace(sellerNo, pointService.getRechargeNo(sellerNo)+1);
						} else {
							rechargeMap.put(sellerNo, pointService.getRechargeNo(sellerNo));
						}
						
						//totalPoint
						if(pointMap.containsKey(sellerNo)) {
							pointMap.replace(sellerNo, pointMap.get(sellerNo) + (int)(0.9 * saleList.get(i).getSalePrice()));
						} else {
							pointMap.put(sellerNo, pointService.checkPoint(sellerNo) + (int)(0.9 * saleList.get(i).getSalePrice()));
						}
						
						User seller = new User();
						seller.setUserNo(sellerNo);
						Recharge rechargeSelling = new Recharge();
						rechargeSelling.setRcType("recharging");
						rechargeSelling.setRcMethod("selling");
						rechargeSelling.setRcPoint((int)(0.9 * saleList.get(i).getSalePrice()));
						rechargeSelling.setUser(seller);
						rechargeSelling.setRechargeNo(rechargeMap.get(sellerNo));
						//rechargeSelling.setTotalPoint(saleUserPoint + (int)(0.9 * saleList.get(i).getSalePrice()));
						rechargeSelling.setTotalPoint(pointMap.get(sellerNo));
						
						rechargeSellingList.add(rechargeSelling);
					}
				}
				
				orderService.orderSale(orderDetailList, rechargeUsing, rechargeSellingList, isCart);	//order 추가

				map.put("result", "success");
				map.put("order", order);
				map.put("totalPoint", rechargeUsing.getTotalPoint());
			}
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
    }
	
	@ApiOperation(value="나의 주문 목록", notes="나의 주문 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/order/list")
	public Map<String, Object> listOrder(
			@RequestParam("type") String type,
			@RequestParam("pageNo") int pageNo,
			@AuthenticationPrincipal Login principal) {
	
		int showCnt = 10;	//보여주는 개수
		int orderCnt = 0;	//리스트 개수
		int pageCnt = 0;
		
		List<Order> orderList = new ArrayList<>();	
	
		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = (User) principal.getUser();
			int userNo = user.getUserNo();

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userNo", userNo);		
			if(type.equals("sale")) {
				paramMap.put("type", "sale");	
			} else if(type.equals("auction")){
				paramMap.put("type", "auction");	
			}		
			orderCnt = orderService.countOrderList(paramMap);
			
			if(orderCnt > 0) {
				pageCnt = (orderCnt % showCnt == 0) ? (orderCnt / showCnt) : (orderCnt / showCnt + 1);		//페이지 개수
				int start = 1+(showCnt*(pageNo-1));
				int end = showCnt+(showCnt*(pageNo-1));
				
				paramMap.put("start", start);
				paramMap.put("end", end);					
				
				orderList = orderService.getOrderList(paramMap);
			}
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderList", orderList);
		map.put("orderCnt", orderCnt);
		map.put("pageCnt", pageCnt);
		
        return map;
    }
	
	@ApiOperation(value="주문 상세 보기", notes="주문을 상세히 본다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/order/detail")
	public Map<String, Object> detailOrder(
				@RequestParam("orderNo") int orderNo,
				@RequestParam("type") String type) throws Exception {

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		
		if(type.equals("sale")) {
			orderDetailList = orderService.getSaleOrder(orderNo);
		} else if(type.equals("auction")){
			orderDetailList = orderService.getAuctionOrder(orderNo);
		}
		
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		
		for(int i = 0; i < orderDetailList.size(); i++) {
			deliveryList.add(deliveryService.getDelivery(orderDetailList.get(i).getOdNo()));
		}
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderDetailList", orderDetailList);
		map.put("deliveryList", deliveryList);

        return map;
	}
}
