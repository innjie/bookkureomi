package com.inyoon.bookkureomi.domain;

import lombok.Data;

public @Data class Review {
    private int reviewNo;
    private int saleNo;
    private int saleUserNo;
    private int purUserNo;
    private String content;
    private int score;
}
