package com.inyoon.bookkureomi.auction;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Bid;

import java.util.List;

@Mapper
public interface BidMapper {
    public List<Bid> getBidListByAuctionNo(int auctionNo);
    public List<Bid> getBidListByUserNo(int userNo);
    public Bid getBid(int bidNo);
    public void insertBid(Bid bid);
    public Bid getSuccessBid(int auctionNo);

    public int getBidNo();
}
