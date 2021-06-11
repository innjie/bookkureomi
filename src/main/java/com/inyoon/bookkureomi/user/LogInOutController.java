package com.inyoon.bookkureomi.user;

import com.inyoon.bookkureomi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    //login
//    @PostMapping("/user/login")
//    @ResponseBody
//    public Map<String, Object> login(HttpServletRequest request,
//                              @RequestParam("id") String id, @RequestParam("pw") String pw) throws Exception {
//        System.out.println("in login Controller");
//        User user = new User();
//        user.setId(id);
//        user.setPw(pw);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        String loginResult = userService.login(user);
//        map.put("result", "success");
//
//
//        if(loginResult.equals("matched")) {
//            HttpSession httpSession = request.getSession();
//            user = userService.getUserById(id);
//            httpSession.setAttribute("user", user);
//            System.out.println("login Success");
//
//            map.put("username", user.getName());
//            System.out.println(user.getName());
//            map.put("result", "success");
//
//            return map;
//        } else {
//            map.put("result", "login failed");
//            return map;
//
//        }
//    }

    //logout
    @PostMapping("/user/logout")
    public String handleRequest(HttpSession session) throws Exception {
        System.out.println("logoutController");
        return "redirect:/book";
    }

}
