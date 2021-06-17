package com.inyoon.bookkureomi.point;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.point.PointMapper;

@Service
public class PointService {
	@Autowired
	private PointMapper pointMapper;

	//새로운 충전번호 가져오기
	public int getRechargeNo(int userNo) {
		return pointMapper.getRechargeNo(userNo);
	}
	
	//해당 유저번호의 포인트 확인
	public int checkPoint(int userNo){
		return pointMapper.checkPoint(userNo);
	}
	
	//포인트 충전
	public void rechargePoint(Recharge recharge){
		pointMapper.rechargePoint(recharge);
	}
	
	//해당 유저번호의 포인트 이용/충전내역 확인
	public List<Recharge> getRechargeList(int userNo){
		return pointMapper.getRechargeList(userNo);
	}

	
	/*	public int checkHasPoint(int userNo){
		return pointMapper.checkHasPoint(userNo);
	}*/
	
	/*
	//orderService.orderSale에 포함 
	public void usePoint(Recharge recharge){
		pointMapper.usePoint(recharge);
		pointMapper.setPoint(recharge);
	}*/
}
