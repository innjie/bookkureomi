package com.inyoon.bookkureomi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "사진 파일")
@Getter
@Setter
public class Image {
	
	@ApiModelProperty(value = "이미지번호", dataType = "long")
	private int imageNo;
	@ApiModelProperty(value = "판매", dataType = "Sale")
	private Sale sale;
	@ApiModelProperty(value = "경매", dataType = "Auction")
	private Auction auction;
	@ApiModelProperty(value = "원본파일이름", dataType = "String")
	private String originName;
	@ApiModelProperty(value = "파일이름", dataType = "String")
	private String name;
	@ApiModelProperty(value = "파일위치", dataType = "String")
	private String filePath;
}
