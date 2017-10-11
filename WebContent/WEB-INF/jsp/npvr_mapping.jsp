<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NPVR Mapping</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#fromTime").timespinner("setValue", "00:00");
		$("#toTime").timespinner("setValue", "00:00");

		var data = [{
			id : 1,
			startDatetime : "2017-01-01 01:00",
			teamA : "China",
			teamB : "Japan",
			status : "Pre Match",
			npvrIds : ["123456789", "987654321"]
		},
		{
			id : 2,
			startDatetime : "2017-01-02 02:00",
			teamA : "China",
			teamB : "America",
			status : "Pre Match",
			npvrIds : []
		}];
		
		$("#fixture_dg").datagrid({
			data : data,
			singleSelect : true,
		    columns : [[
		        {field : 'id', title : 'ID', hidden : true},
		        {field : 'tvCoverageCk', title : 'TV<br>Coverage', align : 'center', formatter : function(value, row, index) {
		        	if (row.npvrIds && row.npvrIds.length > 0) {
		        		return "<input id='tvCoverageCk_" + index + "' type='checkbox' checked='checked' onclick='changeTvCoverage(this)'>";
		        	} else {
		        		return "<input id='tvCoverageCk_" + index + "' type='checkbox' onclick='changeTvCoverage(this)'>";
		        	}
		        }},
		        {field : 'startDatetime', title : 'Start Datetime', width : 150, align : 'center'},
		        {field : 'teamA', title : 'Team / Player A', width : 100, align : 'center'},
		        {field : 'teamB', title : 'Team / Player B', width : 100, align : 'center'},
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
		        {field : 'npvrIds', title : 'NPVR IDs', width : 100, align : 'center', formatter : function(value,row,index) {
		        	var cell = "";
		        	if (value) {
		        		for (var i = 0; i < value.length; i++) {
		        			cell += value[i] + "<br>";
		        		}
		        	}
					return cell += "<a href='#' onclick='openEditPopup(" + index + ")'>Edit</a>";
		        }},
		        {field : 'copyNpvrFrom', title : 'Copy<br>NPVR IDs<br>from', align : 'center', formatter : function(value,row,index) {
		        	if (row.npvrIds && row.npvrIds.length > 0) {
		        		return "<input name='copyNpvrFrom' type='radio'>";
		        	}
		        }},
		        {field : 'copyNpvrTo', title : 'Copy<br>NPVR IDs<br>to', align : 'center', formatter : function(value, row, index) {
		        	if (!row.npvrIds || row.npvrIds.length == 0) {
		        		return "<a class='copy_npvr_to_btn' onclick='copyNpvr()'>Here</a>";
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
					
				}
			}, {
				text : "Cancel",
				handler : function() {
					$("#editNpvrPopup").dialog("close");
				}
			}]
		});
	});
	
	function changeTvCoverage(_this) {
		var ck = $(_this);
		if (!ck.is(':checked')) {
			$.messager.confirm("Confirm", "Are you sure to clear the NPVR IDs?", function(r) {
				if (!r) {
					ck.prop("checked", "checked");
				}
			});
		} else {
			$.messager.alert("", "Please use copy NPVR IDs from / to instead.", "Info");
			ck.prop("checked", false);
		}
	}
	
	function openEditPopup(index) {
		$("#npvrIds").textbox("clear");
		var row = $("#fixture_dg").datagrid("getRows")[index];
		if (row.npvrIds && row.npvrIds.length > 0) {
			var npvrIds = "";
			for (var i = 0; i < row.npvrIds.length; i++) {
				npvrIds += row.npvrIds[i] + "\r\n";
    		}
			$("#npvrIds").textbox("setText", npvrIds);
		}
		
		$("#editNpvrPopup").dialog("open");
	}
	
	function copyNpvr() {
		var from = $("input[name=copyNpvrFrom]:checked").val();
		if (from != undefined) {
			$.messager.confirm("Confirm", "Are you sure to copy the NPVR IDs to this fixture?", function(r) {
				if (r) {
					
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
							<option value="soccer">Soccer</option>
							<option value="tennis">Tennis</option>
						</select>
					</td>
					<td align="right">Channel</td>
					<td>
						<select id="channel" name=""channel"" class="easyui-combobox" style="width:200px;" required="required">
						</select>
					</td>
				</tr>
				<tr align="right">
					<td>Tournament</td>
					<td>
						<select id="tournament" name="tournament" class="easyui-combobox" style="width:200px;" required="required">
						</select>
					</td>
					<td align="right">From</td>
					<td>
						<input id="fromDate"  name="fromDate" type="text" class="easyui-datebox" required="required">
						<input id="fromTime" name="fromTime" class="easyui-timespinner"  style="width:80px;" required="required">
					</td>
				</tr>
				<tr>
					<td align="right">TV Coverage</td>
					<td>
						<select id="tvCoverage" name=""tvCoverage"" class="easyui-combobox" style="width:200px;" required="required">\
							<option value="">Any</option>
							<option value="yes">Yes</option>
							<option value="no">No</option>
							<option></option>
						</select>
					</td>
					<td align="right">To</td>
					<td>
						<input id="toDate"  name="toDate" type="text" class="easyui-datebox" required="required">
						<input id="toTime" name="toTime" class="easyui-timespinner"  style="width:80px;" required="required">
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<span style="display: inline-block; padding-right:10px;">Note: All fields are requried</span>
						<a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">Search</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="contentDiv">
		<table id="fixture_dg"></table>
	</div>
	<div id="popupDiv">
		<div id="editNpvrPopup">
			<form id="editNpvrForm">
				<table style="border-collapse: separate; border-spacing: 10px; width: 100%">
					<tr>
						<td align="center">Edit NPVR IDs for match Team A vs Team B</td>
					</tr>
					<tr>
						<td align="center">
							<input id="npvrIds" class="easyui-textbox" data-options="multiline:true, width:300, height:200" >
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>