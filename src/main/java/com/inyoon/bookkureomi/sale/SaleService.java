package com.inyoon.bookkureomi.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyoon.bookkureomi.domain.Image;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.image.ImageMapper;
import com.inyoon.bookkureomi.sale.SaleMapper;

@Service
public class SaleService {
	@Autowired
	private SaleMapper saleMapper;
	@Autowired
	private ImageMapper imageMapper;

	//새로운 판매번호 가져오기
	public int getSaleNo() {
		return saleMapper.getSaleNo();
	}
	
	//전체 판매 목록 확인
	public List<Sale> getSaleList(Map<String, Object> paramMap){
		List<Sale> saleList = saleMapper.getSaleList(paramMap);
		for(int i=0; i < saleList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saleNo", saleList.get(i).getSaleNo());
			map.put("auctionNo", -1);
			
			saleList.get(i).setImageList(imageMapper.getImageList(map));
		}
		
		return saleList;
	} 
	
	//해당 판매 정보 확인
	public Sale getSale(int saleNo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("saleNo", saleNo);
		paramMap.put("auctionNo", -1);
		List<Image> imageList = imageMapper.getImageList(paramMap);
		
		Sale sale = saleMapper.getSale(saleNo);
		sale.setImageList(imageList);
		return sale;
	}
	
	//판매 검색
	public List<Sale> findSaleList(Map<String, Object> paramMap){
		List<Sale> saleList = saleMapper.findSaleList(paramMap);
		for(int i=0; i < saleList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saleNo", saleList.get(i).getSaleNo());
			map.put("auctionNo", -1);
			
			saleList.get(i).setImageList(imageMapper.getImageList(map));
		}
		
		
		return saleList;
	}
	
	//판매 책 등록
	@Transactional
	public void saleBook(Sale sale, List<Image> imageList){
		saleMapper.saleBook(sale);
		
		if(imageList.size() > 0) {
			for(int i = 0; i < imageList.size(); i++) {
				if(imageList.get(i).getSale() !=  null) {
					imageMapper.createSaleImage(imageList.get(i));
				} else {
					imageMapper.createAuctionImage(imageList.get(i));
				}
			}
		}
	}
	
	//판매 책 수정
	public void updateSale(Sale sale){
		saleMapper.updateSale(sale);
	}

	//판매 책 삭제
	@Transactional
	public void deleteSale(int saleNo){
		saleMapper.deleteSale(saleNo);
		imageMapper.deleteAllSaleImage(saleNo);
	}
	
	//나의 판매 내역 확인
	public List<Sale> getMySaleList(Map<String, Object> paramMap){
		List<Sale> saleList = saleMapper.getMySaleList(paramMap);
		for(int i=0; i < saleList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saleNo", saleList.get(i).getSaleNo());
			map.put("auctionNo", -1);
			
			saleList.get(i).setImageList(imageMapper.getImageList(map));
		}
		
		return saleList;
	}
	
	//전체 판매 카운트
	public int countSaleList() {
		return saleMapper.countSaleList();
	}
	
	//검색 판매 카운트
	public int countFindSaleList(Map<String, Object> paramMap) {
		return saleMapper.countFindSaleList(paramMap);
	}
	
	//나의 판매 카운트
	public int countMySaleList(int userNo) {
		return saleMapper.countMySaleList(userNo);
	}
	
	
	
	public List<Sale> getRecommendSale(int genreNo) {
		return saleMapper.recommend(genreNo);
	}

}
