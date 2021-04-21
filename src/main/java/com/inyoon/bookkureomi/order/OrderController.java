package com.inyoon.bookkureomi.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.sale.SaleService;

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

	//order에 추가, 포인트 세팅, 포인트 사용 내역 추가, state 변경
	@ApiOperation(value="중고 책 구매 ", notes="중고거래 책을 구매한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/order/create")
	public Map<String, Object> createOrder(
			@RequestParam("saleNo") int saleNo,
			@RequestParam("pAddress") String pAddress,
			@RequestParam("rName") String rName,
			@RequestParam("rPhone") String rPhone,
			@RequestParam("rAddress") String rAddress) {

		Map<String, Object> map = new HashMap<String, Object>();

		int total = saleService.checkSalePrice(saleNo);
		
		User user = new User();
		//user.setUserNo(1);
		
		Sale sale = new Sale();
		sale = saleService.getSale(saleNo);
				
		Order order = new Order();
		order.setOrderNo(orderService.getOrderNo());
		order.setPAddress(pAddress);
		order.setRName(rName);
		order.setRPhone(rPhone);
		order.setRAddress(rAddress);
		order.setTotal(total);
		order.setInfo(sale.getTitle());
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOdNo(orderService.getODNo());
		orderDetail.setOrder(order);
		orderDetail.setSale(sale);
		
		//1 -> userNo
		Recharge recharge = new Recharge();
		recharge.setRechargeNo(pointService.getRechargeNo(1));
		if(pointService.checkPoint(1) < total) { //point와 금액 비교
			recharge.setTotalPoint(sale.getSalePrice());
			
			map.put("result", "fail");
			map.put("reason", "※주문실패※\n주문 금액보다 충전된 포인트가 적습니다.");
		} else {
			recharge.setTotalPoint(pointService.checkPoint(1) - sale.getSalePrice());
		
			recharge.setRcType("사용");
			recharge.setRcPoint(-1 * sale.getSalePrice());
			//recharge.setUser(user);
			
			//mapper.xml user 수정 필요 (point, order)
			orderService.orderSale(order);
			orderService.orderDetailSale(orderDetail);
			pointService.usePoint(recharge);
			
			map.put("result", "success");
			map.put("order", order);
		}
		
        return map;
    }
	
	@ApiOperation(value="나의 주문 화면 이동", notes="나의 주문 화면으로 이동한다.")
	@GetMapping("/order/view")
    public String viewOrder() {	
        return "mypage/order";
    }
	
	@ApiOperation(value="나의 주문 목록", notes="나의 주문 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/order/list")
	public Map<String, Object> listOrder(
			@RequestParam("type") String type) {

		int userNo = 1;
		
		List<Order> orderList = new ArrayList<>();	
		
		if(type.equals("sale")) {
			orderList = orderService.getSaleOrderList(userNo);
		} else if(type.equals("auction")){
			orderList = orderService.getAuctionOrderList(userNo);
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderList", orderList);
		
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderDetailList", orderDetailList);
		
        return map;
	}
}
