package com.inyoon.bookkureomi.point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.inyoon.bookkureomi.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.kakao.KakaoPayApprovalVO;
import com.inyoon.bookkureomi.kakao.KakaoPayService;
import com.inyoon.bookkureomi.user.MyAuthentication;

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
	
	@ApiOperation(value="포인트 세팅", notes="포인트를 세팅한다.")
	@ResponseBody 
	@GetMapping("/point/update")
	public Map<String, Object> settingPoint(
			@RequestParam("totalPoint") int totalPoint) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 	
			authentication.setPoint(totalPoint);
			
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}		
		
        return map;
	}
	
	@ApiOperation(value="포인트 확인", notes="포인트를 확인한다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/detail")
	public Map<String, Object> detailPoint(@AuthenticationPrincipal Login principal) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();
			
			int point = pointService.checkPoint(userNo);
			map.put("point", point);
		} else {
			map.put("point", 0);
		}		
		
        return map;
	}
	
	@ApiOperation(value="포인트 이용 내역", notes="포인트 이용 내역을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/list")
	public Map<String, Object> listPoint(
			@RequestParam("pageNo") int pageNo,
			@RequestParam("type") String type,
			@AuthenticationPrincipal Login principal) {
		
		int showCnt = 10;	//보여주는 개수
		int pointCnt = 0;	//리스트 개수
		int pageCnt = 0;
		
		List<Recharge> rechargeList = new ArrayList<Recharge>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userNo", userNo);					
			paramMap.put("type", type);					
			pointCnt = pointService.countRechargeList(paramMap);
			
			if(pointCnt > 0) {
				pageCnt = (pointCnt % showCnt == 0) ? (pointCnt / showCnt) : (pointCnt / showCnt + 1);		//페이지 개수
				int start = 1+(showCnt*(pageNo-1));
				int end = showCnt+(showCnt*(pageNo-1));
				
				paramMap.put("start", start);
				paramMap.put("end", end);					
				
				rechargeList = pointService.getRechargeList(paramMap);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rechargeList", rechargeList);
		map.put("pointCnt", pointCnt);
		map.put("pageCnt", pageCnt);
		
        return map;
    }
	
	@ApiOperation(value="포인트 충전", notes="포인트를 충전한다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/point/create")
	public Map<String, Object> rechargePoint(
			@RequestParam("rcPoint") String rcPoint,
			@RequestParam("rcMethod") String rcMethod,
			@AuthenticationPrincipal Login principal) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();
		
			Recharge recharge = new Recharge();
			recharge.setRcType("recharging");
			recharge.setRcMethod(rcMethod);	
			recharge.setRcPoint(Integer.parseInt(rcPoint));
			recharge.setUser(user);
			recharge.setRechargeNo(pointService.getRechargeNo(userNo));
			recharge.setTotalPoint(pointService.checkPoint(userNo) + Integer.parseInt(rcPoint));
			
			pointService.rechargePoint(recharge);
			
			map.put("totalPoint", recharge.getTotalPoint());
		}
				
        return map;
	}
	
	@ApiOperation(value="포인트 충전 성공", notes="포인트 충전에 성공한다.")
	@GetMapping("/kakao/success")
    public ModelAndView kakaoPaySuccess(
    		@RequestParam("pg_token") String pg_token,
    		ModelMap model,
    		HttpSession session,
			@AuthenticationPrincipal Login principal) {

		KakaoPayApprovalVO result = kakaoPayService.kakaoPayInfo(pg_token, session);
		model.put("info", result);
		
		if(result != null) {
			model.put("result", "success");
			if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
				//user
				MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
				User user = principal.getUser();
				int userNo = user.getUserNo();
			
				Recharge recharge = new Recharge();
				recharge.setRcType("recharging");
				recharge.setRcMethod("kakao");	
				recharge.setRcPoint(Integer.parseInt((String) session.getAttribute("total_amount")));
				recharge.setUser(user);
				recharge.setRechargeNo(pointService.getRechargeNo(userNo));
				recharge.setTotalPoint(pointService.checkPoint(userNo) + Integer.parseInt((String) session.getAttribute("total_amount")));
				
				pointService.rechargeKakaoPoint(recharge, result);
				
				authentication.setPoint(authentication.getPoint() + Integer.parseInt((String) session.getAttribute("total_amount")));
			}
		} else {
			model.put("result", "done");
		}
		
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
