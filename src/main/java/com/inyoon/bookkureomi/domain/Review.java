package com.inyoon.bookkureomi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Review {
    private int orderNo;
    private int userNo;
    private String reviewText;
    private int score;
    private int isDelete;
}
