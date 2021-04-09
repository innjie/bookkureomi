package com.inyoon.bookkureomi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "장바구니")
public class CartItem {
	@ApiModelProperty(value = "판매정보", dataType = "Sale")
	private Sale sale;
	@ApiModelProperty(value = "사용자(장바구니 주인)", dataType = "User")
	private User user;
}
