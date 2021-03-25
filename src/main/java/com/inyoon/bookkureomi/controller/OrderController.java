package com.inyoon.bookkureomi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.inyoon.bookkureomi.domain.Order;

@Controller
public class OrderController {
/*
	//OrderCommand

	//책 주문 폼
	//order book form
	@RequestMapping(value="/order/orderBook.do", method=RequestMethod.GET)
	public String orderForm(
			 @ModelAttribute("orderCommand") OrderCommand orderCommand,
		 HttpServletRequest request) {				
		//
	}
	//판매 책 구매 
	//order book 
	@RequestMapping(value="/order/orderBook.do", method=RequestMethod.POST)
	public String orderBook(
			@Valid @ModelAttribute("orderCommand") OrderCommand orderCommand, 
			BindingResult result,
			HttpServletRequest request) throws Exception {
		//
	}

	//구매 내역 확인
	//get my order list
	@RequestMapping("/order/getOrderList.do")
	public String getOrderList(
			ModelMap model,
			HttpServletRequest request) throws Exception{
		//
	}
	@RequestMapping("/order/getOrderList2.do")
	public String getOrderList2(
			@RequestParam("page") String page,
			@ModelAttribute("orderList") PagedListHolder<Order> orderList,
			BindingResult result) throws Exception {
		//
	}

	//구매 정보 보기
	//get order detail
	@RequestMapping("/order/getOrder.do")
	public ModelAndView getOrder(
			@RequestParam("orderNo") int orderNo) throws Exception {
		//
	}
*/
}
