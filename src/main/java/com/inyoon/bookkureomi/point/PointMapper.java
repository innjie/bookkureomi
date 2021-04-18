package com.inyoon.bookkureomi.point;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Recharge;

@Mapper
public interface PointMapper {
	public int getRechargeNo(int userNo);

	public int checkPoint(int userNo);
	public int checkHasPoint(int userNo);
	public void rechargePoint(Recharge recharge);
	public void usePoint(Recharge recharge);
	public List<Recharge> getRechargeList(int userNo);
}
