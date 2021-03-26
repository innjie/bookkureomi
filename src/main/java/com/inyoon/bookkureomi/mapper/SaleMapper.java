package com.inyoon.bookkureomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inyoon.bookkureomi.domain.Sale;

@Mapper
public interface SaleMapper {
	public List<Sale> getSaleList();
	public Sale getSale(int saleNo);
	public List<Sale> findSaleByTitle(String title);
	public List<Sale> findSaleByGenre(String type);
	
	public void saleBook(Sale sale);
	public void updateSale(Sale sale);
	public void deleteSale(int saleNo);
	public void updateSaleStateClose(int saleNo);

	public List<Sale> getMySaleList(int userNo);
	public Sale getMySale(int saleNo);
	public List<Sale> recommend(int genreNo);
}
