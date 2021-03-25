package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.Bid;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BidMapper {
    public List<Bid> getBidList(int userNo);
    public List<Bid> getBidListByAuctionNo(int auctionNo);
    public List<Bid> getBidListByUserNo(int userNo);
    public Bid getBid(int bidNo);
    public void insertBid(Bid bid);
    public void getSuccessBidList(int userNo);
    public void getSuccessBid(int bidNo, int userNo);
}
