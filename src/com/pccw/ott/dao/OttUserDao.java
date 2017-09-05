package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttUser;

public interface OttUserDao {

	public OttUser findUserByUsernameAndPassword(String username, String password);

	public List<OttUser> findUserByUserName(String userNameForSearch, int first, int max);

	public Long findCountByUserName(String userNameForSearch);

	public void deleteUser(Long userId);

	public OttUser findExactByUsername(String username);

	public void saveUser(OttUser user);

	public void updateUser(OttUser user);

	public void updateUserPassword(Long userId, String newPassword);
}
