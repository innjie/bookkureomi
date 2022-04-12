package com.inyoon.bookkureomi.auction;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Auction;

import java.util.List;

@Mapper
public interface AuctionMapper {
    public int getAuctionNo();
    public List<Auction> getAllAuctionList();
    public List<Auction> getAuctionListByUserNo(int userNo);
    public Auction getAuction(int auctionNo);
    public void insertAuction(Auction auction);
    public void updateAuction(Auction auction);
    public void deleteAuction(int auctionNo);
    public List<Auction> findAuction(String title);
    public int countAuctionList();
    public int countMyAuctionList(int userNo);
    public int countFindAuctionList(String title);
    public Auction getAuctionListSchduled();
    public void closeAuction(int auctionNo);
}
