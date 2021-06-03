package com.inyoon.bookkureomi.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.point.PointMapper;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private PointMapper pointMapper;
	

	//새로운 주문번호 가져오기
	public int getOrderNo() {
		return orderMapper.getOrderNo();
	}
	
	//새로운 주문상세번호 가져오기
	public int getODNo() {
		return orderMapper.getODNo();
	}

	//해당 유저번호의 일반 거래 주문 목록 확인
	public List<Order> getSaleOrderList(int userNo){
		return orderMapper.getSaleOrderList(userNo);
	}
	
	//해당 유저번호의 경매 주문 목록 확인
	public List<Order> getAuctionOrderList(int userNo){
		return orderMapper.getAuctionOrderList(userNo);
	}
	
	//해당 일반 거래 주문번호의 상세 주문 정보 확인 (주문자 입장에서 주문 확인)
	public List<OrderDetail> getSaleOrder(int orderNo){
		return orderMapper.getSaleOrder(orderNo);
	}
	
	//해당 경매 주문번호의 상세 주문 정보 확인 (주문자 입장에서 주문 확인)
	public List<OrderDetail> getAuctionOrder(int orderNo){
		return orderMapper.getAuctionOrder(orderNo);
	}
	
	//해당 판매번호로 상세 주문 정보 확인 (판매자 입장에서 주문 확인)
	public OrderDetail getOrderBySale (int saleNo) {
		return orderMapper.getOrderBySale(saleNo);
	}
	
	//주문(구입)
	@Transactional
	public void orderSale(OrderDetail orderDetail, Recharge rechargeUsing, Recharge rechargeSelling){
		orderMapper.orderSale(orderDetail.getOrder());	//order에 추가	
		orderMapper.orderDetailSale(orderDetail);		//orderDetail에 추가
		
		orderMapper.updateSaleStateClose(orderDetail.getSale().getSaleNo());	//state 변경
		
		pointMapper.usePoint(rechargeUsing);			//포인트 사용 내역 추가
		pointMapper.setPoint(rechargeUsing);			//포인트 판매 세팅

		pointMapper.rechargePoint(rechargeSelling);		//포인트 사용 내역 추가
		pointMapper.setPoint(rechargeSelling);			//포인트 판매 세팅
	}
	
	
	

	
	
	public void orderAuction(Order order){
		orderMapper.orderAuction(order);
	}
	public void orderDetailAuction(OrderDetail orderDetail){
		orderMapper.orderDetailAuction(orderDetail);
	}
	
	
	/*	
	//orderService.orderSale에 포함
	public void orderSale(Order order){
		orderMapper.orderSale(order);
	}
	//orderService.orderSale에 포함
	public void orderDetailSale(OrderDetail orderDetail){
		orderMapper.orderDetailSale(orderDetail);
		orderMapper.updateSaleStateClose(orderDetail.getSale().getSaleNo());
	}
*/	
}
