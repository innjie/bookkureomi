package com.inyoon.bookkureomi.review;

import com.inyoon.bookkureomi.domain.User;
import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Review;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {
    public List<Review> getReviewList(Map<String, Object> paramMap);
    public List<Review> receivedReviewList (long userNo);
    public void insertReview(Review review);
    public void deleteReview(Review review);
    public Review getReview(Review review);
    public int getReviewCount(int userNo);
    public Review getReviewByType(Map<String, Object> parameter);
}
