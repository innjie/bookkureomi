package com.inyoon.bookkureomi.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.domain.OrderDetail;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	public int getOrderNo() {
		return orderMapper.getOrderNo();
	}
	public int getODNo() {
		return orderMapper.getODNo();
	}

	public void orderSale(Order order){
		orderMapper.orderSale(order);
	}
	public void orderDetailSale(OrderDetail orderDetail){
		orderMapper.orderDetailSale(orderDetail);
		orderMapper.updateSaleStateClose(orderDetail.getSale().getSaleNo());
	}
	
	
	
	public void orderAuction(Order order){
		orderMapper.orderAuction(order);
	}
	public void orderDetailAuction(OrderDetail orderDetail){
		orderMapper.orderDetailAuction(orderDetail);
	}
	
	
	public List<Order> getSaleOrderList(int userNo){
		return orderMapper.getSaleOrderList(userNo);
	}
	public List<Order> getAuctionOrderList(int userNo){
		return orderMapper.getAuctionOrderList(userNo);
	}
	
	
	public List<OrderDetail> getSaleOrder(int orderNo){
		return orderMapper.getSaleOrder(orderNo);
	}
	public List<OrderDetail> getAuctionOrder(int orderNo){
		return orderMapper.getAuctionOrder(orderNo);
	}	
	public OrderDetail getOrderBySale (int saleNo) {
		return orderMapper.getOrderBySale(saleNo);
	}

}
