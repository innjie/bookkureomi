package com.inyoon.bookkureomi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

//    //insert userForm
//    @RequestMapping(value=”/user/insert.do”,
//            method= RequestMethod.GET)
//    public String userForm(
//            @ModelAttribute(“userCommand”) UserCommand userCommand, HttpServletRequest request) {
//    }
//
//    //insert user
//    @requestMapping(value=”/user/insert.do”, method =
//            RequestMethod.POST)
//    public String insert(
//            @Valid @ModelAttribute(“userCommand”) UserCommand userCommand, BindingResult result) throws Exception {
//    }
//
//    //update user
//    @RequestMapping(value = “/user/update.do”,
//            method=RequestMethod.GET)
//    public String updateUserForm(
//            @ModelAttribute(“userCommand”) UserCommand
//                    userCommand, HttpServletRequest request) {
//    }
//
//    @RequestMapping(value = “/user/update.do”,
//            method=RequestMethod.POST)
//    public String updateUserForm(
//            @Valid @ModelAttribute(“userCommand”) UserCommand
//                    userCommand, BindingResult result, HttpServletRequest request) {
//    }
//
//    //delete user
//    @RequestMapping(value = “/user/delete.do”)
//    public String deleteUser(int userNo) {
//
//    }
//
//    //view User
//    @requestMapping(value = “/user/view.do”)
//    public String getUser(int userNo) {
//    }
}
