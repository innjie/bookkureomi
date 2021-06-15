package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.domain.Auction;
import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //    //view auctionList
    @GetMapping("/auction/page")
    public String auctionPage()  {
        System.out.println("page Controller in"); return "auction/page";
    }
    //
//    //view auctionList by page
    @GetMapping("/auction/list")
    @ResponseBody
    public Map<String, Object> listAuction() {
        List<Auction> auctionList = new ArrayList<>();
        auctionList = auctionService.getAllAuctionList();

        Map<String, Object> map = new HashMap<String, Object>();
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
            @RequestParam("userNo") int userNo
    ) throws Exception {
        System.out.println("insert controller in");
        Auction auction = new Auction();
        User user = new User();
        user.setUserNo(userNo);
        Genre genre = genreService.getGenreByName(genreType);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDateFormat = dateFormat.parse(endDate);
        java.sql.Date d = new java.sql.Date(endDateFormat.getTime());

        Date today = new Date();
        String todayString = dateFormat.format(today);
        System.out.println("today : " + today);
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

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        map.put("auctionNo", auction.getAuctionNo());

        return map;
    }

    @ResponseBody
    @GetMapping("/auction/find")
    public Map<String, Object> findAuction(@RequestParam("title") String title) {
        List<Auction> auctionList = new ArrayList<>();
        auctionList = auctionService.findAuction(title);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auctionList", auctionList);
        return map;
    }
//    //view  myAuctionList
//    @RequestMapping("/auction/myList.do")
//    public String myAuctionList(ModelMap model, HttpServletRequest request) throws Exception {
//
//    }
//
//
//    //view myAuctionList by page
    @GetMapping("/mypage/auction/page")
    public String viewMyAuction() {
        return "mypage/myAuction";
    }

    @ResponseBody
    @GetMapping("/mypage/auction/list")
    public Map<String, Object> myAuctionList() {
        int userNo = 1;
        List<Auction> auctionList = auctionService.getAuctionListByUserNo(userNo);
        System.out.println(auctionList.size());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auctionList", auctionList);

        return map;
    }
//    @RequestMapping("/auction/myListPage.do")
//    public String listMyAuctionInPage(@RequestParam("page") String page,
//                                      @ModelAttribute("auctionList") PagedListHolder<Auction> auctionList,
//                                      BindingResult result) throws Exception {
//
//    }
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
    @GetMapping("/auction/detail")
    @ResponseBody
    public Map<String, Object> viewAuction(@RequestParam("auctionNo") int auctionNo) throws Exception {
        Auction auction = new Auction();
        System.out.println("auction detail controller in");
        auction = auctionService.getAuction(auctionNo);
        auction.setGenreType(genreService.getGenre(auction.getGenreNo()).getGenreType());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date endDateFormat = dateFormat.parse(auction.getEndDate().toString());
//        auction.setEndDate(endDateFormat);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auction", auction);
        return map;
    }


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
//
//    //delete auction
//    @RequestMapping(“/auction/delete.do”)
//    public String deleteAuction(@RequestParam(“auctionNo”) int auctionNo) {
//    }
}
