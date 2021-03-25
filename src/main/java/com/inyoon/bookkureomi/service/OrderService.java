package com.inyoon.bookkureomi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.mapper.OrderMapper;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

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
