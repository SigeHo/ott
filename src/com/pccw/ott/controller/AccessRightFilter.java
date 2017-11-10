package com.pccw.ott.controller;

/**
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pccw.ott.model.OttUser;
import com.pccw.ott.util.Constants;

public class AccessRightFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(AccessRightFilter.class);
	
	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String currentURI = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		
		//substring() 
		String permissionURL = currentURI.substring(contextPath.length());
		//String currentURL = httpRequest.getRequestURL().toString();
		//=currentURL=http://localhost:8080/PlanManagement/list_table_data.html
		//=currentURI=/PlanManagement/list_table_data.html
		//=contextPath=/PlanManagement
		//=permissionURL=/list_table_data.html
		
		logger.debug("currentURI:"+currentURI);
		logger.debug("contextPath:"+contextPath);
		logger.debug("permissionURL:"+permissionURL);
		 
		
		if( permissionURL.equals(Constants.USER_DO_LOGIN_URL)
		  ||permissionURL.equals(Constants.USER_DO_LOGOUT_URL)	
		  ||permissionURL.equals(Constants.MAIN_PAGE_URL)
		  ||permissionURL.equals(Constants.NO_PERMISSION_URL)
		  
//		  ||permissionURL.equals(Constants.USER_LIST_USER_URL)
//		  ||permissionURL.equals(Constants.USER_LIST_USER_ROLES_URL)
//		  ||permissionURL.equals(Constants.USER_GET_USER_PARAM_URL)
//		  
//		  ||permissionURL.equals(Constants.ROLE_LIST_ROLE_URL)
//		  ||permissionURL.equals(Constants.ROLE_LIST_ROLE_PERMISSIONS_URL)
//		  
//		  ||permissionURL.equals(Constants.PERMISSION_LIST_PERMISSION_URL)
//		  ||permissionURL.equals(Constants.PERMISSION_GET_ALL_TABLE_FIELDS_URL)		  
		  ) {
			// do nothing, ignore session checking
		} 
		else {
			OttUser loginUser = (OttUser) httpRequest.getSession().getAttribute("loginUser");
			if (null == loginUser) {
				httpResponse.sendRedirect(contextPath + Constants.MAIN_PAGE_URL);   
				return;  
			}
			/*BmtUser user = (BmtUser)httpRequest.getSession().getAttribute("user");
			if(user == null) {// Not login
				//Redirect to login page
				httpResponse.sendRedirect(contextPath + Constants.MAIN_PAGE_URL);   
				return;   
			} else {// Already login
				//Check permission
				//List<BmtPermission> permissionList = bmtUserService.getAllPermissionsByUserId(user.getUserId());
				List<BmtPermission> permissionList = (List<BmtPermission>)httpRequest.getSession().getAttribute("permissionList");
				if(!AppUtil.hasPermission(permissionList, permissionURL)) {//No permission
					//Redirect to no permission page
					httpResponse.sendRedirect(contextPath + Constants.NO_PERMISSION_PAGE_URL);
					return;
				}
			}*/
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
