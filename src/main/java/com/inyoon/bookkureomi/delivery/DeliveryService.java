package com.inyoon.bookkureomi.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
