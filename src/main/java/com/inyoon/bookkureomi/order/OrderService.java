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

	public void orderSale(Order order){
		orderMapper.orderSale(order);
	}
	public void orderAuction(Order order){
		orderMapper.orderAuction(order);
	}
	public List<Order> getSaleOrderList(int userNo){
		return orderMapper.getSaleOrderList(userNo);
	}
	public List<Order> getAuctionOrderList(int userNo){
		return orderMapper.getAuctionOrderList(userNo);
	}
	public Order getSaleOrder(int orderNo){
		return orderMapper.getSaleOrder(orderNo);
	}
	public Order getAuctionOrder(int orderNo){
		return orderMapper.getAuctionOrder(orderNo);
	}	
	public Order getOrderBySale (int saleNo) {
		return orderMapper.getOrderBySale(saleNo);
	}

}
