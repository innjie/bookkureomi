package com.inyoon.bookkureomi.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Delivery;

@Mapper
public interface DeliveryMapper {
	public void addDelivery(Delivery delivery);
	public Delivery getDelivery(int orderNo);
}
