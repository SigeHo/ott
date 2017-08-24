<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
div.centent {
	float: left;
	text-align: center;
	margin: 10px;
}

div.centent span {
	display: block;
	margin: 2px 2px;
	padding: 4px 10px;
	background: #898989;
	cursor: pointer;
	font-size: 12px;
	color: white;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$('#rank_dg').datagrid().datagrid('enableCellEditing');
		$('#rank_dg').datagrid({
			onAfterEdit: function(	index,row,changes) {
				if (!($.isEmptyObject(changes))) {
				}
			}
		})
	});
	
	function dateFormatter(value,row,index) {
		if (value != "") {
			var date = new Date(value);
			return date.format("yyyy-MM-dd hh:mm:ss");
		} else {
			return "n/a";
		}
	}
	
	function loadData() {
		var rank = $('#rank_dg').datagrid("getSelected");
		if (!rank) {
			$.messager.alert('','Please select one record above.','warning');
		} else {
			/* var playerId = rank.playerId;
			$("#point_dg").datagrid('options').url = "${ctx}/snooker/rank/listPoint.html?playerId=" + playerId;
			$("#point_dg").datagrid('reload'); */
			var pointData = rank.snookerPointList;
			$("#point_dg").datagrid('loadData', pointData);
			
		}
	}
	
	function doSearch() {
		var playerNameForSearch = $('#playerNameForSearch').val();
		playerNameForSearch = playerNameForSearch.Trim();
		$('#playerNameForSearch').val(playerNameForSearch);
		/* url = '${ctx}/snooker/rank/listRank.html?playerNameForSearch='+playerNameForSearch;
		$('#rank_dg').datagrid('options').url = url;
		$('#rank_dg').datagrid('reload'); */
		$('#rank_dg').datagrid('load', {
			playerNameForSearch: playerNameForSearch
		});
	}
	
	function clearSearch() {
		$("#playerNameForSearch").val("");
	}
	
	function save() {
		if($("#rank_dg").datagrid("getChanges").length) {
			debugger;
			var updated = $("#rank_dg").datagrid('getChanges', 'updated');
			var effectRow = new Object();
			effectRow['updated'] = JSON.stringify(updated);
			$.post("${ctx}/snooker/rank/batchUpdateRank.html", effectRow, function(response) {
				console.log(response);
			}, 'JSON').error(function() {
				$.messager.alert("", "Failed to save the changes.", "error");  
			});		
		} else {
			$.messager.alert("", "Nothing is changed.", "warning");  
		}
	}
	
	function reset() {
		$("#rank_dg").datagrid("rejectChanges");
	}
	
	function deleteRank() {
		var row = $("#rank_dg").datagrid("getSelected");
		if (row) {
			$.messager.confirm('Confirm', 'Are you sure to delete this record?', function(r) {
				if (r) {
					$.post("${ctx}/snooker/rank/deleteRank.html", {rankId : row.rankId}, function(response) {
						console.log(response);
					}, 'JSON').error(function() {
						$.messager.alert("", "Failed to delete this record.", "error");  
					});
				}
			});
		} else {
			$.messager.alert("", "No record is selected.", "warning");
		}
	}

</script>
</head>
<body>
	<table id="rank_dg" class="easyui-datagrid" 
			toolbar="#rank_toolbar" pagination="true"
			pageSize="10" pageList="[10,20,30,40]"
			rownumbers="false" fitColumns="true" 
			singleSelect="true" striped="true"
			url="${ctx}/snooker/rank/listRank.html"
			idField="rankId">
		<thead>
			<tr>
				<th field="rankId" rowspan="2" hidden="true">Rank Id</th>
				<th field="rankTitle" rowspan="2" editor="text">Rank Title</th>
				<th field="rankYear" rowspan="2" editor="text">Season</th>
				<th field="playerId" rowspan="2" sortable="true">Player ID</th>
				<th field="nameCn" rowspan="2" editor="text">Player Name CN</th>
				<th field="nameEn" rowspan="2" editor="text" sortable="true">Player Name EN</th>
				<th field="nameTr" rowspan="2" editor="text">Player Name TR</th>
				<th field="nationality" rowspan="2" editor="text">Nationality</th>
				<th field="rank" rowspan="2" editor="numberbox" sortable="true">Rank</th>
				<th colspan="3">Point</th>
				<th field="ptcPoint" rowspan="2" editor="numberbox" >PTC Point</th>
				<th field="totalPoint" rowspan="2" editor="numberbox" >Total Point</th>
				<th field="lastUpdatedTime" rowspan="2" formatter="dateFormatter">Last Updated Time</th>
			</tr>
			<tr>
				<th field="point1" editor="numberbox">Season 1</th>
				<th field="point2" editor="numberbox">Season 2</th>
				<th field="point3" editor="numberbox">Season 3</th>
			</tr>
		</thead>
	</table>
	<br />
	<table id="point_dg" class="easyui-datagrid" 
			toolbar="#point_toolbar" striped="true"
			rownumbers="false" fitColumns="true" 
			singleSelect="true" idFiled="pointId">
		<thead>
			<tr>
				<th field="pointId" hidden="true" >Point ID</th>
				<th field="leagueId" >League ID</th>
				<th field="leagueNameCn" >League Name CN</th>
				<th field="leagueNameEn" >League Name EN</th>
				<th field="leagueNameTR" >League Name TR</th>
				<th field="sn" >SN</th>
				<th field="lastUpdatedTime" formatter="dateFormatter">Last Updated Time</th>
			</tr>
		</thead>
	</table>	
	
	<div id="rank_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="reset()">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRank()">Delete</a>
				</td>
				<td align="right">
					<span style="padding-left:20px">Player Name: <input type="text" id="playerNameForSearch" name="playerNameForSearch" maxlength="50"/></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="point_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<a href="#" class="easyui-linkbutton" plain="false" onclick="loadData()">Load Data</a>
			</tr>
		</table>
	</div>

</body>
</html>