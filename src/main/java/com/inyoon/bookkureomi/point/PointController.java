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
	
	@ApiOperation(value="포인트 확인 화면 이동", notes="포인트 확인 화면으로 이동한다.")
	@GetMapping("/point/view")
    public String viewPoint() {	
        return "point/point";
    }
	
	
	@ApiOperation(value="포인트 확인", notes="포인트를 확인한다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/detail")
	public Map<String, Object> detailPoint() throws Exception {

		//int point = pointService.checkPointuserNo1);
		int point = pointService.checkPoint(1);
		
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
	//@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/rechargePoint")
	public ModelAndView rechargePoint(
			@RequestParam("rcMethod") String rcMethod,
			@RequestParam("rcPoint") String rcPoint,
			@RequestParam(value="result", required=false) String result) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		ModelMap model = new ModelMap();

		if(result.equals("success")) {
			User user = new User();
			user.setUserNo(1);
			
			Recharge recharge = new Recharge();
			//recharge.setRechargeNo(pointService.getRechargeNo(userNo));
			//recharge.setTotalPoint(pointService.checkPoint(userNo) + Integer.parseInt(rcPoint));
			recharge.setRechargeNo(pointService.getRechargeNo(1));
			recharge.setTotalPoint(pointService.checkPoint(1) + Integer.parseInt(rcPoint));
			recharge.setRcType("충전");
			recharge.setRcMethod(rcMethod);
			recharge.setRcPoint(Integer.parseInt(rcPoint));
			recharge.setUser(user);
			
			pointService.rechargePoint(recharge);
		}
		
		map.put("result", result);
		model.put("result", result);
		
        return new ModelAndView("point/point", model);
	}
	
/*
	//포인트 정보 보기
	//get point detail
	@RequestMapping("/recharge/getPoint.do")
	public ModelAndView getPoint(
			@RequestParam("orderNo") int orderNo) throws Exception {
		//
		//checkPoint(int userNo);
		//getRechargeList(int userNo);
	}

	//카카오페이 포인트 충전 폼
	//recharge point form by kakaoPay
	@RequestMapping("/point/rechargePointKakao.do", method=RequestMethod.GET)
	public String rechargForm(
			) throws Exception {
		//
	}
	
	//카카오페이 포인트 충전
	//recharge point by kakaoPay
	@RequestMapping("/point/rechargePointKakao.do", method=RequestMethod.POST)
	public String  rechargePoint(
			@RequestParam("rcPoint") int rcPoint,
			HttpServletRequest request,
			ModelMap model) throws Exception {
		//
	}

	//포인트 충전 폼
		//recharge point form
	@RequestMapping("/point/rechargePoint.do", method=RequestMethod.GET)
	public String rechargForm(
				) throws Exception {
		//
	}
	
	//포인트 충전
		//recharge point
	@RequestMapping("/point/rechargePoint.do", method=RequestMethod.POST)
	public String  rechargePoint(
			@RequestParam("rcPoint") int rcPoint,
			HttpServletRequest request,
			ModelMap model) throws Exception {
		//
	}
*/
}
