package com.inyoon.bookkureomi.kakao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.sale.SaleService;
 
 
@Controller
@RequestMapping("/book/kakao")
public class KakaoPayController {
    
	@Autowired
    private KakaoPay kakaopay;
	
	@Autowired
	private SaleService saleService;
	
	@RequestMapping("/kakaoPay")
    public String imPurkakaoPay(
    		@RequestParam("saleNo") String saleNo,
    		HttpServletRequest request) {
    	System.out.println("imPurkakaoPay post............................................");
        
    	HttpSession httpSession = request.getSession();
		//User user = (User) httpSession.getAttribute("user");
    	
		int no = Integer.parseInt(request.getParameter("saleNo"));
		//Auction auction = this.farm.getAuction(aNo);
		Sale sale = saleService.getSale(no);  	
        
        
      //  if (sale != null) {
        	return "redirect:" + kakaopay.kakaoPayReady(sale, "im");
      /*  } else if (auction != null) {
        	return "redirect:" + kakaopay.kakaoPayReady(auction, user);
        }*/
 
    }
    
    
}