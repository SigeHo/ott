package com.pccw.ott.service;

import java.util.List;

import com.pccw.ott.model.OttUser;

public interface OttUserService {
	
	public OttUser findExactUser(String username, String password);

	public List<OttUser> findUserByUserName(String userNameForSearch, int first, int max);

	public Long findCountByUserName(String userNameForSearch);

	public void deleteUser(Long userId);

	public OttUser findExactByUsername(String username);

	public void saveOttUser(OttUser user);

	public void updateOttUser(OttUser user);

	public void changePassword(Long userId, String newPassword);

}
