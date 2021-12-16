package com.inyoon.bookkureomi.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inyoon.bookkureomi.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inyoon.bookkureomi.domain.CartItem;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.user.MyAuthentication;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/book")
@Api(value = "CartController", description = "카트 API")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@ApiOperation(value="카트 화면 이동", notes="카트 화면으로 이동한다.")
	@GetMapping("/cart/view")
    public String viewCart() {		
        return "cart/cart";
    }
	
	@ApiOperation(value="카트 내용물 목록", notes="카트 내용물 전체 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/cart/list")
	public Map<String, Object> listCartItem(@AuthenticationPrincipal Login principal) {

		List<Sale> itemList = new ArrayList<>();	

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();
			
			itemList = cartService.checkCart(userNo);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemList", itemList);
		
        return map;
    }
	
	@ApiOperation(value="카트 내용물 추가", notes="카트에 내용물을 추가한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/cart/create")
	public Map<String, Object> createCartItem(
			@RequestParam("saleNo") int saleNo,
			@AuthenticationPrincipal Login principal) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			
			Sale sale = new Sale();
			sale.setSaleNo(saleNo);
			
			CartItem cartItem = new CartItem();
			cartItem.setSale(sale);
			cartItem.setUser(user);
			

			if(cartService.checkCartItem(cartItem) == 0) {
				cartService.addCartItem(cartItem);
				map.put("result", "success");
			} else {
				map.put("result", "fail");
				map.put("reason", "sameInCart");
			}
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}

        return map;
    }
	
	@ApiOperation(value="카트 내용물 삭제", notes="카트에 내용물을 삭제한다.")
	@ResponseBody //@RestController 시 생략 가능
	@DeleteMapping("/cart/delete")
	public Map<String, Object> deleteCartItem(
			@RequestParam(value="saleNo", defaultValue = "-1", required = false) int saleNo,
			@AuthenticationPrincipal Login principal) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();
			
			if(saleNo != -1) {
				Sale sale = new Sale();
				sale.setSaleNo(saleNo);
				
				CartItem cartItem = new CartItem();
				cartItem.setSale(sale);
				cartItem.setUser(user);
				
				cartService.deleteCartItem(cartItem);
			} else {
				cartService.deleteAllCartItem(userNo);
			}
		
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		
        return map;
    }
}
