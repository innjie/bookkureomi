package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.domain.*;
import com.inyoon.bookkureomi.genre.GenreService;
import com.inyoon.bookkureomi.user.Login;
import com.inyoon.bookkureomi.user.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/book")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private GenreService genreService;

    //view auctionList
    @GetMapping("/auction/page")
    public String auctionPage()  {
        return "auction/page";
    }

    //view auctionList by page
    @GetMapping("/auction/list")
    @ResponseBody
    public Map<String, Object> listAuction(@RequestParam("pageNo") int pageNo) {
        int showCnt = 12;
        int auctionCnt = auctionService.countAuctionList();
        int pageCnt = 0;

        List<Auction> auctionList = new ArrayList<>();
        if(auctionCnt > 0) {
            pageCnt = (auctionCnt % showCnt == 0) ? (auctionCnt / showCnt) : (auctionCnt / showCnt + 1);
            int start = 1 + (showCnt * (pageNo - 1));
            int end = showCnt + (showCnt * (pageNo - 1));

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("start", start);
            paramMap.put("end", end);

            auctionList = auctionService.getAllAuctionList();
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auctionCnt", auctionCnt);
        map.put("pageCnt", pageCnt);
        map.put("auctionList", auctionList);
        return map;
    }
    //register auction ... insert auction
    @PostMapping("/auction/insert")
    @ResponseBody
    public Map<String, Object> insertAuction(
            @RequestParam("title") String title,
            @RequestParam("publisher") String publisher,
            @RequestParam("endDate") String endDate,
            @RequestParam("bidPrice") int bidPrice,
            @RequestParam("immediPrice") int immediPrice,
            @RequestParam("info") String info,
            @RequestParam("image") String image,
            @RequestParam("genreType") String genreType,
            @AuthenticationPrincipal Login principal

    ) throws Exception {
        System.out.println("insert controller in");
        Auction auction = new Auction();
        User user = principal.getUser();
        user.setUserNo(user.getUserNo());
        Genre genre = genreService.getGenreByName(genreType);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDateFormat = dateFormat.parse(endDate);
        java.sql.Date d = new java.sql.Date(endDateFormat.getTime());

        Date today = new Date();
        String todayString = dateFormat.format(today);

        System.out.println(todayString);
        java.sql.Date regidate = java.sql.Date.valueOf(todayString);
        System.out.println(regidate);


        auction.setAuctionNo(auctionService.getAuctionNo());
        auction.setImage(image);
        auction.setTitle(title);
        auction.setPublisher(publisher);
        auction.setBidPrice(bidPrice);
        auction.setImmediPrice(immediPrice);
        auction.setState("open");
        auction.setRegiDate(regidate);
        auction.setInfo(info);
        auction.setUser(user);
        auction.setGenreNo(genre.getGenreNo());
        auction.setEndDate(d);

        auctionService.insertAuction(auction);

        System.out.println(user.getUserNo());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        map.put("pageNo", 1);
        map.put("auctionNo", auction.getAuctionNo());

        return map;
    }

    @ResponseBody
    @GetMapping("/auction/find")
    public Map<String, Object> findAuction(
            @RequestParam("title") String title,
            @RequestParam("pageNo") int pageNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        int showCnt = 12;
        int auctionCnt = auctionService.countFindAuctionList(title);
        int pageCnt = 0;

        List<Auction> auctionList = new ArrayList<>();

        if(auctionCnt > 0) {
            pageCnt = (auctionCnt % showCnt == 0) ? (auctionCnt / showCnt) : (auctionCnt / showCnt + 1);		//페이지 개수
            int start = 1+(showCnt*(pageNo-1));
            int end = showCnt+(showCnt*(pageNo-1));

            paramMap.put("start", start);
            paramMap.put("end", end);
            auctionList = auctionService.findAuction(title);
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auctionList", auctionList);
        map.put("auctionCnt", auctionCnt);
        map.put("pageCnt", pageCnt);
        return map;
    }

    //view myAuctionList by page
    @GetMapping("/mypage/auction/page")
    public String viewMyAuction() {
        return "mypage/myAuction";
    }

    @ResponseBody
    @GetMapping("/mypage/auction/list")
    public Map<String, Object> myAuctionList(
            @RequestParam("pageNo") int pageNo,
            @AuthenticationPrincipal Login principal) {
        int showCnt = 12;
        int auctionCnt = 0;
        int pageCnt = 0;

        List<Auction> auctionList = new ArrayList<>();
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("annonymousUser")) {
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.
                    getContext().getAuthentication();
            User user = principal.getUser();
            int userNo = user.getUserNo();
            auctionCnt = auctionService.countMyAuctionList(userNo);
            if(auctionCnt > 0) {
                auctionCnt = (auctionCnt % showCnt == 0) ? (auctionCnt / showCnt) : (auctionCnt / showCnt + 1);		//페이지 개수
                int start = 1+(showCnt*(pageNo-1));
                int end = showCnt+(showCnt*(pageNo-1));

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("start", start);
                paramMap.put("end", end);
                paramMap.put("userNo", userNo);

                auctionList = auctionService.getAuctionListByUserNo(userNo);

            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auctionList", auctionList);
        map.put("auctionCnt", auctionCnt);
        map.put("pageCnt", pageCnt);

        return map;
    }

    @ResponseBody
    @GetMapping("/auction/detail")
    public Map<String, Object> getAuction(@AuthenticationPrincipal Login principal,
                                          @RequestParam int auctionNo) {
        Map<String, Object> map = new HashMap<>();

        int userNo = 0;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            //user
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
            Login user = authentication.getUser();
            userNo = user.getUserNo();
        }

        Auction auction = auctionService.getAuction(auctionNo);
        map.put("auction", auction);
        map.put("isSeller", userNo == auction.getUser().getUserNo());
        map.put("result", "success");

        return map;
    }

//
//    //find auctionList
//    @RequestMapping("/auction/find.do")
//    public String findAuction(@RequestParam("text") String text, @RequestParam("type") String
//            type, ModelMap model) throws Exception {
//    }
//
//    //find auctionList by page
//    @RequestMapping("/auction/findPage.do")
//    public ModelAndView findAuctionInPage(@RequestParam("page") String page,
//                                          @ModelAttribute("auctionList") PagedListHolder<Auction> auctionList, BindingResult result) throws Exception {
//    }
//
    //view auction


//
//    //update auction ... form
//    @RequestMapping(value="/auction/update.do", method=RequestMethod.GET)
//    public String updateAuctionForm(@ModelAttribute("auctionCommand") AuctionCommand
//                                            auctionCommand, HttpServletRequest request) {
//    }
//
//    //update auction ... update
//    @RequestMapping(value="/auction/update.do", method= RequestMethod.POST)
//    public String updateAuction(@Valid @ModelAttribute("auctionCommand") AuctionCommand
//                                        auctionCommand, BindingResult result) throws Exception {
//    }

}
