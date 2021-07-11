package com.inyoon.bookkureomi.domain;

import lombok.Data;

public @Data
class Address {
    private int addrNo;
    private int userNo;
    private String addr;
    private String aName;
    private String zipcode;
}
