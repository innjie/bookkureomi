package com.inyoon.bookkureomi.review;

import com.inyoon.bookkureomi.domain.Review;
import com.inyoon.bookkureomi.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/book")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/review/page")
    public String reviewPage() { return "mypage/review"; }
//
//    @RequestMapping(“/review/myListPage.do”)
//    public String myReviewListPage(
//            @RequestParam("page") String page,
//            @ModelAttribute("reivew") PagedListHolder<Review>
//                    review, BindingResult result) throws Exception {
//    }
//
//    //received review list
//    @RequestMapping(“/review/receiveList.do”)
//    public String receivedReviewList(@RequestParam(“userNo”) int userNo, ModelMap model,
//                                     HttpServletRequest request) throws Exception {
//    }
//
//    //received reviewList page
//    @RequestMapping(“/review/receiveListPage.do”)
//    public String receivedReviewListPage(
//            @RequestParam("page") String page,
//            @ModelAttribute("reivew") PagedListHolder<Review>
//                    review, BindingResult result) throws Exception {
//    }
    @PostMapping("/review/insert")
    @Transactional
    @ResponseBody
    public Map<String, Object> insertReview(@AuthenticationPrincipal Login authentication,
                                            @RequestParam("orderNo") int orderNo,
                                            @RequestParam("score") int score,
                                            @RequestParam("content") String content) {
        Map<String, Object> map = new HashMap<>();

        int userNo = authentication.getUserNo();
        Review review = Review.builder()
                .orderNo(orderNo)
                .score(score)
                .content(content)
                .userNo(userNo)
                .build();

        try {
            //insert review
            reviewService.insertReview(review);
            map.put("result", "success");
        } catch (Exception e) {
            map.put("result", "fail");
            map.put("message", "오류가 발생했습니다.");
        }

        return map;
    }
    @ResponseBody
    @GetMapping("/review/detail")
    public Map<String, Object> reviewDetail(@RequestParam("orderNo") int orderNo,
                                            @AuthenticationPrincipal Login login) {
        Map<String, Object> map = new HashMap<>();
        int userNo = login.getUserNo();
        Review review = Review.builder()
                .orderNo(orderNo)
                .userNo(userNo)
                .build();

        review = reviewService.getReview(review);
        return map;
    }
//
//    //delete review
//    @RequestMapping(“/review/delete.do”)
//    public String deleteReview(@RequestParam(“reviewNo”) int reviewNo) {
//    }

}
