package com.inyoon.bookkureomi.service;

import com.inyoon.bookkureomi.domain.ImmediBuy;
import com.inyoon.bookkureomi.domain.Order;
import com.inyoon.bookkureomi.mapper.ImmediBuyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImmediBuyService {
    @Autowired
    private ImmediBuyMapper immediBuyMapper;


    public List<Order> getImmediBuyListByUserNo(int userNo) {
        return immediBuyMapper.getImmediBuyListByUserNo(userNo);
    }
    public Order getImmediBuy(int orderNo) {
        return immediBuyMapper.getImmediBuy(orderNo);
    }
    public void insertImmediBuy(Order order) {
        immediBuyMapper.insertImmediBuy(order);
    }

}
