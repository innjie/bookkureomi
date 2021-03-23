package com.inyoon.bookkureomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.dao.TestDAO;
import com.inyoon.bookkureomi.domain.Test;

//@Repository
@Service
public class TestService {

	@Autowired
	private TestDAO testDAO;
	
	public Test findUser(String id) {
		return testDAO.findUser(id);
	}
}
