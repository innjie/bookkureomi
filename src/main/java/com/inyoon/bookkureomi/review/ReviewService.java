package com.inyoon.bookkureomi.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviewList(int userNo){
        return reviewMapper.getReviewList(userNo);
    }
    public List<Review> receivedReviewList (int userNo) {
        return reviewMapper.receivedReviewList(userNo);
    }
    public void insertReview(Review review) {
        reviewMapper.insertReview(review);
    }
    public void removeReview(int reviewNo) {
        reviewMapper.deleteReview(reviewNo);
    }

}
