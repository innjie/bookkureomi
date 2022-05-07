package com.inyoon.bookkureomi.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviewList(Map<String, Object> paramMap){
        return reviewMapper.getReviewList(paramMap);
    }
    public List<Review> receivedReviewList (Map<String, Object> paramMap) {
        return reviewMapper.receivedReviewList(paramMap);
    }
    public void insertReview(Review review) {
        reviewMapper.insertReview(review);
    }
    public Review getReview(Review review) {return reviewMapper.getReview(review);}
    public int getReviewCount(int userNo) {return reviewMapper.getReviewCount(userNo);}

    public void deleteReview(Review review) { reviewMapper.deleteReview(review);}
}
