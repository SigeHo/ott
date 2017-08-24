package com.pccw.ott.service;

import java.util.Date;

import com.pccw.ott.model.OttUser;

public interface OttUserService {
	
	public Date getSysdate();

	public OttUser findExactUser(String username, String password);

}
