package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.domain.*;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    AuctionService auctionService;
    @Autowired
    BidService bidService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    PointService pointService;

    @Scheduled(cron = "* * * * * *")
    public void auctionSchedule() {
        //auction update

        //get auction list (status = open, endDate < Sysdate)
        Auction auction = auctionService.getAuctionListSchduled();


        //bid done
        //get bidlist by auctionno
        Bid bid = (Bid) bidService.getSuccessBid(auction.getAuctionNo());
        //get just one --> make new?

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
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setAuction(auctionService.getAuction(bid.getAuctionNo()));
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


        //auction update(state : Open -> close
        auction.setState("close");
        auctionService.updateAuction(auction);


        //end
    }
}
