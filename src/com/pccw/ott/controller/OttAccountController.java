package com.pccw.ott.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pccw.ott.model.OttPermission;
import com.pccw.ott.model.OttRole;
import com.pccw.ott.model.OttUser;
import com.pccw.ott.service.OttPermissionService;
import com.pccw.ott.service.OttRoleService;
import com.pccw.ott.service.OttUserService;
import com.pccw.ott.util.MD5Util;

@Controller
@RequestMapping("/accountmanagement")
public class OttAccountController {

	private static Logger logger = LoggerFactory.getLogger(OttAccountController.class);

	@Autowired
	private OttUserService ottUserService;

	@Autowired
	private OttPermissionService ottPermissionService;

	@Autowired
	private OttRoleService ottRoleService;

	@RequestMapping("/user/doLogin.html")
	@ResponseBody
	public Map<String, Object> doLogin(OttUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			if (null == user || StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
				returnMap.put("success", false);
				returnMap.put("msg", "User name or password can't be empty!");
			} else {
				OttUser loginUser = ottUserService.findExactUser(user.getUsername(), MD5Util.md5(user.getPassword()));
				if (null != loginUser) {
					returnMap.put("success", true);
					returnMap.put("loginUser", loginUser);
					request.getSession().setAttribute("loginUser", loginUser);
				} else {
					returnMap.put("success", false);
					returnMap.put("msg", "User name or password does not match!");
				}
			}

		} catch (Exception e) {
			logger.error(e.toString());
			returnMap.put("success", false);
			returnMap.put("msg", "Login failed!");
		}

		return returnMap;

	}

	@RequestMapping("/user/doLogout.html")
	public void doLogout(HttpSession session) throws Exception {
		session.removeAttribute("loginUser");
	}

	// ---------------- Permission Management ----------------
	@RequestMapping("/permission/goToListPermissionPage.html")
	public ModelAndView goToListPermissionPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("permission_management");
		try {
			mv.addObject("canAddPermission", "Y");
			mv.addObject("canUpdatePermission", "Y");
			mv.addObject("canDeletePermission", "Y");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}

		return mv;
	}

	@RequestMapping("/permission/listPermission.html")
	@ResponseBody
	public Map<String, Object> listPermission(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String permissionName = request.getParameter("permissionNameForSearch");
		int first = (page - 1) * rows;
		int max = page * rows;
		List<OttPermission> list = ottPermissionService.findPermissionList(permissionName, first, max);
		Long totalCount = ottPermissionService.findCountByPermissionName(permissionName);
		returnMap.put("rows", list);
		returnMap.put("total", totalCount);
		return returnMap;
	}

	@RequestMapping("/permission/addPermission.html")
	@ResponseBody
	public Map<String, Object> addPermission(OttPermission permission, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == permission) {
				map.put("success", false);
				map.put("msg", "Add permission failed!");
			} else {
				// save url permission
				if (StringUtils.isBlank(permission.getPermissionName()) || StringUtils.isBlank(permission.getPermissionUrl())) {
					map.put("success", false);
					map.put("msg", "Add failed! Please complete the form.");
				} else {
					OttPermission ottPermission = ottPermissionService.findExactByPermissionName(permission.getPermissionName());
					if (null != ottPermission) {// Already exists
						map.put("success", false);
						map.put("msg", "Permission name already exists! Please check and then try again.");
					} else {// Not exists
						permission.setCreatedBy(loginUser.getUserId());
						permission.setUpdatedBy(loginUser.getUserId());
						ottPermissionService.saveOttPermission(permission);
						map.put("success", true);
						map.put("msg", "Add successfully!");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Add failed!");
		}
		return map;
	}

	@RequestMapping("/permission/updatePermission.html")
	@ResponseBody
	public Map<String, Object> updatePermission(OttPermission permission, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == permission) {
				map.put("success", false);
				map.put("msg", "Update failed!");
			} else {
				if (StringUtils.isBlank(permission.getPermissionName()) || StringUtils.isBlank(permission.getPermissionUrl())) {
					map.put("success", false);
					map.put("msg", "Update failed! Please complete the form.");
				} else {
					permission.setUpdatedBy(loginUser.getUserId());
					OttPermission ottPermission = ottPermissionService.findExactByPermissionName(permission.getPermissionName());
					if (null != ottPermission) {// Already exists
						if (ottPermission.getPermissionId().longValue() == permission.getPermissionId().longValue()) {// The
																														// same
																														// one
							ottPermissionService.updatePermission(permission);
							map.put("success", true);
							map.put("msg", "Update successfully!");
						} else {
							map.put("success", false);
							map.put("msg", "Permission already exists! Please check and then try again.");
						}
					} else {// Not exists
						ottPermissionService.updatePermission(permission);
						map.put("success", true);
						map.put("msg", "Update successfully!");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Update failed!");
		}
		return map;
	}

	@RequestMapping("/permission/deletePermission.html")
	@ResponseBody
	public Map<String, Object> deletePermission(OttPermission permission, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null == permission) {
				map.put("success", false);
				map.put("msg", "Delete failed! Please try again.");
			} else {
				ottPermissionService.deletePermission(permission.getPermissionId());
				map.put("success", true);
				map.put("msg", "Delete successfully!");
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Delete failed! Please try again.");
		}
		return map;
	}

	// ---------------- Role Management ----------------
	@RequestMapping("/role/goToListRolePage.html")
	public ModelAndView goToListRolePage() {
		ModelAndView mv = new ModelAndView("role_management");
		mv.addObject("canAddRole", "Y");
		mv.addObject("canUpdateRole", "Y");
		mv.addObject("canDeleteRole", "Y");
		mv.addObject("canEditRolePermissions", "Y");
		return mv;
	}

	@RequestMapping("/role/listRole.html")
	@ResponseBody
	public Map<String, Object> listRole(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String roleName = request.getParameter("roleNameForSearch");
		int first = (page - 1) * rows;
		int max = page * rows;
		List<OttRole> list = ottRoleService.findRoleList(roleName, first, max);
		Long totalCount = ottRoleService.findCountByRoleName(roleName);
		returnMap.put("rows", list);
		returnMap.put("total", totalCount);
		return returnMap;
	}

	@RequestMapping("/role/addRole.html")
	@ResponseBody
	public Map<String, Object> addRole(OttRole role, @RequestParam String permissions, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == role) {
				map.put("success", false);
				map.put("msg", "Add role failed!");
			} else {
				if (StringUtils.isBlank(role.getRoleName())) {
					map.put("success", false);
					map.put("msg", "Add failed! Please complete the form.");
				} else {
					OttRole ottRole = ottRoleService.findExactByRoleName(role.getRoleName());
					if (null != ottRole) {// Already exists
						map.put("success", false);
						map.put("msg", "Role name already exists! Please check and then try again.");
					} else {// Not exists
						role.setCreatedBy(loginUser.getUserId());
						role.setUpdatedBy(loginUser.getUserId());
						if (StringUtils.isNotBlank(permissions)) {
							String[] permissionIds = permissions.split(",");
							OttPermission permission = null;
							List<OttPermission> permisionList = new ArrayList<>();
							for (int i = 0; i < permissionIds.length; i++) {
								permission = ottPermissionService.loadPermissionById(Long.valueOf(permissionIds[i]));
								permisionList.add(permission);
							}
							role.setPermissionList(permisionList);
						}
						ottRoleService.saveOttRole(role);
						map.put("success", true);
						map.put("msg", "Add successfully!");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Add failed!");
		}
		return map;
	}

	@RequestMapping("/role/updateRole.html")
	@ResponseBody
	public Map<String, Object> updateRole(OttRole role, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == role) {
				map.put("success", false);
				map.put("msg", "Update failed!");
			} else {
				if (StringUtils.isBlank(role.getRoleName())) {
					map.put("success", false);
					map.put("msg", "Update failed! Please complete the form.");
				} else {
					role.setUpdatedBy(loginUser.getUserId());
					OttRole ottRole = ottRoleService.findExactByRoleName(role.getRoleName());
					if (null != ottRole) {// Already exists
						if (ottRole.getRoleId().longValue() == role.getRoleId().longValue()) {// The
																								// same
																								// one
							ottRoleService.updateRole(role);
							map.put("success", true);
							map.put("msg", "Update successfully!");
						} else {
							map.put("success", false);
							map.put("msg", "Role already exists! Please check and then try again.");
						}
					} else {// Not exists
						ottRoleService.updateRole(role);
						map.put("success", true);
						map.put("msg", "Update successfully!");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Update failed!");
		}
		return map;
	}

	@RequestMapping("/role/deleteRole.html")
	@ResponseBody
	public Map<String, Object> deleteRole(OttRole role, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null == role) {
				map.put("success", false);
				map.put("msg", "Delete failed! Please try again.");
			} else {
				ottRoleService.deleteRole(role.getRoleId());
				map.put("success", true);
				map.put("msg", "Delete successfully!");
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Delete failed! Please try again.");
		}
		return map;
	}
	
	@RequestMapping("/role/listAllPermission.html")
	@ResponseBody
	public List<OttPermission> listAllPermission() {
		List<OttPermission> list = ottPermissionService.findAllPermission();
		return list;
	}
	
	// ----------------- User Management -----------------
	@RequestMapping("/user/goToListUserPage.html")
	public ModelAndView goToListUserPage() {
		ModelAndView mv = new ModelAndView("user_management");
		mv.addObject("canAddUser", "Y");
		mv.addObject("canUpdateUser", "Y");
		mv.addObject("canDeleteUser", "Y");
		mv.addObject("canEditUserRole", "Y");
		return mv;
	}
	
	@RequestMapping("/user/listUser.html")
	@ResponseBody
	public Map<String, Object> listUser(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) {
		Map<String, Object> returnMap = new HashMap<>();
		String usernameForSearch = request.getParameter("usernameForSearch");
		int first = (page - 1) * rows;
		int max = page * rows;
		List<OttUser> list = ottUserService.findUserByUserName(usernameForSearch, first, max);
		Long totalCount = ottUserService.findCountByUserName(usernameForSearch);
		returnMap.put("rows", list);
		returnMap.put("page", totalCount);
		return returnMap;
	}
	
	@RequestMapping("/user/addUser.html")
	@ResponseBody
	public Map<String, Object> addUser(OttUser user, @RequestParam String userRole, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == user) {
				map.put("success", false);
				map.put("msg", "Add user failed!");
			} else {
				if (StringUtils.isBlank(user.getUsername()) || StringUtils.isNotBlank(user.getUserEmail())) {
					map.put("success", false);
					map.put("msg", "Add failed! Please complete the form.");
				} else {
					OttUser ottUser = ottUserService.findExactByUsername(user.getUsername());
					if (null != ottUser) {// Already exists
						map.put("success", false);
						map.put("msg", "User name already exists! Please check and then try again.");
					} else {// Not exists
						user.setCreatedBy(loginUser.getUserId());
						user.setUpdatedBy(loginUser.getUserId());
						if (StringUtils.isNotBlank(userRole)) {
							OttRole role = ottRoleService.findRoleById(Long.valueOf(userRole));
							if (role != null) {
								user.setRole(role);
							}
						}
						ottUserService.saveOttUser(user);
						map.put("success", true);
						map.put("msg", "Add successfully!");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Add failed!");
		}
		return map;
	}
	
	@RequestMapping("/user/updateUser.html")
	@ResponseBody
	public Map<String, Object> updateUser(OttUser user, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
		return map;
	}

	@RequestMapping("/user/deleteUser.html")
	@ResponseBody
	public Map<String, Object> deleteUser(OttUser user, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
		try {
			if (null == user) {
				map.put("success", false);
				map.put("msg", "Delete failed! Please try again.");
			} else if (loginUser.getUserId() == user.getUserId()) {
				map.put("success", false);
				map.put("msg", "Delete failed! Cannot delete current user.");
			} else {
				ottUserService.deleteUser(user.getUserId());
				map.put("success", true);
				map.put("msg", "Delete successfully!");
			}
		} catch (Exception e) {
			logger.error("", e);
			map.put("success", false);
			map.put("msg", "Delete failed! Please try again.");
		}
		return map;
	}
	
	@RequestMapping("/user/listAllRole.html")
	@ResponseBody
	public List<OttRole> listAllRole() {
		List<OttRole> list = ottRoleService.findAllRole();
		return list;
	}
}
