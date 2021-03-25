package com.inyoon.bookkureomi.service;

import com.inyoon.bookkureomi.domain.Bid;
import com.inyoon.bookkureomi.mapper.BidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidMapper bidMapper;

    public List<Bid> getBidList(int userNo) {
        return bidMapper.getBidList(userNo);
    }
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

}
