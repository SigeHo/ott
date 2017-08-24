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
		
		function doClose() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#role_add_update_div').css("display", "none");// hide input form
		}
		
		<c:if test="${canUpdateRole eq 'Y'}">
		$(document).ready(function(){
	    	$('#dg').datagrid({onDblClickRow:function(rowIndex, rowData){
	    										if(isFirstTime) {
	    											isFirstTime = false;
	    											isUpdateOrAdd = false;
	    											showUpdateRole(rowIndex, rowData);
	    											return;
	    										}
	    										if(!isUpdateOrAdd && (rowIndexTag != rowIndex)) {// haven't saved data and not the same record
	    											$.messager.confirm('Warning!','You are editing a record, continue without saving?'
						                                                ,function(button){
																			if (button == false ){
																				return ;
																			} else {
																			    isUpdateOrAdd = false;
																				showUpdateRole(rowIndex, rowData);
																			}
																		 }
																      );
	    											
	    										} else {
	    										    isUpdateOrAdd = false;
	    											showUpdateRole(rowIndex, rowData);
	    										}
	    									 }
	    					 });
	    });
	    
	    function showUpdateRole2() {
	    	var rowData = $('#dg').datagrid('getSelected');
			if (rowData){
				var rowIndex = $('#dg').datagrid('getRowIndex', rowData);
		  		if(isFirstTime) {
					isFirstTime = false;
					isUpdateOrAdd = false;
					showUpdateRole(rowIndex, rowData);
					return;
				}
				if(!isUpdateOrAdd && (rowIndexTag != rowIndex)) {// haven't saved data and not the same record
					$.messager.confirm('Warning!','You are editing a record, continue without saving?',
						function(button){
							if (button == false ){
								return ;
							} else {
							    isUpdateOrAdd = false;
								showUpdateRole(rowIndex, rowData);
							}
						 }
					 );
					
				} else {
				    isUpdateOrAdd = false;
					showUpdateRole(rowIndex, rowData);
				}
			} else {
				$.messager.alert('',"Please select one role to edit.");
			}
		}
		</c:if>
		
		<c:if test="${canAddRole eq 'Y'}">
		function showAddRole() {
			$('#addBtn').linkbutton('enable');
			if(isFirstTime) {
				isFirstTime = false;
				isUpdateOrAdd = false;
				rowIndexTag = -1;
				$('#role_add_update_div').css("display", "block");
				clearForm();
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
				url = '${ctx}/accountmanagement/role/addRole.html';
				return;
			}
			if(!isUpdateOrAdd) {// haven't saved data
				$.messager.confirm('Warning!','You are editing a record, continue without saving?!'
                                          ,function(button){
												if (button == false ){
													return ;
												} else {
												    isUpdateOrAdd = false;
												    rowIndexTag = -1;
													$('#role_add_update_div').css("display", "block");
													$('#fm').form('clear');
													url = '${ctx}/accountmanagement/role/addRole.html';
													$('#addSpan').css("display", "inline");
													$('#updateSpan').css("display", "none");
												}
										  }
				);
				
			} else {
				isUpdateOrAdd = false;
				rowIndexTag = -1;
				$('#role_add_update_div').css("display", "block");
				clearForm();
				url = '${ctx}/accountmanagement/role/addRole.html';
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
			}
		}
		
		function doAddRole() {
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					var flag = (  checkLength($('#roleName').val(), ROLE_NAME_MAX, "Role name") 
					           && checkLength($('#roleDesc').val(), ROLE_DESC_MAX, "Description") 
					           && $(this).form('validate')
							   );
					if(flag) {
						$('#addBtn').linkbutton('disable');    // disable the button  
						showLoadingMsg();
					}
					return flag;
				},
				success: function(result){
					$('#addBtn').linkbutton('enable'); 
					hideLoadingMsg();
					var result = eval('('+result+')');
					if (result.success){
						$.messager.alert('',result.msg);  
						$('#dg').datagrid('reload');	// reload the role data
						isUpdateOrAdd = true;
						$('#role_add_update_div').css("display", "none");// hide input form
					} else {
						$.messager.alert('',result.msg); 
					}
				}
			});
		}
		</c:if>
		
		<c:if test="${canUpdateRole eq 'Y'}">
		function showUpdateRole(rowIndex, rowData) {
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$('#updateBtn').linkbutton('enable');
			    $('#role_add_update_div').css("display", "block");
			    if(rowIndexTag != rowIndex) {
					$('#fm').form('load',row);
				}
				rowIndexTag = rowIndex;
				$('#addSpan').css("display", "none");
				$('#updateSpan').css("display", "inline");
				url = '${ctx}/accountmanagement/role/updateRole.html';
			}
		}
		function doUpdateRole(){
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					var flag = (  checkLength($('#roleName').val(), ROLE_NAME_MAX, "Role name") 
					           && checkLength($('#roleDesc').val(), ROLE_DESC_MAX, "Description") 
					           && $(this).form('validate')
							   );
					if(flag) {
						$('#updateBtn').linkbutton('disable');    // disable the button  
						showLoadingMsg();
					}
					return flag;
				},
				success: function(result){
					$('#updateBtn').linkbutton('enable'); 
					hideLoadingMsg();
					var result = eval('('+result+')');
					if (result.success){
						isUpdateOrAdd = true;
						$.messager.alert('',result.msg);						
						//$('#dg').datagrid('reload'); // reload the role data
						$('#dg').datagrid('getRows')[rowIndexTag].roleName = $('#roleName').val();
						$('#dg').datagrid('getRows')[rowIndexTag].roleDesc = $('#roleDesc').val();
						$('#dg').datagrid('updateRow',{
							index:rowIndexTag,
							row:$('#dg').datagrid('getRows')[rowIndexTag]
						});
						rowIndexTag = -1;
					} else {
						$.messager.alert('',result.msg);
					}
				}
			});
		}
		</c:if>

		<c:if test="${canDeleteRole eq 'Y'}">
		function deleteRole(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure to delete this role?',function(r){
					if (r){
						$.post('${ctx}/accountmanagement/role/deleteRole.html',{roleId:row.roleId},function(result){
							if (result.success){
								$.messager.alert('',result.msg);
								$('#dg').datagrid('reload');	// reload the role data
								isUpdateOrAdd = true;
								rowIndexTag = -1;
								$('#role_add_update_div').css("display", "none");// hide input form
							} else {
								$.messager.alert('',result.msg);
							}
						},'json');
					}
				});
			} else {
				$.messager.alert('',"Please select one role to delete.");
			}
		}
		</c:if>
		
		function doSearch() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#role_add_update_div').css("display", "none");// hide input form
			
			var roleNameForSearch = $('#roleNameForSearch').val();
			roleNameForSearch = roleNameForSearch.Trim();
			$('#roleNameForSearch').val(roleNameForSearch);
			url = '${ctx}/accountmanagement/role/listRole.html?roleNameForSearch='+roleNameForSearch;
			$('#dg').datagrid('options').url = url;
			$('#dg').datagrid('reload');
		}
		
		<c:if test="${canEditRolePermissions eq 'Y'}">
		var is_dg_role_permissions_init = false;
	    function editPermissions() {
	    	var row = $('#dg').datagrid('getSelected');
			if (row){
				document.getElementById("currentSelectRoleId").value= row.roleId;
				//alert(document.getElementById("currentSelectRoleId").value);
				$('#dlg-permission-edit').dialog('open')
				$('#dlg-permission-edit').dialog('setTitle','Edit Role\'s Permissions');
				
				if(!is_dg_role_permissions_init) {
					$('#dg-role-permissions').datagrid({
			    		title:'Current role:' +row.roleName,
					    iconCls:'',
					    url:'${ctx}/accountmanagement/role/listRolePermissions.html?roleId='+row.roleId,
					    idField:'permissionId',
					    height:200,
					    pagination:false,
					    fitColumns:true,
					    columns:[[
					        {field:'checked',checkbox:true},
					        {field:'permissionName',title:'Permission Name',width:200},
					        {field:'permissionDesc',title:'Description',width:200},
					        {field:'permissionUrl',title:'URL',width:200}
					    ]], 
					    onLoadSuccess:function() {
					    	var rows = $('#dg-role-permissions').datagrid('getRows')
							var rowcount = rows.length;
							for(var i=0; i<rowcount; i++){
								if(rows[i].checked) {
									$('#dg-role-permissions').datagrid('selectRow',i);	
								} else {
									$('#dg-role-permissions').datagrid('unselectRow',i);	
								}
							}
					    }
			    	});
			    	is_dg_role_permissions_init = true;
		    	} else {
		    		$('#dg-role-permissions').datagrid({title:'Current role:' +row.roleName});
		    		$('#dg-role-permissions').datagrid('clearSelections');					
					$('#dg-role-permissions').datagrid('options').url = '${ctx}/accountmanagement/role/listRolePermissions.html?roleId='+row.roleId;
					$('#dg-role-permissions').datagrid('reload');
		    	}
			} else {
				$.messager.alert('',"Please select one role to edit permissions.");
			}
	    }
	    
	    function saveRolePermissions() {
	    	var currentSelectRoleId = document.getElementById("currentSelectRoleId").value;
	    	var selectedRows = $('#dg-role-permissions').datagrid('getSelections');
	    	var rowcount = selectedRows.length;
	    	var var_permissionIds = "";
	    	var var_permissionNames = "";
	    	if(rowcount == 0) {
	    		$.messager.alert('',"Please at least select a permission for role.");
	    	} else {
	    		for(var i=0; i<rowcount; i++){
	    			var_permissionIds +=","+selectedRows[i].permissionId;
	    			var_permissionNames +=","+selectedRows[i].permissionName;
	    		}
	    		if(var_permissionIds != "" && var_permissionIds.length != 0) {
	    			var_permissionIds = var_permissionIds.substr(1);
	    			var_permissionNames = var_permissionNames.substr(1);
	    		}
	    		
	    		$.post('${ctx}/accountmanagement/role/roleEditPermissions.html',{roleId:currentSelectRoleId,permissionIds:var_permissionIds,permissionNames:var_permissionNames},function(result){
					if (result.success){
						$.messager.alert('',result.msg);
						$('#dlg-permission-edit').dialog('close', 'forceClose');
					} else {
						$.messager.alert('',result.msg);
					}
				},'json');
	    	}
	    }
	    </c:if>
	    
	    function clearForm() {
	    	$('#fm')[0].reset();
			$('#roleName').validatebox('resetValidation');
	    }
	    
		function clearSearch() {
			$("#roleNameForSearch").val("");
		}
	</script>
</head>
		
<body>
	<table id="dg" data-options="
				url:'${ctx}/accountmanagement/role/listRole.html',
				toolbar:'#toolbar',
				fitColumns:true,
				rownumbers:false,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th field="roleName" width="150">Role Name</th>
				<th field="roleDesc" width="150">Role Description</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
					<c:if test="${canAddRole eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAddRole()">Add Role</a>
						</c:if>
						<c:if test="${canUpdateRole eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdateRole2()">Edit Role</a>
						</c:if>
						
						<c:if test="${canDeleteRole eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRole()">Delete Role</a>
						</c:if>
						<c:if test="${canEditRolePermissions eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPermissions()">Edit Role's Permissions</a>
						</c:if>
				</td>
				<td align="right">
					<span style="padding-left:20px">Role name: <input type="text" id="roleNameForSearch" name="roleNameForSearch" /></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="role_add_update_div" style="padding:15px 10px; display:none" >
		<form id="fm" method="post">
			<input name="roleId" type="hidden" />
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<td>Role Name:</td>
					<td><input id="roleName" name="roleName" maxlength="50" class="easyui-validatebox"  data-options="required:true, validateOnCreate:false" style="width:200px"></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description:</td>
					<td><textarea id="roleDesc" name="roleDesc" maxlength="100" class="easyui-validatebox" rows="3" data-options="validateOnCreate:false" style="width:200px"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:right">
						<span id="updateSpan" ><a id="updateBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doUpdateRole()">Update</a></span>
						<span id="addSpan"><a id="addBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddRole()">Add</a></span>
						<a href="#" class="easyui-linkbutton" plain="false" id="btnCancel" name="btnCancel" iconCls="icon-cancel" onclick="doClose()">Close</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<c:if test="${canEditRolePermissions eq 'Y'}">
	<div id="dlg-permission-edit" href="" class="easyui-dialog" modal="true" style="width:650px;height:300px;padding:15px 10px" closed="true" buttons="#dlg-buttons-role-permissions">
		<input type="hidden" id="currentSelectRoleId" name="currentSelectRoleId" />
		<table id="dg-role-permissions"></table>
	</div>
	<div id="dlg-buttons-role-permissions">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRolePermissions()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg-permission-edit').dialog('close')">Cancel</a>
	</div>
	</c:if>
</body>
</html>