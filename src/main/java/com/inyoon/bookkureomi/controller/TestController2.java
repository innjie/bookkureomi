package com.inyoon.bookkureomi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inyoon.bookkureomi.domain.Test;
import com.inyoon.bookkureomi.service.TestService;

@Controller
public class TestController2 {

	@Autowired
	private TestService testService;

	@GetMapping("/test2")
	@ResponseBody //@RestController 시 생략 가능
	public Map<String, Object> testPage2(
			@RequestParam("id") String id) {
		Test test = new Test();
		test = testService.findUser(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", test);
		map.put("phone", test.getPhone());
		
		System.out.println("test222222222222222");

        return map;
    }
}
