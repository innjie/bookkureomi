package com.inyoon.bookkureomi.address;

import com.inyoon.bookkureomi.domain.Address;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.user.MyAuthentication;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    //create address ... insert
    @ResponseBody
    @PostMapping("/address/insert")
    public Map<String, Object> insertAddress(@RequestParam("aName") String aName,
                                @RequestParam("address") String address,
                                @RequestParam("zipcode") String zipcode
    ) throws Exception {
        System.out.println("insert Address in");
        Address addressDTO = new Address();
        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getUser();

        addressDTO.setAddr(address);
        addressDTO.setZipcode(zipcode);
        addressDTO.setAName(aName);
        addressDTO.setUserNo(user.getUserNo());
        addressService.insertAddress(addressDTO);

        Map <String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        map.put("addrNo", addressDTO.getAddrNo());

        return map;

    }

    //view address
    @GetMapping("/address/detail")
    @ResponseBody
    public Map<String, Object> viewAddress(@RequestParam("addrNo") int addrNo) {
        Address address = addressService.getAddress(addrNo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address", address);
        return map;
    }

    //update address
    @ResponseBody
    @PutMapping("/address/update")
    public Map<String, Object> updateAddress(@RequestParam("addrNo") String addrNo,
                                             @RequestParam("aName") String aName,
                                             @RequestParam("addr") String addr,
                                             @RequestParam("zipcode") String zipcode) {
        Map<String, Object> map = new HashMap<String, Object>();

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Address address = new Address();
            address.setAddrNo(Integer.parseInt(addrNo));
            address.setAddr(addr);
            address.setZipcode(zipcode);
            address.setAName(aName);
            addressService.updateAddress(address);

            map.put("result", "success");
        } else {
            map.put("result", "fail");
            map.put("reason", "로그인 후 이용이 가능합니다.");
        }
        return map;
    }

    //delete address
    @ResponseBody
    @DeleteMapping("/address/delete")
    public Map<String, Object> delete(@RequestParam("addrNo") String addrNo) throws Exception {
        Map<String, Object> map = new HashMap<>();

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            addressService.deleteAddress(Integer.parseInt(addrNo));

            map.put("result", "success");
        } else {
            map.put("result", "fail");
            map.put("reason", "로그인 후 이용이 가능합니다.");
        }
        return map;
    }

}
