package com.inyoon.bookkureomi.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Bid;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidMapper bidMapper;


    public List<Bid> getBidListByAuctionNo(int auctionNo) {
        return bidMapper.getBidListByAuctionNo(auctionNo);
    }
    public List<Bid> getBidListByUserNo(int userNo) {
        return bidMapper.getBidListByUserNo(userNo);
    }
    public Bid getBid(int bidNo) {
        return bidMapper.getBid(bidNo);
    }
    public void getSuccessBidList(int userNo) {
        bidMapper.getSuccessBidList(userNo);
    }
    public void insertBid(Bid bid) {
        bidMapper.insertBid(bid);
    }

    public int getBidNo() { return bidMapper.getBidNo();}
}
