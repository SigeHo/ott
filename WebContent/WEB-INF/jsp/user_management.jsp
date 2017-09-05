<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<html>
<head>
	<style type="text/css">
		div.centent {
		   float:left;
		   text-align: center;
		   margin: 10px;
		}
		div.centent span { 
		 display:block; 
		 margin:2px 2px;
		 padding:4px 10px; 
		 background:#898989;
		 cursor:pointer;
		 font-size:12px;
		 color:white;
		}
	</style>
	
	<script type="text/javascript">
		var url;
		var isUpdateOrAdd = true;
		var isFirstTime = true;
		var rowIndexTag = -1;
		var isInitRoleList = false;
		    
		<c:if test="${canUpdateUser eq 'Y'}">    
		$(document).ready(function(){
			
	    	$('#dg').datagrid({onDblClickRow:function(rowIndex, rowData){
	    										if(isFirstTime) {
	    											isFirstTime = false;
	    											isUpdateOrAdd = false;
	    											initUpdateDiv(rowIndex, rowData);
	    											return;
	    										}
	    										if(!isUpdateOrAdd && (rowIndexTag != rowIndex) ) {// haven't saved data and not the same record
	    											$.messager.confirm('Warning!','You are editing a record, continue without saving?'
						                                                ,function(button){
																			if (button == false ){
																				return ;
																			} else {
																			    isUpdateOrAdd = false;
																				initUpdateDiv(rowIndex, rowData);
																			}
																		 }
																      );
	    											
	    										} else {
	    										    isUpdateOrAdd = false;
	    											initUpdateDiv(rowIndex, rowData);
	    										}
	    									 }
	    					 });
	    	   	
	    });
	    
	    function showUpdateUser() {
	    	var rowData = $('#dg').datagrid('getSelected');
			if (rowData){
				var rowIndex = $('#dg').datagrid('getRowIndex', rowData);
				//alert(rowIndex);
				if(isFirstTime) {
					isFirstTime = false;
					isUpdateOrAdd = false;
					initUpdateDiv(rowIndex, rowData);
					return;
				}
				if(!isUpdateOrAdd && (rowIndexTag != rowIndex)) {// haven't saved data and not the same record
					$.messager.confirm('Warning!','You are editing a record, continue without saving?'
                                           ,function(button){
								if (button == false ){
									return ;
								} else {
								    isUpdateOrAdd = false;
									initUpdateDiv(rowIndex, rowData);
								}
							 }
					      );
					
				} else {
				    isUpdateOrAdd = false;
					initUpdateDiv(rowIndex, rowData);
				}
			} else {
				$.messager.alert('',"Please select one user to edit.");
			}
	    }	    
	    </c:if>	 
		
		function doClose() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#user_add_update_div').css("display", "none");// hide input form
		}
		
		<c:if test="${canAddUser eq 'Y'}">
		function showAddUser() {
			$('#addBtn').linkbutton('enable');
			if(isFirstTime) {
				isFirstTime = false;
				rowIndexTag = -1;
				isUpdateOrAdd = false;
				$('#user_add_update_div').css("display", "block");
				$('#fm').form('clear');
				$("#fm").form('resetValidation');
				url = '${ctx}/accountmanagement/user/addUser.html';
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
				initRoleList();
				return;
			}
			if(!isUpdateOrAdd) {// haven't saved data
				$.messager.confirm('Warning!','You are editing a record, continue without saving?!'
                                          ,function(button){
												if (button == false ){
													return ;
												} else {
													rowIndexTag = -1;
												    isUpdateOrAdd = false;
													$('#user_add_update_div').css("display", "block");
													$('#fm').form('clear');
													$("#fm").form('resetValidation');
													url = '${ctx}/accountmanagement/user/addUser.html';
													$('#addSpan').css("display", "inline");
													$('#updateSpan').css("display", "none");
												}
										  }
				);
				
			} else {
				rowIndexTag = -1;
				isUpdateOrAdd = false;
				$('#user_add_update_div').css("display", "block");
				$('#fm').form('clear');
				$("#fm").form('resetValidation');
				url = '${ctx}/accountmanagement/user/addUser.html';
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
			}
			initRoleList();
		}
		
		function doAddUser() {
			var role = $("#roleDl").datalist("getSelected");
			if (role) {
				$("#userRole").val(role.roleId);
			}
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					var flag = (   checkLength($('#username').val(), USER_NAME_MAX, "User name") 
					         	&& checkLength($('#userDesc').val(), USER_DESC_MAX, "Description") 
					         	&& $(this).form('validate')
							   );
					if(flag && role == null){
						$.messager.alert("","Please choose one role.", "warning"); 
						return false;
					}
					if(flag) {
						$('#addBtn').linkbutton('disable');    // disable the button  
						showLoadingMsg();
					}
					return flag;
				},
				success: function(result){
					hideLoadingMsg();
					$('#addBtn').linkbutton('enable');
					var result = eval('('+result+')');
					if (result.success){
						$.messager.alert('',result.msg);  
						$('#dg').datagrid('reload');	// reload the user data
						isUpdateOrAdd = true;
						$('#user_add_update_div').css("display", "none");// hide input form
					} else {
						$.messager.alert('',result.msg); 
					}
				}
			});
		}
		</c:if>
		
		<c:if test="${canUpdateUser eq 'Y'}">
		function initUpdateDiv(rowIndex, rowData) {
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$('#updateBtn').linkbutton('enable'); 
			    $('#user_add_update_div').css("display", "block");
			    if(rowIndexTag != rowIndex) {
					$('#fm').form('load',row);
			    }
				rowIndexTag = rowIndex;
				$('#addSpan').css("display", "none");
				$('#updateSpan').css("display", "inline");
				$('#updateBtn').linkbutton('enable'); 
				url = '${ctx}/accountmanagement/user/updateUser.html';
			}
			initRoleList();
		}
		function doUpdateUser() {
			var role = $("#roleDl").datalist("getSelected");
			if (role) {
				$("#userRole").val(role.roleId);
			}
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					
					var flag = (   checkLength($('#username').val(), USER_NAME_MAX, "User name") 
					         	&& checkLength($('#userDesc').val(), USER_DESC_MAX, "Description") 
					         	&& $(this).form('validate')
							   );
					if(flag && role == null){
						$.messager.alert("","Please choose one role.", "warning"); 
						return false;
					}
					if(flag) {
						$('#updateBtn').linkbutton('disable');    // disable the button  
						showLoadingMsg();
					}
					return flag;
				},
				success: function(result){
					hideLoadingMsg();
					$('#updateBtn').linkbutton('enable'); 
					var result = eval('('+result+')');
					if (result.success){
						isUpdateOrAdd = true;
						$.messager.alert('',result.msg);						
						$('#dg').datagrid('reload'); // reload the user data
						//$('#dg').datagrid('getRows')[rowIndexTag].username = $('#username').val();
						//$('#dg').datagrid('getRows')[rowIndexTag].domainName = $('#domainName').combobox('getValue');
						//$('#dg').datagrid('getRows')[rowIndexTag].userEmail = $('#userEmail').val();
						//$('#dg').datagrid('getRows')[rowIndexTag].userDesc = $('#userDesc').val();
						//$('#dg').datagrid('updateRow',{
						//	index:rowIndexTag,
						//	row:$('#dg').datagrid('getRows')[rowIndexTag]
						//});
						rowIndexTag = -1;
						$('#user_add_update_div').css("display", "none");// hide input form
					} else {
						$.messager.alert('',result.msg);
					}
				}
			});
		}
		</c:if>
		
		<c:if test="${canDeleteUser eq 'Y'}">
		function deleteUser(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure to delete this user?',function(r){
					if (r){
						$.post('${ctx}/accountmanagement/user/deleteUser.html',{userId:row.userId},function(result){
							if (result.success){
								$.messager.alert('',result.msg);
								$('#dg').datagrid('reload');	// reload the user data
								isUpdateOrAdd = true;
								rowIndexTag = -1;
								$('#user_add_update_div').css("display", "none");// hide input form
							} else {
								$.messager.alert('',result.msg);
							}
						},'json');
					}
				});
				
			} else {
				$.messager.alert('',"Please select one user to delete.");
			}
		}
		</c:if>
		
		
		function doSearch() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#user_add_update_div').css("display", "none");// hide input form
			
			var usernameForSearch = $('#usernameForSearch').val();
			usernameForSearch = usernameForSearch.Trim();
			$('#usernameForSearch').val(usernameForSearch);
			$('#dg').datagrid('load', {
				usernameForSearch : usernameForSearch
			});
		}
		
		<c:if test="${canEditUserRole eq 'Y'}">
		var is_dg_user_roles_init = false;
	    function editRole() {
	    	var row = $('#dg').datagrid('getSelected');
			if (row){
				document.getElementById("currentSelectUserId").value= row.userId;
				
				$('#dlg-role-edit').dialog('open')
				$('#dlg-role-edit').dialog('setTitle','Edit user\'s roles');
				
				if(!is_dg_user_roles_init) {					
					$('#dg-user-roles').datagrid({
			    		title:'Current user:' +row.username,
					    iconCls:'',
					    url:'${ctx}/accountmanagement/user/listUserRoles.html?userId='+row.userId,
					    idField:'roleId',
					    height:200,
					    pagination:false,
					    fitColumns:true,
					    columns:[[
					        {field:'checked',checkbox:true},
					        {field:'roleName',title:'Role Name',width:200},
					        {field:'roleDesc',title:'Role Description',width:200}
					    ]], 
					    onLoadSuccess:function() {
					    	var rows = $('#dg-user-roles').datagrid('getRows')
							var rowcount = rows.length;
							for(var i=0; i<rowcount; i++){								
								if(rows[i].checked) {
									$('#dg-user-roles').datagrid('selectRow',i);	
								} else {
									$('#dg-user-roles').datagrid('unselectRow',i);	
								}
							}
					    }
			    	});
			    	is_dg_user_roles_init = true;
				} else {	
					$('#dg-user-roles').datagrid({title:'Current user:' +row.username});
					$('#dg-user-roles').datagrid('clearSelections');					
					$('#dg-user-roles').datagrid('options').url = '${ctx}/accountmanagement/user/listUserRoles.html?userId='+row.userId;
					$('#dg-user-roles').datagrid('reload');
				}
				
			} else {
				$.messager.alert('',"Please select one user to edit roles.");
			}
	    }
	    
	    function saveUserRoles() {
	    	var currentSelectUserId = document.getElementById("currentSelectUserId").value;
	    	var selectedRows = $('#dg-user-roles').datagrid('getSelections');
	    	
	    	var rowcount = selectedRows.length;
	    	var var_roleIds = "";
	    	var var_roleNames = "";
//	    	if(rowcount == 0) {
//	    		$.messager.alert('',"Please at least select a role for user.");
//	    	} else {
	    		for(var i=0; i<rowcount; i++){
	    			var_roleIds +=","+selectedRows[i].roleId;
	    			var_roleNames +=","+selectedRows[i].roleName;
	    		}
	    		if(var_roleIds != "" && var_roleIds.length != 0) {
	    			var_roleIds = var_roleIds.substr(1);
	    			var_roleNames = var_roleNames.substr(1);
	    		}
	    		
	    		$.post('${ctx}/accountmanagement/user/userEditRoles.html',{userId:currentSelectUserId,roleIds:var_roleIds,roleNames:var_roleNames},function(result){
					if (result.success){
						$.messager.alert('',result.msg);
						$('#dlg-role-edit').dialog('close', 'forceClose');
						$('#dg').datagrid('reload');	// reload the user data
					} else {
						$.messager.alert('',result.msg);
					}
				},'json');
//	    	}
	    }
	    </c:if>
	    
	    function initRoleList() {
			var row = $("#dg").datagrid("getSelected");
			if (!isInitRoleList) {
				$("#roleDl").datalist({
					url : '${ctx}/accountmanagement/user/listAllRole.html',
					lines : true,
					idField : 'roleId',
					valueField : 'roleId',
					singleSelect : true,
					textField : 'roleName',
					onLoadSuccess : function(data) {
						if ( row && row.role) {
							$("#roleDl").datalist("selectRecord", row.role.roleId);
						} 
					}
				});
				isInitRoleList = true;
			} else {
				$("#roleDl").datalist("clearSelections");
				if ( row && row.role) {
					$("#roleDl").datalist("selectRecord", row.role.roleId);
				} 
			}
	    }
	    
	    function clearSearch(){
			$("#usernameForSearch").val("");
		}
	    
	    
	</script>
</head>

<body>
	<table id="dg" class="easyui-datagrid" 
			url="${ctx}/accountmanagement/user/listUser.html"
			toolbar="#toolbar" pagination="true"
			pageSize="10" pageList="[10,20,30,40]"
			rownumbers="false" fitColumns="true" 
			singleSelect="true">
		<thead>
			<tr>
				<th field="username" width="150">User Name</th>
				<th field="userEmail" width="150">User Email</th>
				<th field="userDesc" width="150">User Description</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar" style="padding:5px;height:auto;margin-bottom:5px">
	<table width="100%">
		<tr>
			<td>
				<c:if test="${canAddUser eq 'Y'}">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAddUser()">Add User</a>
				</c:if>
				<c:if test="${canUpdateUser eq 'Y'}">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdateUser()">Edit User</a>
				</c:if>
				<c:if test="${canDeleteUser eq 'Y'}">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">Delete User</a>
				</c:if>
			</td>
			<td align="right">
				<span style="padding-left:10px;">User name: <input type="text" style="" id="usernameForSearch" name="usernameForSearch" /></span>
				<a href="#" class="easyui-linkbutton"  iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
			</td>
		</tr>
	</table>
		
		
	</div>
	
	<div id="user_add_update_div" style="padding:15px 10px; display:none" >
		<form id="fm" method="post">
			<input name="userId" type="hidden" />
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<td  style="vertical-align: top">User Name:</td>
					<td><input id="username" name="username" maxlength="50" class="easyui-validatebox" style="width:200px" data-options="required:true, validateOnCreate:false"></td>
				</tr>
				<tr>
					<td  style="vertical-align: top">User Email:</td>
					<td><input id="userEmail" name="userEmail" maxlength="50" class="easyui-validatebox" style="width:200px" data-options="required:true, validType:'email', validateOnCreate:false"></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description:</td>
					<td><textarea id="userDesc" name="userDesc" maxlength="100" class="easyui-validatebox" rows="3" style="width:200px" data-options="required:false, validateOnCreate:false"></textarea></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Role:</td>
					<td>
						<ul id="roleDl"></ul>
						<input id="userRole" name="userRole" hidden>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:right">
						<span id="updateSpan" ><a id="updateBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doUpdateUser()">Update</a></span>
						<span id="addSpan"><a id="addBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddUser()">Add</a></span>
						<a href="#" class="easyui-linkbutton" plain="false" id="btnCancel" name="btnCancel" iconCls="icon-cancel" onclick="doClose()">Close</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<div id="dlg-role-edit" href="" class="easyui-dialog" modal="true" style="width:500px;height:300px;padding:15px 10px" closed="true" buttons="#dlg-buttons-user_roles">
		<input type="hidden" id="currentSelectUserId" name="currentSelectUserId" />
		<table id="dg-user-roles"></table>
	</div>
	<div id="dlg-buttons-user_roles">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUserRoles()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg-role-edit').dialog('close')">Cancel</a>
	</div>
</body>
</html>