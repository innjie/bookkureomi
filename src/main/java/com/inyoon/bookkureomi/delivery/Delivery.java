package com.inyoon.bookkureomi.delivery;

import com.inyoon.bookkureomi.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delivery {
	private Order order;
	private String company;
	private String waybill;
}
