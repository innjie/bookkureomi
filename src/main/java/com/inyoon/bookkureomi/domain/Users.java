package com.inyoon.bookkureomi.domain;

import lombok.Data;

public @Data
class Users {
    private int userNo;
    private String id;
    private String name;
    private String pw;
    private String phone;
    private int firstGenre;
    private int secondGenre;
    private int thirdGenre;
}
