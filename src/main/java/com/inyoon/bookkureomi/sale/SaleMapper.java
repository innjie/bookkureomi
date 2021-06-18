package com.inyoon.bookkureomi.sale;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Sale;

@Mapper
public interface SaleMapper {
	public int getSaleNo();
	
	public List<Sale> getSaleList(Map<String, Object> paramMap);
	public Sale getSale(int saleNo);
	public List<Sale> findSaleList(Map<String, Object> paramMap);
	
	public void saleBook(Sale sale);
	public void updateSale(Sale sale);
	public void deleteSale(int saleNo);

	public List<Sale> getMySaleList(int userNo);
	public List<Sale> recommend(int genreNo);
	
	public int countSaleList();
	public int countFindSaleList(Map<String, Object> paramMap);
}
