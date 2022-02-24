package com.inyoon.bookkureomi.domain;

import lombok.Data;

import java.util.Date;

public @Data
class Bid {
    private int bidNo;
    private int auctionNo;
    private int bidPrice;
    private Date bidTime;
    private int bidUserNo;
    private String pAddress;
    private String rAddress;
    private String rName;
    private String rPhone;
}
