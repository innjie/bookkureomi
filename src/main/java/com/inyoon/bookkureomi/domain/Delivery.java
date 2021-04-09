package com.inyoon.bookkureomi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "배송")
@Getter
@Setter
public class Delivery {
	@ApiModelProperty(value = "주문정보", dataType = "Order")
	private Order order;
	@ApiModelProperty(value = "택배사", dataType = "String")
	private String company;
	@ApiModelProperty(value = "송장번호", dataType = "String")
	private String waybill;
}
