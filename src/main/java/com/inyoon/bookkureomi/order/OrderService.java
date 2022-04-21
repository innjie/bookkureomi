package com.inyoon.bookkureomi.order;

import java.util.List;
import java.util.Map;

import com.inyoon.bookkureomi.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyoon.bookkureomi.cart.CartMapper;
import com.inyoon.bookkureomi.point.PointMapper;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private CartMapper cartMapper;


    //새로운 주문번호 가져오기
    public int getOrderNo() {
        return orderMapper.getOrderNo();
    }

    //새로운 주문상세번호 가져오기
    public int getODNo() {
        return orderMapper.getODNo();
    }

    //해당 유저번호의 일반 거래/경매 주문 목록 확인
    public List<Order> getOrderList(Map<String, Object> paramMap) {
        return orderMapper.getOrderList(paramMap);
    }

    //해당 일반 거래 주문번호의 상세 주문 정보 확인 (주문자 입장에서 주문 확인)
    public List<OrderDetail> getSaleOrder(int orderNo) {
        return orderMapper.getSaleOrder(orderNo);
    }

    //해당 경매 주문번호의 상세 주문 정보 확인 (주문자 입장에서 주문 확인)
    public List<OrderDetail> getAuctionOrder(int orderNo) {
        return orderMapper.getAuctionOrder(orderNo);
    }

    //해당 판매번호로 상세 주문 정보 확인 (판매자 입장에서 주문 확인)
    public OrderDetail getOrderBySale(int saleNo) {
        return orderMapper.getOrderBySale(saleNo);
    }
    public OrderDetail getOrderByAuctionNo(int auctionNo) {return orderMapper.getOrderByAuctionNo(auctionNo);}

    //주문(구입)
    @Transactional
    public void orderSale(List<OrderDetail> orderDetailList, Recharge rechargeUsing, List<Recharge> rechargeSellingList, boolean isCart) {
        orderMapper.orderSale(orderDetailList.get(0).getOrder());    //order에 추가
        for (int i = 0; i < orderDetailList.size(); i++) {
            orderMapper.orderDetailSale(orderDetailList.get(i));    //orderDetail에 추가
            orderMapper.updateSaleStateClose(orderDetailList.get(i).getSale().getSaleNo());    //state 변경

            pointMapper.rechargePoint(rechargeSellingList.get(i));        //포인트 충전 내역 추가

            if (isCart) {
                Sale sale = new Sale();
                sale.setSaleNo(orderDetailList.get(i).getSale().getSaleNo());
                User user = new User();
                user.setUserNo(rechargeUsing.getUser().getUserNo());

                CartItem cartItem = new CartItem();
                cartItem.setSale(sale);
                cartItem.setUser(user);

                cartMapper.deleteCartItem(cartItem);
            }
        }

        pointMapper.usePoint(rechargeUsing);            //포인트 사용 내역 추가
    }

    //주문 카운트
    public int countOrderList(Map<String, Object> paramMap) {
        return orderMapper.countOrderList(paramMap);
    }


    public void orderAuction(OrderDetail orderDetail, Recharge seller, Recharge buyer) {
        orderMapper.orderAuction(orderDetail.getOrder());
        orderMapper.orderDetailAuction(orderDetail);    //orderDetail에 추가

        pointMapper.rechargePoint(seller);        //포인트 충전 내역 추가

        Auction auction = new Auction();
        auction.setAuctionNo(orderDetail.getAuction().getAuctionNo());
        User user = new User();
        user.setUserNo(buyer.getUser().getUserNo());

        pointMapper.usePoint(buyer);
    }

    public void orderDetailAuction(OrderDetail orderDetail) {
        orderMapper.orderDetailAuction(orderDetail);
    }
    public Order getOrder(int orderNo) { return orderMapper.getOrder(orderNo);}

}
