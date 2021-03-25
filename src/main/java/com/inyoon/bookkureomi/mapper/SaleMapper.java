package com.inyoon.bookkureomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Sale;

@Mapper
public interface SaleMapper {
	public List<Sale> getSaleList();
	public Sale getSale(int saleNo);
	public List<Sale> findSaleByName(String name);
	public List<Sale> findSaleByGenre(String genre);
	
	public void saleBook(Sale sale);
	public void updateSale(int saleNo, Sale sale);
	public void deleteSale(int saleNo);

	public List<Sale> getMySaleList(int userNo);
	public Sale getMySale(int saleNo);
	public List<Sale> recommend(int genreNo);
}
