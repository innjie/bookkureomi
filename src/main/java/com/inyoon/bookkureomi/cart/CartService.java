package com.inyoon.bookkureomi.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.CartItem;
import com.inyoon.bookkureomi.domain.Sale;

@Service
public class CartService {
	@Autowired
	private CartMapper cartMapper;

	//카트 아이템 목록 확인
	public List<Sale> checkCart(int userNo){
		return cartMapper.checkCart(userNo);
	}
	
	//해당 카트아이템 추가
	public void addCartItem(CartItem cartItem){
		cartMapper.addCartItem(cartItem);
	}
	
	//해당 아이템을 카트에서 삭제
	public void deleteCartItem(CartItem cartItem){
		cartMapper.deleteCartItem(cartItem);
	}
	
	//해당 사용자의 카트 아이템 목록 삭제
	public void deleteAllCartItem(int userNo){
		cartMapper.deleteAllCartItem(userNo);
	}
	
	//해당 아이템이 카트에 있는지 확인
	public int checkCartItem(CartItem cartItem) {
		return cartMapper.checkCartItem(cartItem);
	}

}
