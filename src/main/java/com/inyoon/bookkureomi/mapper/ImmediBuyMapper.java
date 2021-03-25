package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.ImmediBuy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImmediBuyMapper {
    public List<ImmediBuy> getImmediBuyList(int userNo);
    public List<ImmediBuy> getImmediBuyListByUserNo(int userNo);
    public ImmediBuy getImmediBuy(int immediNo);
    public void insertImmediBuy(ImmediBuy immediBuy);
}
