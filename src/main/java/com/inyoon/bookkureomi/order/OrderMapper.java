package com.inyoon.bookkureomi.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
	public int getOrderNo();
	public void orderBook(Order order);
	public List<Order> getOrderList(int userNo);
	public Order getOrder(int orderNo);
}
