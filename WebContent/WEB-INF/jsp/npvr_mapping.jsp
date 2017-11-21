<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NPVR Mapping</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#sportType").combobox({
			onChange : function(newValue, oldValue) {
				if (newValue != oldValue) {
					$.ajax({
						url : "${ctx}/npvr/retrieveLeagueList.html",
						data : {
							sportType : newValue
						},
						type : "POST",
						success : function(response) {
							if (response.success) {
								$("#tournament").combobox({
									valueField : "id",
									textField : "leagueName",
									data : response.leagueList
								});
							} else {
								$.messager.alert("", "Failed to retrieve league list. Please try again.", "error");
							}
						},
						error : function() {
							$.messager.alert("", "Failed to retrieve league list. Please try again.", "error");
						}
					});
				}
			}
		});
		
		$("#fromTime").timespinner("setValue", "00:00");
		$("#toTime").timespinner("setValue", "00:00");
		
		$("#fixture_dg").datagrid({
			singleSelect : true,
		    columns : [[
		        {field : 'fixtureId', hidden : true},
		        {field : 'channelNos', hidden : true},
		        {field: 'sportType', hidden : true},
		        {field: 'tournament', hidden : true},
		        {field : 'tvCoverageCk', title : 'TV<br>Coverage', align : 'center', formatter : function(value, row, index) {
		        	if (row.npvrIds && row.npvrIds.length > 0) {
		        		return "<input id='tvCoverageCk_" + index + "' type='checkbox' checked='checked' onclick='changeTvCoverage(" + index + ")'>";
		        	} else {
		        		return "<input id='tvCoverageCk_" + index + "' type='checkbox' onclick='changeTvCoverage(" + index + ")'>";
		        	}
		        }},
		        {field : 'startDateTime', title : 'Start Datetime', width : 120, align : 'center'},
		        {field : 'teamA', title : 'Team / Player A', width : 150, align : 'center'},
		        {field : 'teamB', title : 'Team / Player B', width : 150, align : 'center'},
		        {field : 'status', title : 'Status', width : 100, align : 'center'},
		        {field : 'qvodMarker', title : 'QVOD Time<br>Markers', align : 'center', formatter : function(value,row,index) {
		        	return "<a href='#'>Edit</a>";
		        }},
		        {field : 'overrideCk', title : 'Override<br>Match<br>Date / Time', align : 'center', formatter : function(value, row, index) {
		        	return "<input type='checkbox'>";
		        }},
		        {field : 'actualStartDate', title : 'Actual Start Date', width : 150, align : 'center'},
		        {field : 'actualStartTime', title : 'Actual Start Time', width : 150, align : 'center'},
		        {field : 'actualEndDate', title : 'Actual End Date', width : 150, align : 'center'},
		        {field : 'actualEndTime', title : 'Actual End Time', width : 150, align : 'center'},
		        {field : 'npvrIds', title : 'NPVR IDs', width : 120, align : 'center', formatter : function(value,row,index) {
		        	var cell = "";
		        	if (value) {
		        		var npvrs = value.split(",");
			        	if (npvrs.length) {
			        		for (var i = 0; i < npvrs.length; i++) {
			        			cell += npvrs[i] + "<br>";
			        		}
			        	}
		        	}
					return cell += "<a href='#' onclick='openEditPopup(" + index + ")'>Edit</a>";
		        }},
		        {field : 'copyNpvrFrom', title : 'Copy<br>NPVR IDs<br>from', align : 'center', formatter : function(value,row,index) {
		        	if (row.npvrIds && row.npvrIds.length > 0) {
		        		return "<input name='copyNpvrFrom' type='radio' value='" + index + "'>";
		        	}
		        }},
		        {field : 'copyNpvrTo', title : 'Copy<br>NPVR IDs<br>to', align : 'center', formatter : function(value, row, index) {
		        	if (!row.npvrIds || row.npvrIds.length == 0) {
		        		return "<a class='copy_npvr_to_btn' onclick='copyNpvrIds(" + index + ")'>Here</a>";
		        	}
		        }}
		    ]],
		    onLoadSuccess : function(data) {
		    	$(".copy_npvr_to_btn").linkbutton();
		    }
		});
		
		$("#editNpvrPopup").dialog({
			title : "Edit NPVR IDs",
			width : 400,
			height : 320,
			closed : true,
			modal : true,
			buttons : [{
				text : "Save",
				handler : function() {
					verifyNpvrIds();
				}
			}, {
				text : "Cancel",
				handler : function() {
					$("#editNpvrPopup").dialog("close");
				}
			}]
		});
	});
	
	
	function verifyNpvrIds() {
		var npvrIdArr = $("#npvrIds").textbox("getText").trim().split("\n");
		var row = $("#fixture_dg").datagrid("getSelected");
		if (npvrIdArr.length && $("#editNpvrForm").form("validate")) {
			$.messager.progress({
				msg : 'Loading...',
				text : ''
			});
			var data = {
				npvrIdArr : npvrIdArr
			};
			$.ajax({
				url : '${ctx}/npvr/verifyNpvrIds.html',
				data : data,
				type : 'post',
				traditional : true,
				success : function(response) {
					$.messager.progress("close");
					if (response.success) {
 						saveNpvrIds(row, response.validNpvrs);
					} else if (response.msg) {
						$.messager.alert("", response.msg, "error");
					} else {
						$.messager.alert("", "Failed to verify NPVR IDs. Please try again later.", "error");
					}
				},
				error : function() {
					$.messager.alert("", "Failed to verify NPVR IDs. Please try again later.", "error");
				}
			});
		}
	}
	
	function saveNpvrIds(row, validNpvrs) {
		var arr = new Array();
		for (var i in validNpvrs) {
			var validNpvr = validNpvrs[i];
			var npvrMapping = {
					sportType : row.sportType,
					channelNo : validNpvr["channelNo"],
					fixtureId : row.fixtureId,
					npvrId : validNpvr["npvrId"]
			}
			arr.push(npvrMapping);
		}
		/* var data = new Object();
		data['npvrMappingList'] = JSON.stringify(arr); */
		var data = {
				"npvrMappingList" : JSON.stringify(arr),
				"fixtureId" : row.fixtureId,
				"sportType" : row.sportType
		}
		$.post("${ctx}/npvr/saveNpvrIds.html", data,
			function(response) {
				if (response.success) {
					$.messager.alert("", "Save NPVR IDs successfully.", "info");
					$("#editNpvrPopup").dialog("close");
					var updateIndex = $("#fixture_dg").datagrid("getRowIndex", row); 
					$("#fixture_dg").datagrid("updateRow", {
						index : updateIndex,
						row : {
							channelNos : response.channelNos,
							npvrIds : response.npvrIds
						}
					});
				} else if (response.msg) {
					$.messager.alert("", response.msg, "error");
				} else {
					$.messager.alert("", "Failed to save NPVR IDs.", "error");
				}
			}, 'JSON').error(function() {
				$.messager.alert("", "Failed to save NPVR IDs.", "error");
		});		
	} 
	
	function doSearch() {
		if ($("#searchForm").form("validate")) {
			ajaxLoading();
			var data = $("#searchForm").serialize();
			$.post("${ctx}/npvr/doSearch.html", data,
				function(response) {
					ajaxLoadEnd();
					if (response.success) {
						if (response.list.length) {
							$("#fixture_dg").datagrid("loadData", response.list);
						} else {
							$("#fixture_dg").datagrid("loadData", {rows : []});
						}
					} else if (response.msg) {
						$.messager.alert("", response.msg, "error");
					} else {
						$.messager.alert("", "Failed to search. Please try again.", "error");
					}
				}, 'JSON').error(function() {
					ajaxLoadEnd();
					$.messager.alert("", "Failed to search. Please try again.", "error");
			});
		}
	}
	
	function changeTvCoverage(index) {
		var ck = $("#tvCoverageCk_" + index);
		if (!ck.is(':checked')) {
			$.messager.confirm("Confirm", "Are you sure to clear the NPVR IDs?", function(r) {
				if (r) {
					var row = $("#fixture_dg").datagrid("getRows")[index];
					var data = {
							fixtureId : row.fixtureId,
							sportType : row.sportType
					}
					$.ajax({
						url : '${ctx}/npvr/clearNpvrIds.html',
						data : data,
						type : "post",
						success : function(response) {
							if (response.success) {
								$.messager.alert("", "Clear NPVR IDs successfully.", "info");
								$("#fixture_dg").datagrid("updateRow", {
									index : index,
									row : {
										npvrIds : ""
									}
								});
							} else {
								$.messager.alert("", response.msg, "error");
							}
						},
						error : function() {
							$.messager.alert("", "Failed to clear NPVR IDs. Please try again.", "error");
						}
					});
				} else {
					ck.prop("checked", "checked");
				}
			});
		} else {
			$.messager.alert("", "Please use copy NPVR IDs from / to instead.", "info");
			ck.prop("checked", false);
		}
	}
	
	function openEditPopup(index) {
		$("#npvrIds").textbox("clear");
		var row = $("#fixture_dg").datagrid("getRows")[index];
		if (row.npvrIds != null) {
			var npvrArr = row.npvrIds.split(",");
			if (npvrArr.length > 0) {
				var npvrIds = "";
				for (var i = 0; i < npvrArr.length; i++) {
					npvrIds += npvrArr[i] + "\n";
	    		}
				$("#npvrIds").textbox("setText", npvrIds);
			}
		}
		
		$("#editNpvrPopup").dialog("open");
	}
	
	function copyNpvrIds(index) {
		var fromIndex = $("input[name=copyNpvrFrom]:checked").val();
		if (fromIndex != undefined) {
			$.messager.confirm("Confirm", "Are you sure to copy the NPVR IDs to this fixture?", function(r) {
				if (r) {
					var fromRow = $("#fixture_dg").datagrid("getRows")[fromIndex];
					var toRow = $("#fixture_dg").datagrid("getRows")[index];
					var data = new Object();
					var npvrIdArr = fromRow.npvrIds.split(",");
					var channelNoArr = fromRow.channelNos.split(",");
					var npvrMappingList = new Array();
					for (var i in npvrIdArr) {
						var npvrMapping = {
								fixtureId : toRow.fixtureId,
								sportType : toRow.sportType,
								channelNo : parseInt(channelNoArr[i]),
								npvrId : npvrIdArr[i]
						}
						npvrMappingList.push(npvrMapping);
					}
					data["npvrMappingList"] = JSON.stringify(npvrMappingList);
					$.ajax({
						url : "${ctx}/npvr/saveNpvrIds.html",
						data : data,
						type : "post",
						success : function(response) {
							if (response.success) {
								$.messager.alert("", "Copy NPVR IDs successfully.", "info");
								$("#fixture_dg").datagrid("updateRow", {
									index : index,
									row : {
										channelNos : fromRow.channelNos,
										npvrIds : fromRow.npvrIds
									}
								});
							} else {
								$.messager.alert("", response.msg, "error");
							}
						},
						error : function() {
							$.messager.alert("", "Failed to copy NPVR IDs.", "error");
						}
					});
				}
			});
		} else {
			$.messager.alert("", "Please choose the NPVR IDs to copy from.", "info");
		}
	}
	
</script>

</head>
<body>
	<div id="searchDiv">
		<form id="searchForm">
			<table id="searchTb" style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<td align="right">Sport Type</td>
					<td>
						<select id="sportType" name="sportType" class="easyui-combobox" style="width:200px;" required="required">
							<option value="SOCCER" selected>Soccer</option>
							<option value="TENNIS">Tennis</option>
							<option value="SNOOKER">Snooker</option>
						</select>
					</td>
					<td align="right">Channel</td>
					<td>
						<input id="channelNo" name="channelNo" class="easyui-numberbox" style="width:200px;" data-options="min: 1, max: 999">
					</td>
				</tr>
				<tr align="right">
					<td>Tournament</td>
					<td>
						<select id="tournament" name="tournament" class="easyui-combobox" style="width:200px;"></select>
					</td>
					<td align="right">From</td>
					<td>
						<input id="fromDate"  name="fromDate" type="text" class="easyui-datebox" required="required" validType="validDate">
						<input id="fromTime" name="fromTime" class="easyui-timespinner"  style="width:80px;" required="required" >
					</td>
				</tr>
				<tr>
					<td align="right">TV Coverage</td>
					<td>
						<select id="tvCoverage" name="tvCoverage" class="easyui-combobox" style="width:200px;" required="required">
							<option value="">Any</option>
							<option value="yes">Yes</option>
							<option value="no">No</option>
							<option></option>
						</select>
					</td>
					<td align="right">To</td>
					<td>
						<input id="toDate"  name="toDate" type="text" class="easyui-datebox" required="required" validType="validDate">
						<input id="toTime" name="toTime" class="easyui-timespinner"  style="width:80px;" required="required" >
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
<!-- 						<span style="display: inline-block; padding-right:10px;">Note: All fields are requried</span> -->
						<a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch()">Search</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="contentDiv">
		<table id="fixture_dg" style="height: 640px;"></table>
	</div>
	<div id="popupDiv">
		<div id="editNpvrPopup">
			<form id="editNpvrForm">
				<table style="border-collapse: separate; border-spacing: 10px; width: 100%">
					<tr>
						<td align="center">
							Edit NPVR IDs for match Team A vs Team B<br>
							(One id per line.)
						</td>
					</tr>
					<tr>
						<td align="center">
							<input id="npvrIds" class="easyui-textbox" data-options="multiline:true, width:300, height:150, required: true, validType: 'npvrIds'" >
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>