package com.inyoon.bookkureomi.delivery;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Delivery;

@Mapper
public interface DeliveryMapper {
	public void addDelivery(Delivery delivery);
	public void updateDelivery(Delivery delivery);
	public Delivery getDelivery(int orderNo);
}
