package com.inyoon.bookkureomi.point;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.point.PointMapper;

@Service
public class PointService {
	@Autowired
	private PointMapper pointMapper;

	public int getRechargeNo(int userNo) {
		return pointMapper.getRechargeNo(userNo);
	}
	public int checkPoint(int userNo){
		return pointMapper.checkPoint(userNo);
	}
	public void rechargePoint(Recharge recharge){
		pointMapper.rechargePoint(recharge);
	}
	public List<Recharge> getRechargeList(int userNo){
		return pointMapper.getRechargeList(userNo);
	}

}
