package com.inyoon.bookkureomi.review;

import lombok.Data;

public @Data class Review {
    private int orderNo;
    private String content;
    private int score;
}
