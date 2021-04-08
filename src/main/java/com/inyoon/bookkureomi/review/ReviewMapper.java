package com.inyoon.bookkureomi.review;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Review;

import java.util.List;
@Mapper
public interface ReviewMapper {
    public List<Review> getReviewList(int userNo);
    public List<Review> receivedReviewList (int userNo);
    public void insertReview(Review review);
    public void deleteReview(int reviewNo);
}
