package com.inyoon.bookkureomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Order;

@Mapper
public interface OrderMapper {
	public void orderBook(Order order);
	public List<Order> getOrderList(int userNo);
	public Order getOrder(int orderNo);
}
