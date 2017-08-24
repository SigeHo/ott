package com.pccw.ott.dao;

import java.util.Date;

import com.pccw.ott.model.OttUser;

public interface OttUserDao {
	public Date getSysdate();

	public OttUser findUserByUsernameAndPassword(String username, String password);
}
