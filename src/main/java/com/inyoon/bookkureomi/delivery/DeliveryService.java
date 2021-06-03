package com.inyoon.bookkureomi.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Delivery;

@Service
public class DeliveryService {
	@Autowired
	private DeliveryMapper deliveryMapper;

	//배송 정보 추가
	public void addDelivery(Delivery delivery){
		deliveryMapper.addDelivery(delivery);
	}
	
	//배송 정보 수정
	public void updateDelivery(Delivery delivery){
		deliveryMapper.updateDelivery(delivery);
	}
	
	//해당 주문의 배송 정보 확인
	public Delivery getDelivery(int orderNo){
		return deliveryMapper.getDelivery(orderNo);
	}

}
