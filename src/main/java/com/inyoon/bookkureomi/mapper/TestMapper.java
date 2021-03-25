package com.inyoon.bookkureomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.inyoon.bookkureomi.domain.Test;

@Mapper
public interface TestMapper {
	public Test findUser(String id);
}
