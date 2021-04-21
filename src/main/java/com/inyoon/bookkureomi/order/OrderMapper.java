package com.inyoon.bookkureomi.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.domain.OrderDetail;

@Mapper
public interface OrderMapper {
	public int getOrderNo();
	public int getODNo();
	
	public void orderSale(Order order);
	public void orderDetailSale(OrderDetail orderDetail);
	public void updateSaleStateClose(int saleNo);

	public void orderAuction(Order order);
	public void orderDetailAuction(OrderDetail orderDetail);
	
	public List<Order> getSaleOrderList(int userNo);
	public List<Order> getAuctionOrderList(int userNo);
	
	public List<OrderDetail> getSaleOrder(int orderNo);
	public List<OrderDetail> getAuctionOrder(int orderNo);
	public OrderDetail getOrderBySale (int saleNo);
}
