package com.inyoon.bookkureomi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "주문상세")
@Getter
@Setter
public class OrderDetail {
	@ApiModelProperty(value = "주문상세번호", dataType = "int")
	private int odNo;
	@ApiModelProperty(value = "판매정보", dataType = "Sale")
	private Sale sale;
	@ApiModelProperty(value = "경매정보", dataType = "Auction")
	private Auction auction;
	@ApiModelProperty(value = "주문정보", dataType = "Order")
	private Order order;
}
