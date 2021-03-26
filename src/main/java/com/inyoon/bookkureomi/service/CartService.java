package com.inyoon.bookkureomi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.CartItem;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.mapper.CartMapper;

@Service
public class CartService {
	@Autowired
	private CartMapper cartMapper;

	public List<Sale> checkCart(int userNo){
		return cartMapper.checkCart(userNo);
	}
	public void addCartItem(CartItem cartItem){
		cartMapper.addCartItem(cartItem);
	}
	public void deleteCartItem(CartItem cartItem){
		cartMapper.deleteCartItem(cartItem);
	}
	public void deleteAllCartItem(int userNo){
		cartMapper.deleteAllCartItem(userNo);
	}

}