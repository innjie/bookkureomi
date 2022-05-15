package com.inyoon.bookkureomi.user;

import com.inyoon.bookkureomi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class LogInOutController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public String loginForm(@ModelAttribute("login") Login login, HttpServletRequest request) {
        System.out.println("loginForm Controller");
        return "user/login";
    }
    
    @GetMapping("/user/login/check")
    @ResponseBody
    public Map<String, Object> loginCheck() {
    	Map<String, Object> map = new HashMap<String, Object>();
        
    	if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
    		map.put("login", "login");
		} else {
			map.put("login", "nologin");
		}        

        return map;
    }
    //logout
    @PostMapping("/user/logout")
    public String handleRequest(HttpSession session) throws Exception {
        System.out.println("logoutController");
        return "redirect:/book";
    }

}
