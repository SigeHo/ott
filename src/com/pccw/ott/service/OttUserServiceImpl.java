package com.pccw.ott.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.ott.dao.OttUserDao;
import com.pccw.ott.model.OttUser;

@Service("ottUserService")
@Transactional
public class OttUserServiceImpl implements OttUserService {
	
	@Autowired
	private OttUserDao ottUserDao;

	@Override
	public Date getSysdate() {
		return ottUserDao.getSysdate();
	}

	@Override
	public OttUser findExactUser(String username, String password) {
		return ottUserDao.findUserByUsernameAndPassword(username, password);
	}

}
