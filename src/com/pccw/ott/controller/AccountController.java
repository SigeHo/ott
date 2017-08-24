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
import org.springframework.ui.Model;
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
public class AccountController {

	private static Logger logger = LoggerFactory.getLogger(AccountController.class);

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
		returnMap.put("rows", list);
		returnMap.put("total", list.size());
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
		returnMap.put("rows", list);
		returnMap.put("total", list.size());
		return returnMap;
	}

	@RequestMapping("/role/addRole.html")
	@ResponseBody
	public Map<String, Object> addRole(OttRole role, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OttUser loginUser = (OttUser) request.getSession().getAttribute("loginUser");
			if (null == role) {
				map.put("success", false);
				map.put("msg", "Add role failed!");
			} else {
				// save url role
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

	@RequestMapping("/role/listRolePermissions.html")
	@ResponseBody
	public Map<String, Object> listRolePermissions(Model model, OttRole role) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			if (null == role) {
				returnMap.put("total", 0);
				returnMap.put("rows", new ArrayList<OttPermission>());
			} else {
				List<OttPermission> permissions = ottRoleService.listRolePermissions(role.getRoleId());
				returnMap.put("total", permissions.size());
				returnMap.put("rows", permissions);
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return returnMap;
	}
}
