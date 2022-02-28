package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.domain.Auction;
import com.inyoon.bookkureomi.domain.Bid;
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

    @Scheduled(cron="* * * * * *")
    public void auctionSchedule() {
        //auction update

        //get auction list (status = open, endDate < Sysdate)
        List<Auction> auctionList = auctionService.getAuctionListSchduled();

        for(Auction auction : auctionList) {
            //bid done
            //get bidlist by auctionno
            List<Bid> bidList = bidService.getBidListByAuctionNo(auction.getAuctionNo());
            //get just one --> make new?

            //bid -> order

            //auction update(state : Open -> close
            auction.setState("close");
            auctionService.updateAuction(auction);
        }

        //end
    }
}
