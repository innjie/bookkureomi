package com.inyoon.bookkureomi.domain;

import lombok.Data;

import java.util.Date;

public @Data class Auction {
    private int auctionNo;
    private int userNo;
    private String title;
    private String publisher;
    private String image;
    private String info;
    private Date regiDate;
    private Date endDate;
    private String state;
    private int bidPrice;
    private int immediPrice;
    private int genre;
}
