package com.inyoon.bookkureomi.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.sale.SaleMapper;

@Service
public class SaleService {
	@Autowired
	private SaleMapper saleMapper;

	public int getSaleNo() {
		return saleMapper.getSaleNo();
	}
	
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
	}
	
	public List<Sale> getMySaleList(int userNo){
		return saleMapper.getMySaleList(userNo);
	}
	public List<Sale> recommend(int genreNo) {
		return saleMapper.recommend(genreNo);
	}

	public int checkSalePrice(int saleNo){
		return saleMapper.checkSalePrice(saleNo);
	}
}
