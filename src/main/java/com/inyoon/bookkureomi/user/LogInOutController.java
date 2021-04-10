package com.inyoon.bookkureomi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
//
//    //login
//    @RequestMapping(value = “/user/login.do”, method = RequestMethod.POST)
//    public ModelAndView login(HttpServletRequest request, @Valid @ModelAttribute("login")
//            LoginCommand loginCommand, BindingResult result) throws Exception {
//
//    }
//
//    //logout
//    @RequestMapping(“/user/logout.do”)
//    public String handleRequest(HttpSession session) throws Exception {
//
//    }

}
