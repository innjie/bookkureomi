package com.inyoon.bookkureomi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.inyoon.bookkureomi.domain.Test;
import com.inyoon.bookkureomi.service.TestService;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
	@GetMapping("/test")
    public String testPage1(Model model) {	
		System.out.println("test11111111111 - get");

        return "test/test";
    }
	
	@GetMapping("/test1")
    public ModelAndView testPage2(
    		@RequestParam("id") String id) {
		Test test = new Test();
		test = testService.findUser(id);
		
		System.out.println("test11111111111 - post");
		
        return new ModelAndView("test/test", "test", test);
    }
}
