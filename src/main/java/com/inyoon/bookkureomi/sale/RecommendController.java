package com.inyoon.bookkureomi.sale;

import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.user.Login;
import com.inyoon.bookkureomi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class RecommendController {
    @Autowired
    SaleService saleService;
    @Autowired
    UserService userService;

    @GetMapping("/recommend/page")
    public String recommendPage() { return "sale/recommend"; }

    @GetMapping("/recommend/user")
    @ResponseBody
    public Map<String, Object> recommendUser(@AuthenticationPrincipal Login login) {
        Map<String, Object> map = new HashMap<>();

        User user = userService.getUser(login.getUserNo());
        List<Sale> saleList = new ArrayList<>();
        List<Integer> genreList = new ArrayList<>();
        genreList.add(user.getFirstGenre());
        genreList.add(user.getSecondGenre());
        genreList.add(user.getThirdGenre());

        try {
            saleList = saleService.getRecommendSale(user.getUserNo());
            for(Sale s : saleList) {
                System.out.println(s);
            }
            map.put("result", "success");
            map.put("saleList", saleList);
        } catch (Exception e) {
            map.put("result", "fail");
            map.put("message", "정보 수집에 오류가 발생했습니다.");
        }
        return map;
    }
    @GetMapping("/recommend/sale")
    @ResponseBody
    public Map<String, Object> recommendSale() {
        Map<String, Object> map = new HashMap<>();


        return map;
    }
}
