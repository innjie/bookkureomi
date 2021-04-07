package com.inyoon.bookkureomi.auction;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BidMapper {
    public List<Bid> getBidListByAuctionNo(int auctionNo);
    public List<Bid> getBidListByUserNo(int userNo);
    public Bid getBid(int bidNo);
    public void insertBid(Bid bid);
    public void getSuccessBidList(int userNo);
}
