package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.domain.*;
import com.inyoon.bookkureomi.order.OrderService;
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

    @Scheduled(cron="* * * * * *")
    public void auctionSchedule() {
        //auction update

        //get auction list (status = open, endDate < Sysdate)
        List<Auction> auctionList = auctionService.getAuctionListSchduled();

        for(Auction auction : auctionList) {
            //bid done
            //get bidlist by auctionno
            Bid bid = (Bid) bidService.getSuccessBid(auction.getAuctionNo());
            //get just one --> make new?


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

            //point

            //auction update(state : Open -> close
            auction.setState("close");
            auctionService.updateAuction(auction);
        }

        //end
    }
}
