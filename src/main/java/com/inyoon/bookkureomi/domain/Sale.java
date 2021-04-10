package com.inyoon.bookkureomi.domain;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "일반 판매", description = "일반 판매")
@Getter
@Setter
public class Sale {
	@ApiModelProperty(value = "판매번호", dataType = "int")
	private int saleNo;
	@ApiModelProperty(value = "출판사", dataType = "String")
	private String publisher;
	@ApiModelProperty(value = "제목", dataType = "String")
	private String title;
	@ApiModelProperty(value = "원가", dataType = "int")
	private int costPrice;
	@ApiModelProperty(value = "판매가", dataType = "int")
	private int salePrice;
	@ApiModelProperty(value = "이미지소스", dataType = "String")
	private String image;
	@ApiModelProperty(value = "설명", dataType = "String")
	private String info;
	@ApiModelProperty(value = "판매자", dataType = "User")
	private User user;
	@ApiModelProperty(value = "등록일", dataType = "Date")
	private Date regiDate;
	@ApiModelProperty(value = "판매상태", dataType = "String")
	private String state;
	@ApiModelProperty(value = "장르", dataType = "Genre")
	private Genre genre;
	@ApiModelProperty(value = "저자", dataType = "String")
	private String author;
}
