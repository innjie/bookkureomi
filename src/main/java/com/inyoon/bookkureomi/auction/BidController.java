package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.address.AddressService;
import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.Auction;
import com.inyoon.bookkureomi.domain.Bid;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.sale.SaleService;
import com.inyoon.bookkureomi.user.Login;
import com.inyoon.bookkureomi.user.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BidController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private PointService pointService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private AddressService addressService;
//    //view my Bid List
//    @RequestMapping("/bid/myList.do")
//    public String myBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//    //view myBidList in page
//    @RequestMapping("/bid/myListPage.do")
//    public String myBidListPage(@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //view product bid list
//    @RequestMapping("/bid/productList.do")
//    public String productBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//
//    //view product bid list in page
//    @RequestMapping("/bid/productListPage.do")
//    public String productBidListPage (@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //insert Bid form
//    @RequestMapping(value = "/bid/insert.do",  method = RequestMethod.GET)
//    public String insertBidForm(@ModelAttribute("bidCommand") BidCommand bidCommand,
//                                ModelMap model) throws Exception {
//
//    }
//
    //Bid ... insertBid
    @RequestMapping(value = "/bid/insert",  method = RequestMethod.POST)
    public Map<String, Object> insertbid(@RequestParam("auctionNo") int auctionNo,
                                         @RequestParam("pAddress") String pAddress,
                                         @RequestParam("rName") String rName,
                                         @RequestParam("rPhone") String rPhone,
                                         @RequestParam("rAddress") String rAddress,
                                         @AuthenticationPrincipal Login principal) throws Exception {
        Map <String, Object> map = new HashMap<String, Object>();

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anounymous")) {
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
            User user = (User) principal.getUser();
            int userNo = user.getUserNo();

            boolean isCart;

            int orderNo = orderService.getOrderNo();
            int odNo = orderService.getODNo();

            int nowPoint = pointService.checkPoint(user.getUserNo());	//현재 포인트 확인
            int totalPrice = 0;

            //get auction
            Auction auction = auctionService.getAuction(auctionNo);

        }




        return map;
   }
//
//
//
//    //bid Success list
//    @RequestMapping(“bid/mySuccessList”)
//    public String successBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//
//    //view myBidList in page
//    @RequestMapping("/bid/mySuccessListPage.do")
//    public String successBidListPage(@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //view bid
//    @RequestMapping("/bid/view.do")
//    public String viewBid(@RequestParam("bidNo") int bidNo, ModelMap model,
//                          HttpServletRequest request) throws Exception {
//    }

}
