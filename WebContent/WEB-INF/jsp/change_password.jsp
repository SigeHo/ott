<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function doReset() {
		$('#fm').form("reset");
		$('#fm').form("resetValidation");
	}
	
	function doSave() {
		var newPwd = $("#newPassword").val();
		var confirmPwd = $("#confirmPassword").val();
		$("#fm").form("submit", {
			url : '${ctx}/accountmanagement/user/changePassword.html',
			onSubmit : function(param) {
				var isValid = $(this).form('validate');
				if (isValid && newPwd != confirmPwd){
					$.messager.alert("", "Confirm password does not match the new password.", "warning");
					return false;
				}
				return isValid;
			},
			success : function(response) {
				var result = eval('('+response+')');
				if (result.success) {
					$.messager.alert("", "Change password successfully.", "info");
					doReset();
				} else if(result.msg){
					$.messager.alert("", result.msg, "error");
				} else {
					$.messager.alert("", "Failed to change password. Please try again.", "error", function() {
						$("#main").tabs("close", "Pwd")
					});
				}
			}
		});		
	}
</script>

</head>
<body>
	<div>
		<form id="fm" method="post">
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<td>Old Password: </td>
					<td>
						<input id="oldPassword" name="oldPassword" type="password" class="easyui-validatebox" data-options="required:true, validateOnCreate:false" style="width:200px;">
					</td>
				</tr>
				<tr>
					<td>New Password: </td>
					<td>
						<input id="newPassword" name="newPassword" type="password" class="easyui-validatebox" data-options="required:true, validateOnCreate:false" style="width:200px;">
					</td>
				</tr>
				<tr>
					<td>Confirm Password: </td>
					<td>
						<input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="required:true, validateOnCreate:false, validType:'equalTo[\'#newPassword\']', invalidMessage:'Doesn\'t match the new passworld.'" style="width:200px;">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:right">
						<a href="#" class="easyui-linkbutton" plain="false" iconCls="icon-ok" onclick="doSave()">Save</a>
						<a href="#" class="easyui-linkbutton" plain="false" iconCls="icon-reload" onclick="doReset()">Reset</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>