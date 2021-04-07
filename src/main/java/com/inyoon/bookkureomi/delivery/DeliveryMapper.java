package com.inyoon.bookkureomi.delivery;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.delivery.Delivery;

@Mapper
public interface DeliveryMapper {
	public void addDelivery(Delivery delivery);
	public Delivery getDelivery(int orderNo);
}
