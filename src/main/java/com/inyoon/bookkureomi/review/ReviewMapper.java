package com.inyoon.bookkureomi.review;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Review;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {
    public List<Review> getReviewList(Map<String, Object> paramMap);
    public List<Review> receivedReviewList (Map<String, Object> paramMap);
    public void insertReview(Review review);
    public void deleteReview(int reviewNo);
    public Review getReview(Review review);
    public int getReviewCount(int userNo);
}
