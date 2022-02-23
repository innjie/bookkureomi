package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.address.AddressService;
import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.*;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.point.PointService;
import com.inyoon.bookkureomi.user.Login;
import com.inyoon.bookkureomi.user.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/book")
public class ImmediBuyController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private PointService pointService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private AddressService addressService;
    //    //view ImmediBuy List(my)
//    @RequestMapping(“/immediBuy/list.do”)
//    public String myImmediBuyList(ModelMap model,
//                                  HttpServletRequest request) throws Exception {
//    }
//
//    @RequestMapping(“/immediBuy/listPage.do”)
//    public String myImmediBuyListPage(
//            @RequestParam("page") String page,
//            @ModelAttribute("immdeBuy") PagedListHolder<ImmediBuy>
//                    immediBuy, BindingResult result) throws Exception {
//    }
    @ResponseBody
    @PostMapping("/immediate/insert")
    public Map<String, Object> ImmediateOrder(
            @RequestParam("auctionNo") int auctionNo,
            @RequestParam("pAddress") String pAddress,
            @RequestParam("rName") String rName,
            @RequestParam("rPhone") String rPhone,
            @RequestParam("rAddress") String rAddress,
            @AuthenticationPrincipal Login principal) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anounymous")) {
            MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
            User user = (User) principal.getUser();
            int userNo = user.getUserNo();

            int orderNo = orderService.getOrderNo();
            int odNo = orderService.getODNo();

            int nowPoint = pointService.checkPoint(user.getUserNo());	//현재 포인트 확인

            //get auction
            Auction auction = auctionService.getAuction(auctionNo);
            int immediPrice = auction.getImmediPrice();

            if(nowPoint < immediPrice) {
                map.put("result", "fail");
                map.put("reason", "※주문실패※\n주문 금액보다 충전된 포인트가 적습니다.");
            } else {
                //set order
                Order order = new Order();
                order.setOrderNo(orderNo);
                order.setPAddress(pAddress);
                order.setRName(rName);
                order.setRPhone(rPhone);
                order.setRAddress(rAddress);
                order.setTotal(immediPrice);
                order.setUser(user);
                order.setInfo("");

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setAuction(auction);
                orderDetail.setOrder(order);
                orderDetail.setOdNo(odNo);

                //포인트 - 구매자
                Recharge recharge = new Recharge();
                recharge.setRechargeNo(pointService.getRechargeNo(userNo));
                recharge.setTotalPoint(nowPoint - immediPrice);
                recharge.setRcType("using");
                recharge.setRcPoint(-1 * immediPrice);
                recharge.setUser(user);

                //포인트 - 판매자
                User seller = new User();
                int sellerNo = auction.getUser().getUserNo();
                seller.setUserNo(sellerNo);
                Recharge sellerRecharge = new Recharge();
                int sellerPoint = pointService.checkPoint(sellerNo);
                sellerRecharge.setTotalPoint(sellerPoint + (int)(0.9 * auction.getImmediPrice()));
                sellerRecharge.setRechargeNo(pointService.getRechargeNo(sellerNo));
                sellerRecharge.setRcType("recharging");
                sellerRecharge.setRcMethod("selling");
                sellerRecharge.setRcPoint((int)(0.9 * auction.getImmediPrice()));
                sellerRecharge.setUser(seller);

                //order
                orderService.orderAuction(order);
                map.put("result", "success");
                map.put("order", order);
                map.put("totalPoint", recharge.getTotalPoint());

                //auction closed
                auction.setState("close");
                auctionService.updateAuction(auction);
            }
        } else {
            map.put("result", "fail");
            map.put("reason", "로그인 후 이용이 가능합니다.");
        }
        return map;
    }


//    //immediBuy
//    @RequestMapping(value = "/immediBuy/insert.do",  method = RequestMethod.POST)
//    public ModelAndView imPur(@RequestParam("auctionNo") int auctionNo, @Valid
//    @ModelAttribute("immediBuyCommand") ImmediBuyCommand immediBuyCommand, BindingResult bindingResult, HttpServletRequest request) throws Exception {
//    }

}
