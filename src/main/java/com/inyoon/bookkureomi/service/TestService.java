package com.inyoon.bookkureomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Test;
import com.inyoon.bookkureomi.mapper.TestMapper;

//@Repository
@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;
	
	public Test findUser(String id) {
		return testMapper.findUser(id);
	}
}
