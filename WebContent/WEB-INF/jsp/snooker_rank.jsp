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
	var editIndex = undefined;
	
	function endEditing() {
		if (editIndex == undefined) { return true; }
		if ($('#rank_dg').datagrid('validateRow', editIndex)) {
			$('#rank_dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	function onClickCell(index,field,value) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#rank_dg').datagrid('selectRow', index).datagrid('beginEdit', index);
				var ed = $('#rank_dg').datagrid('getEditor', {
					index : index,
					field : field
				});
				if (ed) {
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function() {
					$('#rank_dg').datagrid('selectRow', editIndex);
				}, 0);
			}
		} 
	}
	
	function onEndEdit(index, row){
		toggleSaveBtn();
	}
	
	function onLoadSuccess(data) {
		if ($("#rank_dg").datagrid("getChanges").length) {
			$("#rank_dg").datagrid("rejectChanges");		
		}
		$("#saveBtn").linkbutton("disable");
	}

	function dateFormatter(value, row, index) {
		if (value != "") {
			var date = new Date(value);
			return date.format("MM/dd/yyyy hh:mm:ss");
		} else {
			return "";
		}
	}

	function loadData() {
		var rank = $('#rank_dg').datagrid("getSelected");
		if (!rank) {
			$.messager.alert('', 'Please select one record above.', 'warning');
		} else {
			var pointData = rank.snookerPointList;
			$("#point_dg").datagrid('loadData', pointData);

		}
	}

	function doSearch() {
		var playerNameForSearch = $('#playerNameForSearch').val();
		playerNameForSearch = playerNameForSearch.Trim();
		$('#playerNameForSearch').val(playerNameForSearch);
		$('#rank_dg').datagrid('load', {
			playerNameForSearch : playerNameForSearch
		});
	}

	function clearSearch() {
		$("#playerNameForSearch").val("");
	}

	function save() {
		$("#rank_dg").datagrid("loading");
		$("#rank_dg").datagrid("endEdit", editIndex);
		if ($("#rank_dg").datagrid("getChanges").length) {
			var inserted = $("#rank_dg").datagrid('getChanges', 'inserted');
			var updated = $("#rank_dg").datagrid('getChanges', 'updated');
			var deleted = $("#rank_dg").datagrid('getChanges', 'deleted');
			var effectRow = new Object();
			if(inserted.length) {
				effectRow['inserted'] = JSON.stringify(inserted);
			}
			if(updated.length) {
				effectRow['updated'] = JSON.stringify(updated);
			}
			if(deleted.length) {
				effectRow['deleted'] = JSON.stringify(deleted);
			}
			$.post("${ctx}/snooker/rank/saveChanges.html", effectRow,
					function(response) {
						if (response.success) {
							$("#saveBtn").linkbutton('disable');
							$("#rank_dg").datagrid("reload");
							$.messager.alert("", "Save changes successfully .", "info");
							$("#rank_dg").datagrid("loaded");
						}
					}, 'JSON').error(function() {
				$.messager.alert("", "Failed to save the changes.", "error");
				$("#rank_dg").datagrid("loaded");
			});
		} else {
			$.messager.alert("", "Nothing is changed.", "warning");
			$("#rank_dg").datagrid("loaded");
		}
	}

	function reset() {
		$("#rank_dg").datagrid("rejectChanges");
		$("#saveBtn").linkbutton("disable");
	}

	function addRank() {
		if (endEditing()) {
			$('#rank_dg').datagrid('appendRow', {
				lastUpdatedTime : ""
			});
			editIndex = $('#rank_dg').datagrid('getRows').length - 1;
			$('#rank_dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			toggleSaveBtn();
		}
	}
	
	function deleteRank() {
		if (editIndex == undefined) {
			return
		}
		$('#rank_dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		editIndex = undefined;
		toggleSaveBtn();
	}
	
	function toggleSaveBtn() {
		debugger;
		var changes = $("#rank_dg").datagrid("getChanges");
		if ($("#rank_dg").datagrid("getChanges").length) {
			$("#saveBtn").linkbutton("enable");
		} else {
			$("#saveBtn").linkbutton("disable");
		}
	}
</script>
</head>
<body>
	<table id="rank_dg" class="easyui-datagrid" data-options="
				singleSelect: true,
				toolbar: '#rank_toolbar',
				pagination: true,
				pageSize: 10,
				url: '${ctx}/snooker/rank/listRank.html',
				singleSelect: true,
				onClickCell: onClickCell,
				onEndEdit: onEndEdit,
				onLoadSuccess: onLoadSuccess
				">
		<thead>
			<tr>
				<th field="rankId" rowspan="2" editor="text" hidden="true">Rank Id</th>
				<th field="rankTitle" rowspan="2" editor="text">Rank Title</th>
				<th field="rankYear" rowspan="2" editor="text">Season</th>
				<th field="playerId" rowspan="2" editor="numberbox" sortable="true">Player ID</th>
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
						<a id="saveBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset()">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRank()">Add</a>
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