package com.inyoon.bookkureomi.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.sale.Sale;

@Mapper
public interface CartMapper {
	public List<Sale> checkCart(int userNo);
	public void addCartItem(CartItem cartItem);
	public void deleteCartItem(CartItem cartItem);
	public void deleteAllCartItem(int userNo);
}
