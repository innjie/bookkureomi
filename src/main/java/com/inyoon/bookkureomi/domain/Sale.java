package com.inyoon.bookkureomi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sale {
	private int saleNo;
	private String publisher;
	private String title;
	private int costPrice;
	private int salePrice;
	private String image;
	private String info;
	private User user;
	private Date regiDate;
	private String state;
	private Genre genre;
	
}
