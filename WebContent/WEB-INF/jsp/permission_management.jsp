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
		
		var isMultiple = true;
		
		var rowIndexTag = -1;
		
		function doClose() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#permission_add_update_div').css("display", "none");// hide input form
		}
		
		<c:if test="${canUpdatePermission eq 'Y'}">
		$(document).ready(function() {
			$('#dg').datagrid({onDblClickRow:function(rowIndex, rowData) {
				if(isFirstTime) {
					isFirstTime = false;
					isUpdateOrAdd = false;
					showUpdatePermission(rowIndex, rowData);
					return;
				}
				if(!isUpdateOrAdd && (rowIndexTag != rowIndex)) {// haven't saved data and not the same record
					$.messager.confirm('Warning!','You are editing a record, continue without saving?'
                                           ,function(button){
								if (button == false ){
									return ;
								} else {
								    isUpdateOrAdd = false;
									showUpdatePermission(rowIndex, rowData);
								}
							 }
					      );
				} else {
				    isUpdateOrAdd = false;
					showUpdatePermission(rowIndex, rowData);
				}
			}});
		});	 
	    
	    function showUpdatePermission2() {
	    	var rowData = $('#dg').datagrid('getSelected');
			if (rowData){
				var rowIndex = $('#dg').datagrid('getRowIndex', rowData);
				if(isFirstTime) {
					isFirstTime = false;
					isUpdateOrAdd = false;
					showUpdatePermission(rowIndex, rowData);
					return;
				}
				if(!isUpdateOrAdd && (rowIndexTag != rowIndex)) {// haven't saved data and not the same record
					$.messager.confirm('Warning!','You are editing a record, continue without saving?'
				          ,function(button){
								if (button == false ){
									return ;
								} else {
								    isUpdateOrAdd = false;
									showUpdatePermission(rowIndex, rowData);
								}
						  }
					 );
					
				} else {
				    isUpdateOrAdd = false;
					showUpdatePermission(rowIndex, rowData);
				}
			} else {
				$.messager.alert('',"Please select one user to edit.");
			}
	    }
		</c:if>
		
		<c:if test="${canAddPermission eq 'Y'}">
		function showAddPermission() {
			isMultiple = true;
			$('#addBtn').linkbutton('enable');
			if(isFirstTime) {
				isFirstTime = false;
				isUpdateOrAdd = false;
				rowIndexTag = -1;
				$('#permission_add_update_div').css("display", "block");
				clearForm();
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
				url = '${ctx}/accountmanagement/permission/addPermission.html';
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
													$('#permission_add_update_div').css("display", "block");
													clearForm();
													url = '${ctx}/accountmanagement/permission/addPermission.html';
													//$('#tableFields').combobox({multiple:true});
													$('#addSpan').css("display", "inline");
													$('#updateSpan').css("display", "none");
												}
										  }
				);
				
			} else {
				isUpdateOrAdd = false;
				rowIndexTag = -1;
				$('#permission_add_update_div').css("display", "block");
				clearForm();
				url = '${ctx}/accountmanagement/permission/addPermission.html';
				//$('#tableFields').combobox({multiple:true});
				$('#addSpan').css("display", "inline");
				$('#updateSpan').css("display", "none");
			}
			
		}
		
		function doAddPermission() {
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					var flag;
			    		flag =  ( checkLength($('#permissionName').val(), PERMISSION_NAME_MAX, "Permission name") 
					         && checkLength($('#permissionUrl').val(), PERMISSION_URL_MAX, "Permission URL") 
					         && checkLength($('#permissionDesc').val(), PERMISSION_DESC_MAX, "Description")
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
						isUpdateOrAdd = true;
						$.messager.alert('',result.msg);  
						$('#dg').datagrid('reload');	// reload the permission data
						$('#permission_add_update_div').css("display", "none");// hide input form
					} else {
						$.messager.alert('',result.msg); 
					}
				}
			});
		}
		</c:if>
		
		<c:if test="${canUpdatePermission eq 'Y'}">
		function showUpdatePermission(rowIndex, rowData) {
			var row = $('#dg').datagrid('getSelected');
			if (row){
			
			    isMultiple = false;
			    $('#updateBtn').linkbutton('enable'); 
			    $('#permission_add_update_div').css("display", "block");
			    
			    if(rowIndexTag != rowIndex) {//load data
					
					$('#permissionId').val(row.permissionId);
						$('#permissionName').val(row.permissionName);
						$('#permissionUrl').val(row.permissionUrl);
						$('#permissionDesc').val(row.permissionDesc);
				}
				rowIndexTag = rowIndex;
				$('#addSpan').css("display", "none");
				$('#updateSpan').css("display", "inline");
				url = '${ctx}/accountmanagement/permission/updatePermission.html';
			}
		}
		function doUpdatePermission() {
			$('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					var flag;
			    		flag = ( checkLength($('#permissionName').val(), PERMISSION_NAME_MAX, "Permission name") 
					         && checkLength($('#permissionUrl').val(), PERMISSION_URL_MAX, "Permission URL") 
					         && checkLength($('#permissionDesc').val(), PERMISSION_DESC_MAX, "Description")
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
						$('#dg').datagrid('updateRow',{
							index:rowIndexTag,
							row: {
								permissionName: $('#permissionName').val(),
								permissionUrl: $('#permissionUrl').val(),
								permissionDesc: $('#permissionDesc').val()
							}
						});
						rowIndexTag = -1;
					} else {
						$.messager.alert('',result.msg);
					}
				}
			});
		}
		</c:if>
		
		<c:if test="${canDeletePermission eq 'Y'}">
		function deletePermission(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure to delete this permission?',function(r){
					if (r){
						$.post('${ctx}/accountmanagement/permission/deletePermission.html',{permissionId:row.permissionId},function(result){
							if (result.success){
								$.messager.alert('',result.msg);
								$('#dg').datagrid('reload');	// reload the permission data
								isUpdateOrAdd = true;
								rowIndexTag = -1;
								$('#permission_add_update_div').css("display", "none");
							} else {
								$.messager.alert('',result.msg);
							}
						},'json');
					}
				});
			} else {
				$.messager.alert('',"Please select one permission to delete.");
			}
		}
		</c:if>
		
		function doSearch() {
			isUpdateOrAdd = true;
			rowIndexTag = -1;
			$('#permission_add_update_div').css("display", "none");
			
			var permissionNameForSearch = $('#permissionNameForSearch').val();
			permissionNameForSearch = permissionNameForSearch.Trim();
			$('#permissionNameForSearch').val(permissionNameForSearch);
			$('#dg').datagrid('load', {
				permissionNameForSearch : permissionNameForSearch
			});
		}
		
	    function clearForm() {
	    	$('#fm')[0].reset();
	    	$('#permissionName').validatebox('resetValidation');
	    	$('#permissionUrl').validatebox('resetValidation');
	    }

		function clearSearch() {
			$("#permissionNameForSearch").val("");
		}
	</script>
</head>

<body>
	<table id="dg" title="" class="easyui-datagrid"
			toolbar="#toolbar" pagination="true"
			pageSize="10" pageList="[10,20,30,40]"
			rownumbers="false" fitColumns="true" 
			url="${ctx}/accountmanagement/permission/listPermission.html"
			singleSelect="true">
		<thead>
			<tr>
				<th field="permissionName" width="150">Permission Name</th>
				<th field="permissionUrl" width="150">URL</th>
				<th field="permissionDesc" width="150">Description</th>
			</tr>
		</thead>
	</table>	

	<div id="toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
					<c:if test="${canAddPermission eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAddPermission()">Add Permission</a>
						</c:if>
						<c:if test="${canUpdatePermission eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdatePermission2()">Edit Permission</a>
						</c:if>
						<c:if test="${canDeletePermission eq 'Y'}">
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletePermission()">Delete Permission</a>
						</c:if>
				</td>
				<td align="right">
					<span style="padding-left:20px">Permission name: <input type="text" id="permissionNameForSearch" name="permissionNameForSearch" style="width:150px"/></span>
							<input type="hidden" id="permissionTypeForSearch" name="permissionTypeForSearch" value="URL">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="permission_add_update_div" style="padding:15px 10px; display:none" >
		<form id="fm" method="post">
			<input id="permissionId" name="permissionId" type="hidden" />

			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr id="permissionNameTR">
					<td>Permission Name:</td>
					<td><input id="permissionName" name="permissionName" maxlength="110" class="easyui-validatebox" data-options="required:true, validateOnCreate:false" style="width:200px"></td>
				</tr>
				<tr id="permissionUrlTR">
					<td>Permission URL:</td>
					<td><input id="permissionUrl" name="permissionUrl" maxlength="200" class="easyui-validatebox" data-options="required:true, validateOnCreate:false" style="width:200px"></td>
				</tr>
				<tr id="permissionDescTR">
					<td style="vertical-align: top">Description:</td>
					<td><textarea id="permissionDesc" name="permissionDesc" maxlength="200" class="easyui-validatebox" rows="3" data-options="required:false, validateOnCreate:false" style="width:200px"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:right">
						<span id="updateSpan" ><a id="updateBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doUpdatePermission()">Update</a></span>
						<span id="addSpan"><a id="addBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddPermission()">Add</a></span>
						<a href="#" class="easyui-linkbutton" plain="false" id="btnCancel" name="btnCancel" iconCls="icon-cancel" onclick="doClose()">Close</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>