package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReviewMapper {
    public List<Review> getReviewList(int userNo);
    public List<Review> receivedReviewList (int userNo);
    public Review getReview(int reviewNo);
    public void insertReview(Review review);
    public void removeReview(int reviewNo);
}
