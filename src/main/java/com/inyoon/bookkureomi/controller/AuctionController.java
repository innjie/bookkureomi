package com.inyoon.bookkureomi.controller;

import com.inyoon.bookkureomi.domain.Auction;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class AuctionController {
//    //view auctionList
//    @RequestMapping("/auction/view.do")
//    public String auctionList(ModelMap model) throws Exception {
//
//    }
//
//    //view auctionList by page
//    @RequestMapping("/auction/viewAuctionListPage.do")
//    public String auctionListPage(@RequestParam("page") String page,
//                                  @ModelAttribute("auctionList") PagedListHolder<Auction> auctionList,
//                                  BindingResult result) throws Exception {
//
//    }
//
//    //view  myAuctionList
//    @RequestMapping("/auction/myList.do")
//    public String myAuctionList(ModelMap model, HttpServletRequest request) throws Exception {
//
//    }
//
//
//    //view myAuctionList by page
//    @RequestMapping("/auction/myListPage.do")
//    public String listMyAuctionInPage(@RequestParam("page") String page,
//                                      @ModelAttribute("auctionList") PagedListHolder<Auction> auctionList,
//                                      BindingResult result) throws Exception {
//
//    }
//
//    //find auctionList
//    @RequestMapping("/auction/find.do")
//    public String findAuction(@RequestParam("text") String text, @RequestParam("type") String
//            type, ModelMap model) throws Exception {
//    }
//
//    //find auctionList by page
//    @RequestMapping("/auction/findPage.do")
//    public ModelAndView findAuctionInPage(@RequestParam("page") String page,
//                                          @ModelAttribute("auctionList") PagedListHolder<Auction> auctionList, BindingResult result) throws Exception {
//    }
//
//    //view auction
//    @RequestMapping("/auction/view.do")
//    public String viewAuction(@RequestParam("aNo") int aNo, ModelMap model,
//                              HttpServletRequest request) throws Exception {
//    }
//
//    //register auction ... auction form
//    @RequestMapping(value = "/auction/insert.do", method = RequestMethod.GET)
//    public String insertAuctionForm(@ModelAttribute("auctionCommand") AuctionCommand
//                                            auctionCommand) throws Exception {
//
//    }
//
//    //register auction ... insert auction
//    @RequestMapping(value = "/auction/insert.do", method = RequestMethod.POST)
//    public String insertAuction(@Valid @ModelAttribute("auctionCommand") AuctionCommand
//                                        auctionCommand,BindingResult bindingResult,HttpServletRequest request)
//            throws Exception {
//    }
//
//    //update auction ... form
//    @RequestMapping(value="/auction/update.do", method=RequestMethod.GET)
//    public String updateAuctionForm(@ModelAttribute("auctionCommand") AuctionCommand
//                                            auctionCommand, HttpServletRequest request) {
//    }
//
//    //update auction ... update
//    @RequestMapping(value="/auction/update.do", method= RequestMethod.POST)
//    public String updateAuction(@Valid @ModelAttribute("auctionCommand") AuctionCommand
//                                        auctionCommand, BindingResult result) throws Exception {
//    }
//
//    //delete auction
//    @RequestMapping(“/auction/delete.do”)
//    public String deleteAuction(@RequestParam(“auctionNo”) int auctionNo) {
//    }
}
