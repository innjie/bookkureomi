package com.inyoon.bookkureomi.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Order;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	public int getOrderNo() {
		return orderMapper.getOrderNo();
	}

	public void orderBook(Order order){
		orderMapper.orderBook(order);
	}
	public List<Order> getOrderList(int userNo){
		return orderMapper.getOrderList(userNo);
	}
	public Order getOrder(int orderNo){
		return orderMapper.getOrder(orderNo);
	}

}
