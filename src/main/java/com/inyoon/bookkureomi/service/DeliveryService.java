package com.inyoon.bookkureomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Delivery;
import com.inyoon.bookkureomi.mapper.DeliveryMapper;

@Service
public class DeliveryService {
	@Autowired
	private DeliveryMapper deliveryMapper;

	public void addDelivery(Delivery delivery){
		deliveryMapper.addDelivery(delivery);
	}
	public Delivery getDelivery(int orderNo){
		return deliveryMapper.getDelivery(orderNo);
	}

}
