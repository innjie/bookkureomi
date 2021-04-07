package com.inyoon.bookkureomi.sale;

import java.util.Date;

import com.inyoon.bookkureomi.genre.Genre;
import com.inyoon.bookkureomi.user.User;
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
