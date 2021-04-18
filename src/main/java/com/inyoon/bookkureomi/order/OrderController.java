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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/book")
@Api(value = "OrderController", description = "주문 API")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value="주문 화면 이동", notes="주문 화면으로 이동한다.")
	@GetMapping("/order/view")
    public String viewSale() {		
        return "order/order";
    }
	

	@ApiOperation(value="중고 책 구매 ", notes="중고거래 책을 구매한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/order/create")
	public Map<String, Object> listSale() {

		Order order = new Order();
		order.setOrderNo(orderService.getOrderNo());
		
		orderService.orderSale(order);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("order", order);
		
        return map;
    }
	
	@ApiOperation(value="나의 주문 화면 이동", notes="나의 주문 화면으로 이동한다.")
	@GetMapping("/mypage/order/view")
    public String viewMySale() {	
        return "mypage/order";
    }
	
	@ApiOperation(value="나의 주문 목록", notes="나의 주문 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/mypage/order/list")
	public Map<String, Object> listMySale(
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
	@GetMapping("/mypage/order/detail")
	public Map<String, Object> detailSale(
				@RequestParam("orderNo") int orderNo,
				@RequestParam("type") String type) throws Exception {

		Order order = new Order();
		
		if(type.equals("sale")) {
			order = orderService.getSaleOrder(orderNo);
		} else if(type.equals("auction")){
			order = orderService.getAuctionOrder(orderNo);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", order);
		
        return map;
	}
}
