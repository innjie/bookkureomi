package com.inyoon.bookkureomi.domain;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "장바구니")
@Getter
@Setter
public class CartItem {
	@ApiModelProperty(value = "판매정보 리스트 (장바구니 아이템들)", dataType = "Sale")
	private Sale sale;
	@ApiModelProperty(value = "사용자(장바구니 주인)", dataType = "User")
	private User user;
}
