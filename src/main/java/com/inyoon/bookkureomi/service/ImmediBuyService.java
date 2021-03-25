package com.inyoon.bookkureomi.service;

import com.inyoon.bookkureomi.domain.ImmediBuy;
import com.inyoon.bookkureomi.mapper.ImmediBuyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImmediBuyService {
    @Autowired
    private ImmediBuyMapper immediBuyMapper;

    public List<ImmediBuy> getImmediBuyList(int userNo) {
        return immediBuyMapper.getImmediBuyList(userNo);
    }
    public List<ImmediBuy> getImmediBuyListByUserNo(int userNo) {
        return immediBuyMapper.getImmediBuyListByUserNo(userNo);
    }
    public ImmediBuy getImmediBuy(int immediNo) {
        return immediBuyMapper.getImmediBuy(immediNo);
    }
    public void insertImmediBuy(ImmediBuy immediBuy) {
        immediBuyMapper.insertImmediBuy(immediBuy);
    }

}
