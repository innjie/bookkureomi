package com.inyoon.bookkureomi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recharge {
	private int rechargeNo;
	private User user;
	private int rcPoint;
	private int totalPoint;
	private Date rcDate;
	private String rcMethod;
}
