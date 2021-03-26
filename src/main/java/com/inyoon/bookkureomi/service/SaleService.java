package com.inyoon.bookkureomi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.mapper.SaleMapper;

@Service
public class SaleService {
	@Autowired
	private SaleMapper saleMapper;

	public List<Sale> getSaleList(){
		return saleMapper.getSaleList();
	}
	public Sale getSale(int saleNo){
		return saleMapper.getSale(saleNo);
	}
	public List<Sale> findSaleByTitle(String title){
		return saleMapper.findSaleByTitle(title);
	}
	public List<Sale> findSaleByGenre(String type){
		return saleMapper.findSaleByGenre(type);
	}
	
	public void saleBook(Sale sale){
		saleMapper.saleBook(sale);
	}
	public void updateSale(Sale sale){
		saleMapper.updateSale(sale);
	}
	public void deleteSale(int saleNo){
		saleMapper.deleteSale(saleNo);
		saleMapper.updateSaleStateClose(saleNo);
	}
	
	public List<Sale> getMySaleList(int userNo){
		return saleMapper.getMySaleList(userNo);
	}
	public Sale getMySale(int saleNo){
		return saleMapper.getMySale(saleNo);
	}
	public List<Sale> recommend(int genreNo) {
		return saleMapper.recommend(genreNo);
	}

}