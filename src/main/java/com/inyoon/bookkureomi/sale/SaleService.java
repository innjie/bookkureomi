package com.inyoon.bookkureomi.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.sale.SaleMapper;

@Service
public class SaleService {
	@Autowired
	private SaleMapper saleMapper;

	//새로운 판매번호 가져오기
	public int getSaleNo() {
		return saleMapper.getSaleNo();
	}
	
	//전체 판매 목록 확인
	public List<Sale> getSaleList(Map<String, Object> paramMap){
		return saleMapper.getSaleList(paramMap);
	}
	
	//해당 판매 정보 확인
	public Sale getSale(int saleNo){
		return saleMapper.getSale(saleNo);
	}
	
	//제목으로 판매 검색
	public List<Sale> findSaleByTitle(String title){
		return saleMapper.findSaleByTitle(title);
	}
	
	//장르로 판매 검색
	public List<Sale> findSaleByGenre(String type){
		return saleMapper.findSaleByGenre(type);
	}
	
	//판매 책 등록
	public void saleBook(Sale sale){
		saleMapper.saleBook(sale);
	}
	
	//판매 책 수정
	public void updateSale(Sale sale){
		saleMapper.updateSale(sale);
	}

	//판매 책 삭제
	public void deleteSale(int saleNo){
		saleMapper.deleteSale(saleNo);
	}
	
	//나의 판매 내역 확인
	public List<Sale> getMySaleList(int userNo){
		return saleMapper.getMySaleList(userNo);
	}
	
	//전체 판매 카운트
	public int countSaleList() {
		return saleMapper.countSaleList();
	}
	
	
	
	
	public List<Sale> recommend(int genreNo) {
		return saleMapper.recommend(genreNo);
	}

}
