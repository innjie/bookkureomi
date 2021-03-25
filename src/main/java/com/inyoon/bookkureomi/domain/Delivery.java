package com.inyoon.bookkureomi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delivery {
	private Order order;
	private String company;
	private String waybill;
}
