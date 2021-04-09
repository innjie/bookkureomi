package com.inyoon.bookkureomi.domain;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "포인트 충전")
@Getter
@Setter
public class Recharge {
	@ApiModelProperty(value = "충전번호", dataType = "int")
	private int rechargeNo;
	@ApiModelProperty(value = "충전하는 유저", dataType = "User")
	private User user;
	@ApiModelProperty(value = "충전 포인트", dataType = "int")
	private int rcPoint;
	@ApiModelProperty(value = "총 포인트", dataType = "int")
	private int totalPoint;
	@ApiModelProperty(value = "충전일", dataType = "Date")
	private Date rcDate;
	@ApiModelProperty(value = "충전방법", dataType = "String")
	private String rcMethod;
}
