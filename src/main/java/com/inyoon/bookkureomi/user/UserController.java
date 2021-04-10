package com.inyoon.bookkureomi.user;

import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.inyoon.bookkureomi.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class UserController {

    private static final String JOINFORM = "user/all/join";
    //insert userForm

    @Autowired
    private GenreService genreService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/genre/list")
    public Map<String, Object> genreList() {
        System.out.println("genreList in");
        List<Genre> genreList = new ArrayList<>();
        genreList = genreService.getGenreList();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("genreList", genreList);

        System.out.println(genreList);
        return map;
    }

    //join
    @GetMapping("/user/join")
    public String userForm(@ModelAttribute("user") User user, HttpServletRequest request) {
        return JOINFORM;
    }

//    @PostMapping("/user/overlapId")
//    @ResponseBody
//    public String overlapId(@RequestParam String inputId) throws Exception {
//        System.out.println("overlapId in");
//        User user = userService.getUserById(inputId);
//        String usable = user != null ? "" : "Y";
//        return usable;
//    }

    //insert user
    @PostMapping("/user/join")
    @ResponseBody
    public String insert(@Validated @ModelAttribute("userCommand") User user, BindingResult result) throws Exception {
        System.out.println("insert join");
        return "good";
    }

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
