package com.inyoon.bookkureomi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Repository
@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;
	
	public Test findUser(String id) {
		return testMapper.findUser(id);
	}
}
