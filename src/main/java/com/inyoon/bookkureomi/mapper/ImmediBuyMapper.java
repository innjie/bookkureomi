package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.ImmediBuy;
import com.inyoon.bookkureomi.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImmediBuyMapper {
    public List<Order> getImmediBuyListByUserNo(int userNo);
    public Order getImmediBuy(int orderNo);
    public void insertImmediBuy(Order order);
}
