package com.inyoon.bookkureomi.address;

import com.inyoon.bookkureomi.domain.Address;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.user.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping ("/mypage/address/page")
    public String viewAddressPage() {return "mypage/address";}
    //view address by page
    @GetMapping ("/mypage/address/list")
    @ResponseBody
    public Map<String, Object> listAddress(@RequestParam("pageNo") int pageNo) {
        int showCnt = 12;
        int addressCnt = 0;
        int pageCnt = 0;

        List<Address> addressList = new ArrayList<>();
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("annonymousUser")) {
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.
                    getContext().getAuthentication();
            User user = (User) authentication.getUser();
            int userNo = user.getUserNo();
            addressCnt = addressService.countAddressList(userNo);

            if(addressCnt > 0) {
                pageCnt = (addressCnt % showCnt == 0) ? (addressCnt / showCnt) : (addressCnt / showCnt + 1);
                int start = 1 + (showCnt * (pageNo - 1));
                int end = showCnt + (showCnt * (pageNo - 1));

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("start", start);
                paramMap.put("end", end);

                addressList = addressService.getAddressList(userNo);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addressList", addressList);
        map.put("addressCnt", addressCnt);
        map.put("pageCnt", pageCnt);
        return map;

    }
//
//    //view address
//    @RequestMapping("/address/view.do")
//    public ModelAndView viewAddress(@RequestParam("addrNo") int addrNo)
//            throws Exception {
//    }
//
//    //create address ... form
//    @RequestMapping(value="/address/insert.do", method=RequestMethod.GET)
//    public String insertAddressForm(@ModelAttribute("addressCommand") AddressCommand
//                                            addressCommand, HttpServletRequest request) {
//
//    }
//
//    //create address ... insert
//    @RequestMapping(value="/address/insert.do", method=RequestMethod.POST)
//    public String insertAddress(@Valid @ModelAttribute("addressCommand") AddressCommand
//                                        addressCommand, BindingResult result, HttpServletRequest request)
//            throws Exception {
//    }
//
//    //update address ... form
//    @RequestMapping(value="/address/update.do", method=RequestMethod.GET)
//    public String updateAddressForm(@ModelAttribute("addressCommand") AddressCommand
//                                            addressCommand, HttpServletRequest request) {
//    }
//
//    //update address ... update
//    @RequestMapping(value="/address/update.do", method= RequestMethod.POST)
//    public String update(@Valid @ModelAttribute("addressCommand") AddressCommand
//                                 addressCommand, BindingResult result) throws Exception {
//    }
//    //delete address
//    @RequestMapping("/address/delete.do")
//    public String delete(@RequestParam("addrNo") int addrNo) throws Exception {
//    }

}
