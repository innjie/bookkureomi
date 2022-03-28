package com.inyoon.bookkureomi.user;

import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.inyoon.bookkureomi.domain.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/book")
public class UserController {

    private static final String JOINFORM = "user/join";
    private static final int GENRESIZE = 3;
    //insert userForm

    @Autowired
    private GenreService genreService;
    @Autowired
    private UserService userService;
    
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String Main(@ModelAttribute("user") User user, HttpServletRequest request)
    {
        return "index";
    }
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

    @PostMapping("/user/overlapId")
    @ResponseBody
    public Map<String, Object> overlapId(@RequestParam ("id") String inputId) throws Exception {
       
    	System.out.println("overlapId in");
        User user = userService.getUserById(inputId);
        
        Map<String, Object> map = new HashMap<String, Object>();
        if(user == null) {
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        
        return map;
    }

    //insert user
    @PostMapping("/user/join")
    @ResponseBody
    public Map<String, Object> insert(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("genreArray[]") List<String> genreArray
                         ) throws Exception {
        System.out.println(id);
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(pw));
        user.setName(name);
        user.setPhone(phone);
        user.setGenreArray(genreArray);

        Iterator<String> it =  genreArray.iterator();

        for(int i = 0; i < GENRESIZE; i++) {
            Genre genre = new Genre();
            if(it.hasNext()) {
                switch (i) {
                    case 0:
                        genre = genreService.getGenreByName(it.next().toString());
                        System.out.println(genre.getGenreNo());
                        user.setFirstGenre(genre.getGenreNo());
                        break;
                    case 1:
                        genre = genreService.getGenreByName(it.next().toString());
                        System.out.println(genre.getGenreNo());
                        user.setSecondGenre(genre.getGenreNo());
                        break;
                    case 2:
                        genre = genreService.getGenreByName(it.next().toString());
                        System.out.println(genre.getGenreNo());
                        user.setThirdGenre(genre.getGenreNo());
                        break;
                }
            }
        }
        user.setUserNo(userService.getUserNo());
        
        userService.insertUser(user);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        map.put("userNo", user.getUserNo());

        System.out.println(map);
        return map;
    }

    //mypage
    @GetMapping("/mypage/page")
    public String mypagePage(@AuthenticationPrincipal Login principal) {
        return "mypage/page";
    }
    //mypage get UserInfo
    @GetMapping("/mypage/info")
    @ResponseBody
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Login principal) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUser(principal.getUserNo());

        map.put("user", user);
        map.put("result", "success");

        return map;
    }
}
