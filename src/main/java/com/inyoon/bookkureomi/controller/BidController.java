package com.inyoon.bookkureomi.controller;

import com.inyoon.bookkureomi.domain.Bid;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BidController {
//    //view my Bid List
//    @RequestMapping("/bid/myList.do")
//    public String myBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//    //view myBidList in page
//    @RequestMapping("/bid/myListPage.do")
//    public String myBidListPage(@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //view product bid list
//    @RequestMapping("/bid/productList.do")
//    public String productBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//
//    //view product bid list in page
//    @RequestMapping("/bid/productListPage.do")
//    public String productBidListPage (@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //insert Bid form
//    @RequestMapping(value = "/bid/insert.do",  method = RequestMethod.GET)
//    public String insertBidForm(@ModelAttribute("bidCommand") BidCommand bidCommand,
//                                ModelMap model) throws Exception {
//
//    }
//
//    //Bid ... insertBid
//    @RequestMapping(value = "/auction/insert.do",  method = RequestMethod.POST)
//    public ModelAndView insertbid(@RequestParam("aNo") int aNo,
//                                  @RequestParam("minPrice") int minPrice, @Valid @ModelAttribute("bidCommand")
//                                          BidCommand bidCommand, BindingResult bindingResult, HttpServletRequest request) throws Exception {
//
//    }
//
//
//
//    //bid Success list
//    @RequestMapping(“bid/mySuccessList”)
//    public String successBidList(ModelMap model, HttpServletRequest request) throws Exception {
//    }
//
//    //view myBidList in page
//    @RequestMapping("/bid/mySuccessListPage.do")
//    public String successBidListPage(@RequestParam("page") String page, @ModelAttribute("bidList")
//            PagedListHolder<Bid> bidList, BindingResult result) throws Exception {
//    }
//
//    //view bid
//    @RequestMapping("/bid/view.do")
//    public String viewBid(@RequestParam("bidNo") int bidNo, ModelMap model,
//                          HttpServletRequest request) throws Exception {
//    }

}
