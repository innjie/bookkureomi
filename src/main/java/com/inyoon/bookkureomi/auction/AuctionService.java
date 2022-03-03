package com.inyoon.bookkureomi.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Auction;

import java.util.List;

@Service
public class AuctionService {
    @Autowired
    private AuctionMapper auctionMapper;

    public int getAuctionNo() {return auctionMapper.getAuctionNo();}
    public List<Auction> getAllAuctionList() {
        return auctionMapper.getAllAuctionList();
    }
    public List<Auction> getAuctionListByUserNo(int userNo) {
        return auctionMapper.getAuctionListByUserNo(userNo);
    }
    public Auction getAuctionListSchduled() {
        return auctionMapper.getAuctionListSchduled();
    }
    public Auction getAuction(int auctionNo) {
        return auctionMapper.getAuction(auctionNo);
    }
    public void insertAuction(Auction auction) {
        auctionMapper.insertAuction(auction);
    }
    public void updateAuction(Auction auction) {
        auctionMapper.updateAuction(auction);
    }
    public void deleteAuction(int auctionNo) {
        auctionMapper.deleteAuction(auctionNo);
    }
    public List<Auction> findAuction(String title) { return auctionMapper.findAuction(title); }

    public int countAuctionList() { return auctionMapper.countAuctionList();}

    public int countMyAuctionList(int userNo) { return auctionMapper.countMyAuctionList(userNo);}

    public int countFindAuctionList(String title) { return auctionMapper.countFindAuctionList(title); }
}
