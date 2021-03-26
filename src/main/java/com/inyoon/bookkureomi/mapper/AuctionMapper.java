package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.Auction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuctionMapper {
    public List<Auction> getAllAuctionList();
    public List<Auction> getAuctionListByUserNo(int userNo);
    public Auction getAuction(int auctionNo);
    public void insertAuction(Auction auction);
    public void updateAuction(int auctionNo, Auction auction);
    public void deleteAuction(int auctionNo);
    public List<Auction> findAuction(String title);
}
