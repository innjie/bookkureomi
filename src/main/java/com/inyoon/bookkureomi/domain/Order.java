package com.inyoon.bookkureomi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	private int orderNo;
	private Sale sale;
	private Auction auction;
	private User user;
	private String pAddress;
	private Date orderDate;
	private String rAddress;
	private String rName;
	private String rPhone;
}
