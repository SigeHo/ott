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
	cursor: leveler;
	font-size: 12px;
	color: white;
}

tr.changed-row {
	background-color: yellow;
}
</style>

<script type="text/javascript">
	var editIndex = undefined;
	
	function endEditing() {
		if (editIndex == undefined){return true}
		if ($('#player_dg').datagrid('validateRow', editIndex)){
			$('#player_dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function onClickRow(index,row) {
		if (editIndex != index) {
			if (endEditing()) {
				editIndex = index;
			} else {
				setTimeout(function() {
					$('#player_dg').datagrid('selectRow', editIndex);
				},0);
			}
		}
	}

	function onDblClickCell(index,field,value) {
		$('#player_dg').datagrid('selectRow', index).datagrid('beginEdit', index);
		var ed = $('#player_dg').datagrid('getEditor', {index:index,field:field});
		if (ed){
			($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
		}
	}
	
	function dateFormatter(value, row, index) {
		if (value != "") {
			var date = new Date(value);
			return date.format("MM/dd/yyyy hh:mm:ss");
		} else {
			return "";
		}
	}
	
	function doSearch() {
		var playerNameForSearch = $('#playerNameForSearch').val();
		playerNameForSearch = playerNameForSearch.Trim();
		$('#playerNameForSearch').val(playerNameForSearch);
		$('#player_dg').datagrid('load', {
			playerNameForSearch : playerNameForSearch
		});
	}

	function clearSearch() {
		$("#playerNameForSearch").val("");
	}

	function savePlayer() {
		if (endEditing()) {
			if ($("#player_dg").datagrid("getChanges").length) {
				var inserted = $("#player_dg").datagrid('getChanges', 'inserted');
				var updated = $("#player_dg").datagrid('getChanges', 'updated');
				var deleted = $("#player_dg").datagrid('getChanges', 'deleted');
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
				$.post("${ctx}/snooker/player/savePersonChanges.html", effectRow,
					function(response) {
						if (response.success) {
							$.messager.alert("", "Save changes successfully .", "info", function() {
								$("#player_dg").datagrid("reload");
								$("#savePlayerBtn").linkbutton("disable");
							});
						} else if (response.msg) {
							$.messager.alert("", response.msg, "error");
						} else {
							$.messager.alert("", "Failed to save the changes.", "error");
						}
					}, 'JSON').error(function() {
					$.messager.alert("", "Failed to save the changes.", "error");
				});
			} else {
				$.messager.alert("", "Nothing is changed.", "warning");
			}
		}
	}
	
	function reset() {
		$("#player_dg").datagrid("rejectChanges");
		$("#savePlayerBtn").linkbutton("disable");
	}

	function addRow() {
		var dg = $("#player_dg");
		if (endEditing()) {
			dg.datagrid('appendRow', {
				lastPublishedDate : ""
			});
			editIndex = dg.datagrid('getRows').length - 1;
			dg.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
		}
	}
	
	function deleteRow() {
		var dg = $("#player_dg");
		if (editIndex == undefined) {
			return
		}
		dg.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	
</script>
</head>
<body>
	<table id="player_dg" class="easyui-datagrid" data-options="
		title: 'Snooker Player(double click to edit)',
		singleSelect: true,
		toolbar: '#player_toolbar',
		pagination: true,
		pageSize: 10,
		url: '${ctx}/snooker/player/listPlayer.html',
		singleSelect: true,
		onClickRow: onClickRow,
		onDblClickCell: onDblClickCell
	">
		<thead>
			<tr>
				<th field="playerId" width="150px">Player ID</th>
				<th field="nameCn" editor="textbox" width="150px">Player Name CN</th>
				<th field="nameEn" editor="textbox" width="150px">Player Name EN</th>
				<th field="nameTr" editor="textbox" width="150px">Player Name TR</th>
				<th field="sex" editor="textbox" width="150px">Sex</th>
				<th field="nationality" editor="textbox" width="150px">Nationality</th>
				<th field="birthdayStr" editor="datebox" width="150px">Birthday</th>
				<th field="birthday" hidden="true" width="150px">Birthday</th>
				<th field="height" editor="numberbox" width="150px">Height</th>
				<th field="weight" editor="numberbox" width="150px">Weight</th>
				<th field="score" editor="numberbox" width="150px">Score</th>
				<th field="maxScoreNum" editor="numberbox" width="150px">Max Score Num</th>
				<th field="currentRank" editor="numberbox" width="150px">Current Rank</th>
				<th field="highestRank" editor="numberbox" width="150px">Highest Rank</th>
				<th field="transferTimeStr" editor="datebox" width="150px">Transfer Time</th>
				<th field="transferTime" hidden="true" width="150px">Transfer Time</th>
				<th field="totalMoney" editor="numberbox" width="150px">Total Money</th>
				<th field="winRecord" editor="numberbox" width="150px">Win Record</th>
				<th field="point" editor="numberbox" width="150px">Point</th>
				<th field="experience" editor="{type:'textarea', options:{rows:5}}" width="150px">Experience</th>
				<th field="remark" editor="{type:'textarea', options:{rows:5}}" width="150px">Remark</th>
				<th field="lastPublishedDate" formatter="dateFormatter" width="150px">Last Published Date</th>
			</tr>
		</thead>
	</table>
	
	<div id="player_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="savePlayerBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePlayer()">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset()">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow()">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow()">Delete</a>
				</td>
				<td align="right">
					<span style="padding-left:20px">Player Name: <input type="text" id="playerNameForSearch" name="playerNameForSearch" maxlength="50"/></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>
