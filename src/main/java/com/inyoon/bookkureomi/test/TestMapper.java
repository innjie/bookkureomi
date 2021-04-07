package com.inyoon.bookkureomi.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
	public Test findUser(String id);
}
