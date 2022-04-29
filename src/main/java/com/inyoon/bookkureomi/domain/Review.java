package com.inyoon.bookkureomi.domain;

import lombok.Builder;
import lombok.Data;
@Builder
public @Data class Review {
    private int orderNo;
    private int userNo;
    private String content;
    private int score;
}
