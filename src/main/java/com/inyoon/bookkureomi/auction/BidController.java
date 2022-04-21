package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.address.AddressService;
import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.*;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.sale.SaleService;
import com.inyoon.bookkureomi.user.Login;
import com.inyoon.bookkureomi.user.MyAuthentication;
import com.inyoon.bookkureomi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private BidService bidService;
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/bid/detail")
    public Map<String, Object> getBidUser(@RequestParam("userNo") int userNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.getUser(userNo);
            map.put("user", user);
        } catch(Exception e) {
            map.put("result", "fail");
            map.put("error", "오류가 발생했습니다.");
        }
        return map;
    }
    //insertBid
    @ResponseBody
    @PostMapping(value = "/bid/insert")
    public Map<String, Object> insertBid(@RequestParam("auctionNo") int auctionNo,
                                         @RequestParam("pAddress") String pAddress,
                                         @RequestParam("rName") String rName,
                                         @RequestParam("rPhone") String rPhone,
                                         @RequestParam("rAddress") String rAddress,
                                         @RequestParam("bidPrice") int bidPrice,
                                         @AuthenticationPrincipal Login principal) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anounymous")) {

            User user = (User) principal.getUser();
            int userNo = user.getUserNo();


            int odNo = orderService.getODNo();
            int bidNo = bidService.getBidNo();

            int nowPoint = pointService.checkPoint(user.getUserNo());    //현재 포인트 확인

            //get auction
            Auction auction = auctionService.getAuction(auctionNo);
            auction.setBidPrice(bidPrice);

            if (nowPoint < bidPrice) {
                map.put("result", "fail");
                map.put("reason", "※주문실패※\n주문 금액보다 충전된 포인트가 적습니다.");

            } else {
                Bid bid = new Bid();
                bid.setBidNo(bidNo);
                bid.setAuctionNo(auctionNo);
                bid.setBidUserNo(user.getUserNo());
                bid.setBidPrice(bidPrice);
                bid.setPAddress(pAddress);
                bid.setRAddress(rAddress);
                bid.setRName(rName);
                bid.setRPhone(rPhone);

                //point - buy
                Recharge recharge = new Recharge();
                recharge.setRechargeNo(pointService.getRechargeNo(userNo));
                recharge.setTotalPoint(nowPoint - bidPrice);
                recharge.setRcPoint(-1 * bidPrice);
                recharge.setUser(user);

                bidService.insertBid(bid);
                //bidprice update
                auction.setBidPrice(bidPrice);
                auctionService.updateAuction(auction);
                map.put("result", "success");
                map.put("bid", bid);
                map.put("totalPoint", recharge.getTotalPoint());
            }
        } else {
            map.put("result", "fail");
            map.put("reason", "로그인 후 이용이 가능합니다.");
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/auction/close")
    @Transactional
    public Map<String, Object> closeAuction(
            @RequestParam("auctionNo") int auctionNo,
            @AuthenticationPrincipal Login principal) {
        Map<String, Object> map = new HashMap<>();

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anounymous")) {
            //get auction list (status = open, endDate < Sysdate)
            Auction auction = auctionService.getAuction(auctionNo);

            //bid done
            //get bidlist by auctionno
            Bid bid = (Bid) bidService.getSuccessBid(auction.getAuctionNo());
            //get just one --> make new?
            if(bid == null) {
                int bidPrice = bid.getBidPrice();
                //bid -> order
                Order order = new Order();
                order.setOrderNo(orderService.getOrderNo());
                User user = userService.getUser(bid.getBidUserNo());
                order.setUser(user);
                order.setPAddress(bid.getPAddress());
                order.setRAddress(bid.getRAddress());
                order.setRName(bid.getRName());
                order.setRPhone(bid.getRPhone());
                order.setTotal(bid.getBidPrice());
                order.setInfo("");
                //service process

                //bid -> orderdetail
                List<OrderDetail> orderDetailList = new ArrayList<>();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setAuction(auctionService.getAuction(bid.getAuctionNo()));
                orderDetailList.add(orderDetail);
                //service process

                int userNo = user.getUserNo();

                //point
                int nowPoint = pointService.checkPoint(userNo);    //현재 포인트 확인
                //포인트 - 구매자
                Recharge recharge = new Recharge();
                recharge.setRechargeNo(pointService.getRechargeNo(userNo));
                recharge.setTotalPoint(nowPoint - bidPrice);
                recharge.setRcType("using");
                recharge.setRcPoint(-1 * bidPrice);
                recharge.setUser(user);

                //포인트 - 판매자
                User seller = new User();
                int sellerNo = auction.getUser().getUserNo();
                seller.setUserNo(sellerNo);
                Recharge sellerRecharge = new Recharge();
                int sellerPoint = pointService.checkPoint(sellerNo);
                sellerRecharge.setTotalPoint(sellerPoint + (int) (0.9 * bidPrice));
                sellerRecharge.setRechargeNo(pointService.getRechargeNo(sellerNo));
                sellerRecharge.setRcType("recharging");
                sellerRecharge.setRcMethod("selling");
                sellerRecharge.setRcPoint((int) (0.9 * bidPrice));
                sellerRecharge.setUser(seller);
                orderService.orderAuction(orderDetail, sellerRecharge, recharge);	//order 추가
            }
            //auction update(state : Open -> close
            auctionService.closeAuction(auctionNo);
            map.put("result", "success");
        } else {
            map.put("result", "fail");
            map.put("reason", "로그인 후 이용이 가능합니다.");
        }
        return map;
    }
    @ResponseBody
    @GetMapping("/bid/list")
    public Map<String, Object> bidListByAuctionNo(@RequestParam int auctionNo) {
        Map<String, Object> map = new HashMap<>();

        int userNo = 0;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            //user
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
            Login user = authentication.getUser();
            userNo = user.getUserNo();
        }
        try {
            List<Bid> bidList = bidService.getBidListByAuctionNo(auctionNo);
            System.out.println(bidList.size());
            map.put("bidList", bidList);
            map.put("result", "success");
        } catch (Exception e) {
            map.put("result", "fail");
        }

        return map;
    }
    @ResponseBody
    @GetMapping("/bid/userInfo")
    public Map<String, Object> getBidUserInfo(@RequestParam int userNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.getUser(userNo);
            map.put("user", user);
            map.put("result", "success");
        }catch(Exception e) {
            map.put("result", "fail");
            map.put("error", "정보 불러오기에 실패했습니다.");
        }
        return map;
    }
}
