package com.inyoon.bookkureomi.review;

import com.inyoon.bookkureomi.domain.Review;
import com.inyoon.bookkureomi.user.Login;
import org.codehaus.groovy.runtime.metaclass.ConcurrentReaderHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/book")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/review/page")
    public String reviewPage() { return "mypage/review"; }

    @GetMapping("/review/list")
    @ResponseBody
    public Map<String, Object> reviewList(@AuthenticationPrincipal Login login,
                                          @RequestParam("pageNo") int pageNo) {
        int showCnt = 10;	//보여주는 개수
        int reviewCnt = 0;	//리스트 개수
        int pageCnt = 0;

        Map<String, Object> map = new HashMap<>();
        int userNo = login.getUserNo();
        List<Review> reviewList = new ArrayList<>();
        reviewCnt = reviewService.getReviewCount(userNo);
        if(reviewCnt > 0) {
            pageCnt = (reviewCnt % showCnt == 0) ? (reviewCnt / showCnt) : (reviewCnt / showCnt + 1);		//페이지 개수
            int start = 1+(showCnt*(pageNo-1));
            int end = showCnt+(showCnt*(pageNo-1));

            System.out.println("start: " + start + ", end: " + end);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userNo", userNo);
            paramMap.put("start", start);
            paramMap.put("end", end);
            reviewList = reviewService.getReviewList(paramMap);
        }
        map.put("reviewList", reviewList);
        map.put("reviewCnt", reviewCnt);
        map.put("pageCnt", pageCnt);

        return map;
    }
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
                                            @RequestParam("reviewText") String reviewText) {
        Map<String, Object> map = new HashMap<>();

        int userNo = authentication.getUserNo();
        Review review = Review.builder()
                .orderNo(orderNo)
                .score(score)
                .reviewText(reviewText)
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
        map.put("review", review);
        return map;
    }
    @ResponseBody
    @PostMapping("/review/delete")
    public Map<String, Object> deleteReview(@AuthenticationPrincipal Login login,
                                            @RequestParam int orderNo) {
        Map<String, Object> map = new HashMap<>();

        int userNo = login.getUserNo();
        Review review = Review.builder()
                .orderNo(orderNo)
                .userNo(userNo)
                .build();

        try {
            reviewService.deleteReview(review);
            map.put("result", "success");
        } catch(Exception e) {
            map.put("result", "fail");
        }
        return map;
    }

}
