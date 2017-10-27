<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OTT Sports CMS</title>

<style type="text/css">
.comboboxClass {
	display: inline-block;
	vertical-align: middle;
}

ul {
	margin: 5px 0 0 25px;
	padding: 0
}

ul li {
	list-style: disc;
	padding: 5px 0 5px 0
}
</style>

<script type="text/javascript">
	
	var flag = "${empty sessionScope.loginUser}";
	var isLoginSuccessful = (flag != 'true');

	$(document).keydown(function(event) {
		if (event.keyCode == 13 && isLoginSuccessful == false) {
			doLogin();
		}
	});
	
	$(document).ready(function() {
		loginInit();
		logoutInit();
		<c:if test="${empty sessionScope.loginUser}">
		loginOpen();
		</c:if>
		$('#username').focus(function() {
			$('#login_error').text('');
		});
		$('#password').focus(function() {
			$('#login_error').text('');
		});
	});

	function clearErrorText() {
		$('#login_error').text("");
	}

	function addTab(title, url, id) {
		var icon = "";
		url = url;
		if ($('#main').tabs('exists', title)) {
			$('#main').tabs('select', title);
		} else {
			var content = '<iframe src="'
					+ url
					+ '" name="mainIframe" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>';
			$('#main').tabs(
					'add',
					{
						title : title,
						content : content,
						cache : false,
						iconCls : icon == undefined || icon.length == 0
								|| icon == 'null' ? 'icon-file' : icon,
						closable : true
					});
		}
	}

	function doLogin() {
		var lognUrl = $('#loginForm').attr("action");

		$('#loginForm').form('submit', {
			url : lognUrl,
			onSubmit : function() {
				var formCheck = $(this).form('validate');
				if (!formCheck) {
					return formCheck;
				}
				return formCheck;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					loginClose();
					isLoginSuccessful = true;
					$('#username').validatebox({
						required : false
					});
					$('#password').validatebox({
						required : false
					});
					window.location.href = '${ctx}/main.html';
				} else {
					$('#login_error').text(result.msg);
				}
			}
		});
	}

	function loginInit() {
		$('#login').dialog({
			title : 'Login',
			modal : true,
			buttons : [ {
				text : 'Login',
				handler : doLogin
			}, {
				text : 'Reset',
				handler : function() {
					$("form:first")[0].reset();
					$('#login_error').text("");
				}
			} ],
			closed : true,
			closable : false
		});
	}
	function loginOpen() {
		$('#login').dialog('open');
	}
	function loginClose() {
		$('#login').dialog('close');
	}

	function logoutInit() {
		$('#logout').dialog({
			title : 'Logout',
			modal : true,
			closed : true,
			closable : false
		});
	}
	function logoutOpen() {
		$('#logout').dialog('open');
		$('#logoutIframe').attr('src',
				'${ctx}/accountmanagement/user/doLogout.html');

		var timer = 3;
		setInterval(function() {
			if (timer == 0) {
				location.href = '${ctx}/main.html';
				return;
			}
			$('#logoutTimer').text(timer);
			timer--;
		}, 1000);
	}
</script>
</head>

<body class="easyui-layout">
        <div region="west" split="true" title="Boot Server Management System" style="width:240px;padding:10px;overflow:hidden;">
        	<table border="0" cellpadding="5" cellspacing="0">
			<tr>
				<td class="base_menu">
					<c:if test="${hasUserPermission eq 'Y'|| hasRolePermission eq 'Y'|| hasPermission eq 'Y'}">
			       	<span style="font-weight:bold;color:#06c">Account Management</span>
					<ul>
						<li><a href="javascript:void(0)" onclick="addTab('Pwd', '${ctx}/accountmanagement/user/goToChangePwdPage.html', ' ');return false;">Change Password</a><br/></li>
<%-- 						<c:if test="${hasUserPermission eq 'Y'}"> --%>
<%-- 			   			<li><a href="javascript:void(0)" onclick="addTab('User', '${ctx}/accountmanagement/user/goToListUserPage.html', ' ');return false;">User</a><br/></li> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${hasRolePermission eq 'Y'}"> --%>
<%-- 			   			<li><a href="javascript:void(0)" onclick="addTab('Role', '${ctx}/accountmanagement/role/goToListRolePage.html', ' ');return false;">Role</a><br/></li> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${hasPermission eq 'Y'}"> --%>
<%-- 			   			<li><a href="javascript:void(0)" onclick="addTab('Permission', '${ctx}/accountmanagement/permission/goToListPermissionPage.html', ' ');return false;">Permission</a><br/></li> --%>
<%-- 						</c:if> --%>
				    </ul>
				    </c:if>				    
				</td>
			</tr>
			
			<tr>
				<td class="base_menu">
					<span style="font-weight:bold;color:#06c">Snooker Management</span>
					<ul>
						<li><a href="javascript:void(0)" onclick="addTab('Snooker Live', '${ctx}/snooker/fixture/goToListLivePage.html', ' ');return false;">Snooker Live</a><br/></li>
						<li><a href="javascript:void(0)" onclick="addTab('Snooker Fixture', '${ctx}/snooker/fixture/goToListFixturePage.html', ' ');return false;">Snooker Fixture</a><br/></li>
			   			<li><a href="javascript:void(0)" onclick="addTab('Snooker Rank', '${ctx}/snooker/rank/goToListRankPage.html', ' ');return false;">Snooker Rank</a><br/></li>
			   			<li><a href="javascript:void(0)" onclick="addTab('Snooker League', '${ctx}/snooker/league/goToListLeaguePage.html', ' ');return false;">Snooker League</a><br/></li>
			   			<li><a href="javascript:void(0)" onclick="addTab('Snooker Player', '${ctx}/snooker/player/goToListPlayerPage.html', ' ');return false;">Snooker Player</a><br/></li>
				    </ul>
			</tr>
			
			<tr>
				<td class="base_menu">
					<span style="font-weight:bold;color:#06c">NPVR</span>
					<ul>
						<li><a href="javascript:void(0)" onclick="addTab('NPVR Mapping', '${ctx}/npvr/goToNpvrMappingPage.html', ' ');return false;">NPVR Mapping</a><br/></li>
				    </ul>
			</tr>
<!--            	<tr> -->
<!-- 				<td class="base_menu"> -->
<%-- 					<c:if test="${hasAuditTrailPermission eq 'Y'}"> --%>
<!-- 			       	<span style="font-weight:bold;color:#06c">Audit History Management</span> -->
<!-- 					<ul> -->
<%-- 			   			<li><a href="javascript:void(0)" onclick="addTab('Audit trail', '${ctx}/audit_trail/goToListAuditTrailPage.html', ' ');return false;">Audit trail</a><br/></li> --%>
<!-- 				    </ul> -->
<%-- 				    </c:if>				     --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			
			<tr>
            <c:if test="${!empty sessionScope.loginUser}">
            <tr>
            	<td>Current user: <span style="font-weight:bold;">${sessionScope.loginUser.username}</span></td>
            </tr>
            <tr>
            	<td>Platform: <span style="font-weight:bold;">${sessionScope.platform}</span></td>
            </tr>
            <tr>
	            <td><a id="logout_link" href="javascript:void(0)" onClick="logoutOpen()" class="easyui-linkbutton">Logout</a><br/><br/></td>
            </tr>
            </c:if>
            </table>
        </div>
        
        <div region="south" border="false" style="height:30px;padding:5px;text-align:center;border:0" class="tabs-header">Copyright &copy; PCCW Media Limited / All rights reserved. Privacy Statement, Disclaimer & Terms of Use.</div>
        
        <div region="center" style="overflow:hidden;">
        	<div class="easyui-tabs" id="main" fit="true" border="false">
				<div title="Welcome" iconCls="icon-page" style="padding:20px;overflow:hidden;"> 
					<div style="line-height:150%">
						<h3 style="font-size:14px">OTT Sports CMS</h3>
						<li>Version 2017-08-11</li>
					</div>
				</div>
			</div>
        </div>
        
     <div id="login" class="easyui-window" title="Login" style="width:320px;height:240px;overflow:hidden;">
	        <form id="loginForm" method="post" action="${ctx}/accountmanagement/user/doLogin.html" target="_parent">
	        	<table style="margin:25px 10px 0px 20px; border-collapse: separate; border-spacing: 0px 10px;">
	        		<tr>
	        			<td style="text-align:right; padding-right: 5px;">User Name: </td>
	        			<td>
	        				<input id="username" name="username" style="width:150px" class="easyui-validatebox" data-options="required:true, validateOnCreate:false, validateOnBlur:true" maxlength="50" onFocus="clearErrorText()">
	        			</td>
	        		</tr>
	        		<tr>
	        			<td style="text-align:right; padding-right: 5px;">Password: </td>
	        			<td>
	        				<input id="password" name="password" type="password" style="width:150px" class="easyui-validatebox" data-options="required:true, validateOnCreate:false, validateOnBlur:true" maxlength="50" onFocus="clearErrorText()">
	        			</td>
	        		</tr>
	        	</table>
			<div id="login_error" style="text-align:center; color:red;vertical-align:middle"></div>
	        </form>
     </div>   
    	        
        <div id="logout" style="width:300px;height:100px;padding:10px 0 0 10px;">
	        Successfully exit OTT Sports CMS.
	        <div style="font-size:10px;padding-top:10px;color:#999">Back to login screen after 3 seconds.(<span id="logoutTimer" style="color:#06c">3</span>s)</div>
	        <iframe id="logoutIframe" style="display:none">
	        </iframe>
        </div>
    </body>
</html>