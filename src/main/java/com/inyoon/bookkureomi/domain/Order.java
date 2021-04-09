package com.inyoon.bookkureomi.domain;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "주문")
@Getter
@Setter
public class Order {
	@ApiModelProperty(value = "주문번호", dataType = "int")
	private int orderNo;
	@ApiModelProperty(value = "판매정보", dataType = "Sale")
	private Sale sale;
	@ApiModelProperty(value = "경매정보", dataType = "Auction")
	private Auction auction;
	@ApiModelProperty(value = "주문자정보", dataType = "User")
	private User user;
	@ApiModelProperty(value = "주문자 주소", dataType = "String")
	private String pAddress;
	@ApiModelProperty(value = "주문일", dataType = "Date")
	private Date orderDate;
	@ApiModelProperty(value = "받는이 주소", dataType = "String")
	private String rAddress;
	@ApiModelProperty(value = "받는이 이름", dataType = "String")
	private String rName;
	@ApiModelProperty(value = "받는이 전화번호", dataType = "String")
	private String rPhone;
}
