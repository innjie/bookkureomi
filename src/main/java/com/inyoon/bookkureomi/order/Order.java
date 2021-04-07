package com.inyoon.bookkureomi.order;

import java.util.Date;

import com.inyoon.bookkureomi.auction.Auction;
import com.inyoon.bookkureomi.sale.Sale;
import com.inyoon.bookkureomi.user.User;
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
