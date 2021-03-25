package com.inyoon.bookkureomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Sale;

@Mapper
public interface CartMapper {
	public List<Sale> checkCart(int userNo);
	public void addCartItem(int userNo, Sale sale);
	public void deleteCartItem(int userNo, int saleNo);
	public void deleteAllCartItem(int userNo);
}
