package com.inyoon.bookkureomi.kakao;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.point.PointService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
 
@Controller
@RequestMapping("/book/kakao")
@Api(value = "KakaoPayController", description = "카카오페이 API")
public class KakaoPayController {
    
	@Autowired
    private KakaoPayService kakaoPayService;
	
	@Autowired
	private PointService pointService;
	
	@ApiOperation(value="카카오페이 결제", notes="카카오페이로 포인트를 충전한다.")
	@ResponseBody
	@GetMapping("/kakaoPay")
	public Map<String, Object> imPurkakaoPay(
    		@RequestParam("rcPoint") String rcPoint,
    		HttpServletRequest request) {
    	System.out.println("imPurkakaoPay post............................................");
        
		Map<String, Object> map = new HashMap<String, Object>();

    	//HttpSession httpSession = request.getSession();
		//User user = (User) httpSession.getAttribute("user");
    	User user = new User();
    	user.setId("im");
    	user.setUserNo(1);
    			
		Recharge recharge = new Recharge();
        recharge.setUser(user);
        recharge.setRcPoint(Integer.parseInt(rcPoint));
        //recharge.setRechargeNo(pointService.getRechargeNo(user.getUserNo()));
        recharge.setRechargeNo(pointService.getRechargeNo(1));
        
        String result = kakaoPayService.kakaoPayReady(recharge, request);
        System.out.println(result);
        map.put("result", result);
        
		return map;
		//return "redirect:" + kakaoPayService.kakaoPayReady(recharge);
    }
    
    
}