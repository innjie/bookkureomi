package com.inyoon.bookkureomi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/book/sale")
public class SaleController{
	
	@GetMapping("/salelist")
    public String testPage1(Model model) {	
        return "sale/saleList";
    }
	
/*
	//SaleCommand

	//판매 책 리스트 확인
	//get salebook list
	@RequestMapping("/sale/getSaleList.do")
	public ModelAndView getSaleList(
			HttpServletRequest request) throws Exception{
		//
	}
	@RequestMapping("/sale/getSaleList2.do")
	public String getSaleList2(
			@RequestParam("page") String page,
			@ModelAttribute("saleList") PagedListHolder<Sale> saleList,
		BindingResult result) throws Exception {
		//
	}
	
	//판매 책 상세 보기
	//get salebook detail
	@RequestMapping("/sale/getSale.do")
	public ModelAndView getSale(
				@RequestParam("saleNo") int saleNo) throws Exception {
		//
	}

	//책 제목, 장르로 판매 책 검색
	//find salebook by name or genre
	@RequestMapping("/sale/findSale.do")
	public String findSale(
				@RequestParam("name") String name,
				@RequestParam("genre") String genre,
				ModelMap model) throws Exception {
		//
	}
	@RequestMapping("/sale/findSale2.do")
	public ModelAndView findSale2(
			@RequestParam("page") String page,
			@ModelAttribute("saleList") PagedListHolder<Sale> saleList,
		BindingResult result) throws Exception {
		//
	}
	
	
	//판매 책 등록 폼
	//sale book form
	@RequestMapping(value="/sale/saleBook.do", method=RequestMethod.GET)
	public String saleForm(
			 @ModelAttribute("saleCommand") SaleCommand saleCommand,
		 HttpServletRequest request) {				
		//
	}
	//판매 책 등록
	//sale book
	@RequestMapping(value="/sale/saleBook.do", method=RequestMethod.POST)
	public String saleBook(
				@Valid @ModelAttribute("saleCommand") SaleCommand saleCommand, 
			BindingResult result,
			HttpServletRequest request) throws Exception {
		//
	}
	
		//판매 등록 책 수정 폼
		//update sale book form
	@RequestMapping(value="/sale/updateSale.do", method=RequestMethod.GET)
	public String updateForm(
			@ModelAttribute("saleCommand") SaleCommand saleCommand,
		 HttpServletRequest request) {				
		//
	}
	//판매 등록 책 수정 
	//update sale book 
	@RequestMapping(value="/sale/updateSale.do", method=RequestMethod.POST)
	public String updateSale(
			@Valid @ModelAttribute("saleCommand") SaleCommand saleCommand, 
		BindingResult result,
		HttpServletRequest request) throws Exception {
		//
	}
	
	//판매 등록 책 삭제 폼
	//delete sale book
	@RequestMapping("/sale/deleteSale.do")
	public String deleteSale(
			@RequestParam("saleNo") int saleNo) throws Exception {
		//
	}
	
		//판매 내역 확인
		//get my sale list
	@RequestMapping("/sale/getMySaleList.do")
	public ModelAndView getMySaleList(
		HttpServletRequest request) throws Exception{
		//
	}
	@RequestMapping("/sale/getMySaleList2.do")
	public String getMySaleList2(
			@RequestParam("page") String page,
			@ModelAttribute("saleList") PagedListHolder<Sale> saleList,
			BindingResult result) throws Exception {
		//
	}
	
	//판매 정보 보기
	//get my sale
	@RequestMapping("/sale/getMySale.do")
	public ModelAndView getMySale(
			@RequestParam("saleNo") int saleNo) throws Exception {
		//
	}
	*/
}

