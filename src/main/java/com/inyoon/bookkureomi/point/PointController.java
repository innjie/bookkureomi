package com.inyoon.bookkureomi.point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.kakao.KakaoPayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/book")
@Api(value = "PointController", description = "포인트 API")
public class PointController {
	
	//수입지출 따로 리스트
	//날짜별 리스트
	
	@Autowired
	private PointService pointService;
	@Autowired
    private KakaoPayService kakaoPayService;
	
	@ApiOperation(value="포인트 확인 화면 이동", notes="포인트 확인 화면으로 이동한다.")
	@GetMapping("/point/view")
    public String viewPoint() {	
        return "point/point";
    }
	
	
	@ApiOperation(value="포인트 확인", notes="포인트를 확인한다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/detail")
	public Map<String, Object> detailPoint() throws Exception {

		//int point = pointService.checkPoint(userNo1);
		int point = 0;
		
		if(pointService.checkHasPoint(1) != 0) {
			point = pointService.checkPoint(1);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("point", point);
		
        return map;
	}
	
	@ApiOperation(value="포인트 이용 내역", notes="포인트 이용 내역을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/list")
	public Map<String, Object> listPoint() {

		List<Recharge> rechargeList = new ArrayList<Recharge>();
		//rechargeList = pointService.getRechargeList(userNo);
		rechargeList = pointService.getRechargeList(1);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rechargeList", rechargeList);
		
        return map;
    }
	
	@ApiOperation(value="포인트 충전", notes="포인트를 충전한다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/create")
	public Map<String, Object> rechargePoint(
			@RequestParam("rcPoint") String rcPoint) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		User user = new User();
		user.setUserNo(1);
		
		Recharge recharge = new Recharge();
		//recharge.setRechargeNo(pointService.getRechargeNo(userNo));
		//recharge.setTotalPoint(pointService.checkPoint(userNo) + Integer.parseInt(rcPoint));
		recharge.setRechargeNo(pointService.getRechargeNo(1));
		if(pointService.checkHasPoint(1) != 0) {
			recharge.setTotalPoint(Integer.parseInt(rcPoint));
		} else {
			recharge.setTotalPoint(pointService.checkPoint(1) + Integer.parseInt(rcPoint));
		}
		recharge.setRcType("recharging");
		recharge.setRcMethod("kakao");
		recharge.setRcPoint(Integer.parseInt(rcPoint));
		recharge.setUser(user);
		
		pointService.rechargePoint(recharge);
				
        return map;
	}
	
	@ApiOperation(value="포인트 충전 성공", notes="포인트 충전에 성공한다.")
	@GetMapping("/kakao/success")
    public ModelAndView kakaoPaySuccess(
    		@RequestParam("pg_token") String pg_token,
    		ModelMap model) {
		model.put("result", "success");

		model.put("info", kakaoPayService.kakaoPayInfo(pg_token));
		
		return new ModelAndView("point/result", model);
	}
	
	@ApiOperation(value="포인트 충전 취소", notes="포인트 충전을 취소한다.")
	@GetMapping("/kakao/cancel")
    public ModelAndView kakaoPayCancel(
    		ModelMap model) {
		model.put("result", "cancel");

		return new ModelAndView("point/result", model);
	}
	
	@ApiOperation(value="포인트 충전 실패", notes="포인트 충전에 실패한다.")
	@GetMapping("/kakao/fail")
    public ModelAndView kakaoPayFail(
    		ModelMap model) {
		model.put("result", "fail");

		return new ModelAndView("point/result", model); 	
	}
}
