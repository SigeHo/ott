package com.pccw.ott.service;

import java.util.List;

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
	public OttUser findExactUser(String username, String password) {
		return ottUserDao.findUserByUsernameAndPassword(username, password);
	}

	@Override
	public List<OttUser> findUserByUserName(String userNameForSearch, int first, int max) {
		return ottUserDao.findUserByUserName(userNameForSearch, first, max);
	}

	@Override
	public Long findCountByUserName(String userNameForSearch) {
		return ottUserDao.findCountByUserName(userNameForSearch);
	}

	@Override
	public void deleteUser(Long userId) {
		ottUserDao.deleteUser(userId);		
	}

	@Override
	public OttUser findExactByUsername(String username) {
		return ottUserDao.findExactByUsername(username);
	}

	@Override
	public void saveOttUser(OttUser user) {
		ottUserDao.saveUser(user);		
	}

	@Override
	public void updateOttUser(OttUser user) {
		ottUserDao.updateUser(user);		
	}

	@Override
	public void changePassword(Long userId, String newPassword) {
		ottUserDao.updateUserPassword(userId, newPassword);
	}

}
