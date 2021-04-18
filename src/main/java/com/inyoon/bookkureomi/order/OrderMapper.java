package com.inyoon.bookkureomi.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Order;

@Mapper
public interface OrderMapper {
	public int getOrderNo();
	public void orderSale(Order order);
	public void updateSaleStateClose(int saleNo);
	public void orderAuction(Order order);
	public List<Order> getSaleOrderList(int userNo);
	public List<Order> getAuctionOrderList(int userNo);
	public Order getSaleOrder(int orderNo);
	public Order getAuctionOrder(int orderNo);
	public Order getOrderBySale (int saleNo);
}
