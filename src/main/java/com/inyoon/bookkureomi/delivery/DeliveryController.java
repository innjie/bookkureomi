package com.inyoon.bookkureomi.delivery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inyoon.bookkureomi.domain.Delivery;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.order.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/book")
@Api(value = "DeliveryController", description = "배송 API")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value="배송 상세 보기", notes="배송 정보를 본다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/delivery/detail")
	public Map<String, Object> getDelivery(
				@RequestParam("odNo") int odNo) throws Exception {

		Delivery delivery = new Delivery();
		
		delivery = deliveryService.getDelivery(odNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delivery", delivery);
		
        return map;
	}
	
	@ApiOperation(value="배송 정보 추가", notes="배송 정보를 추가한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/delivery/create")
	public Map<String, Object> addDelivery(
				@RequestParam("saleNo") int saleNo,
				@RequestParam("company") String company,
				@RequestParam("waybill") String waybill) throws Exception {

		OrderDetail orderDetail = new OrderDetail();
		orderDetail = orderService.getOrderBySale(saleNo);
		
		Delivery delivery = new Delivery();
		
		delivery.setOrderDetail(orderDetail);
		delivery.setCompany(company);
		delivery.setWaybill(waybill);
		
		deliveryService.addDelivery(delivery);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("delivery", delivery);
		
        return map;
	}
	
	@ApiOperation(value="배송 정보 수정", notes="배송 정보를 수정한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PutMapping("/delivery/update")
	public Map<String, Object> updateDelivery(
				@RequestParam("saleNo") int saleNo,
				@RequestParam("company") String company,
				@RequestParam("waybill") String waybill) throws Exception {

		
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail = orderService.getOrderBySale(saleNo);
		
		Delivery delivery = new Delivery();
		
		delivery.setOrderDetail(orderDetail);
		delivery.setCompany(company);
		delivery.setWaybill(waybill);
		
		deliveryService.updateDelivery(delivery);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("delivery", delivery);
		
        return map;
	}
}
