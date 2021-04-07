package com.inyoon.bookkureomi.auction;

import com.inyoon.bookkureomi.order.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImmediBuyMapper {
    public List<Order> getImmediBuyListByUserNo(int userNo);
    public Order getImmediBuy(int orderNo);
    public void insertImmediBuy(Order order);
}
