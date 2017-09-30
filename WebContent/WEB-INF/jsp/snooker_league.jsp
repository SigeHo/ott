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
	var leagueEditIndex = undefined;
	var levelEditIndex = undefined;
	
	function endEditing(type) {
		var dg = null;
		var editIndex = undefined;
		if (type == "league") {
			dg = $("#league_dg")
			editIndex = leagueEditIndex;
		} else {
			dg = $("#level_dg")
			editIndex = levelEditIndex;
		}
		if (editIndex == undefined) { return true; }
		if (dg.datagrid('validateRow', editIndex)) {
			dg.datagrid('endEdit', editIndex);
			if (type == "league") {
				leagueEditIndex = undefined;
			} else {
				levelEditIndex = undefined;
			}
			return true;
		} else {
			return false;
		}
	}

	function onDblClickCell(index,field,value) {
		var id  = $(this).attr("id");
		var editIndex = undefined;
		var dg = null;
		var type = undefined;
		if (id == "league_dg") {
			dg = $("#league_dg");
			type = "league";
			editIndex = leagueEditIndex;
			$("#saveLeagueBtn").linkbutton("enable");
		} else {
			dg = $("#level_dg");
			editIndex = levelEditIndex;
			type = "level";
			$("#saveLevelBtn").linkbutton("enable");
		}
		if (endEditing(type)) {
			dg.datagrid('selectRow', index).datagrid('beginEdit', index);
			var ed = dg.datagrid('getEditor', {
				index : index,
				field : field
			});
			if (ed) {
				($(ed.target).data('text') ? $(ed.target).text('text') : $(ed.target)).focus();
			}
			if (id == "league_dg") {
				leagueEditIndex = index;
			} else {
				levelEditIndex = index;
			}
		} else {
			setTimeout(function() {
				dg.datagrid('selectRow', editIndex);
			}, 0);
		}
	}

	function onLoadSuccess(data) {
		var id = $(this).attr("id");
		if (id == "league_dg") {
			$("#saveLeagueBtn").linkbutton("disable");
			var league = $("#league_dg").datagrid("getSelected");
			if (!league || undefined == league) {
				$("#level_dg").datagrid("loadData", {rows : []});
			}
		} else {
			$("#saveLevelBtn").linkbutton("disable");
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
	
	function onClickRow(index, row) {
		var id = $(this).attr("id");
		if (id == 'league_dg') {
			var levelData = row.ottSnookerLevelList;
			if (levelData) {
				$("#level_dg").datagrid('loadData', levelData);	
			} else {
				$("#level_dg").datagrid('loadData', {rows : []});
			}
			//leagueEditIndex = index;
		} else {
			//levelEditIndex = index;
		}
	}

	function doSearch() {
		var leagueNameForSearch = $('#leagueNameForSearch').val();
		leagueNameForSearch = leagueNameForSearch.Trim();
		$('#leagueNameForSearch').val(leagueNameForSearch);
		$('#league_dg').datagrid('load', {
			leagueNameForSearch : leagueNameForSearch
		});
	}

	function clearSearch() {
		$("#leagueNameForSearch").val("");
	}

	function saveLeague() {
		if (endEditing('league')) {
			if ($("#league_dg").datagrid("getChanges").length) {
				var inserted = $("#league_dg").datagrid('getChanges', 'inserted');
				var updated = $("#league_dg").datagrid('getChanges', 'updated');
				var deleted = $("#league_dg").datagrid('getChanges', 'deleted');
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
				$.post("${ctx}/snooker/league/saveLeagueChanges.html", effectRow,
					function(response) {
						if (response.success) {
							$.messager.alert("", "Save changes successfully .", "info", function() {
								$("#league_dg").datagrid("reload");
								$("#saveLeagueBtn").linkbutton("disable");
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
	
	function saveLevel() {
		if ($("#level_dg").datagrid("getChanges").length) {
			var inserted = $("#level_dg").datagrid('getChanges', 'inserted');
			var updated = $("#level_dg").datagrid('getChanges', 'updated');
			var deleted = $("#level_dg").datagrid('getChanges', 'deleted');
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
			var league = $("#league_dg").datagrid("getSelected");
			effectRow['league'] = JSON.stringify(league);
			$.post("${ctx}/snooker/league/saveLevelChanges.html", effectRow,
				function(response) {
					if (response.success) {
						$.messager.alert("", "Save changes successfully .", "info", function() {
							$("#level_dg").datagrid("reload");
							$("#saveLevelBtn").linkbutton("disable");
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

	function reset(type) {
		var dg = null;
		if (type == "league") {
			$("#league_dg").datagrid("rejectChanges");
			$("#saveLeagueBtn").linkbutton("disable");
			$("#level_dg").datagrid("loadData", {rows : []});
		} else {
			$("#level_dg").datagrid("rejectChanges");
			$("#saveLevelBtn").linkbutton("disable");
		}
	}

	function addRow(type) {
		var dg = null;
		if (type == "league") {
			dg = $("#league_dg");
		} else {
			dg = $("#level_dg");
			var league = $("#league_dg").datagrid("getSelected");
			if (null == league || undefined == league || undefined == league.leagueId || "" == league.leagueId) {
				$.messager.alert("", "Please select one league above.", "info");
				return;
			}
		}
		var editIndex = undefined;
		if (endEditing(type)) {
			dg.datagrid('appendRow', {
				lastPublishedDate : ""
			});
			editIndex = dg.datagrid('getRows').length - 1;
			dg.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			if (type == "league") {
				leagueEditIndex = editIndex;
				$("#level_dg").datagrid("loadData", {rows : []});
			} else {
				levelEditIndex = editIndex;
			}
			toggleSaveBtn(type);
		}
	}
	
	function deleteRow(type) {
		var dg = null;
		if (type == "league") {
			dg = $("#league_dg");
		} else {
			dg = $("#level_dg");
		}
		var editIndex = undefined;
		if (type == "league") {
			editIndex = leagueEditIndex;
		} else {
			editIndex = levelEditIndex;
		}
		if (editIndex == undefined) {
			return
		}
		dg.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		if (type == "league") {
			leagueEditIndex = undefined;
			$("#level_dg").datagrid("loadData", {rows : []});
		} else {
			levelEditIndex = undefined;
		}
		toggleSaveBtn(type);
	}
	
	function toggleSaveBtn(type) {
		var dg = null;
		if (type == "league") {
			dg = $("#league_dg");
			if (dg.datagrid("getChanges").length) {
				$("#saveLeagueBtn").linkbutton("enable");
			} else {
				$("#saveLeagueBtn").linkbutton("disable");
			}
		} else {
			dg = $("#level_dg");
			if (dg.datagrid("getChanges").length) {
				$("#saveLevelBtn").linkbutton("enable");
			} else {
				$("#saveLevelBtn").linkbutton("disable");
			}
		}
	}
</script>
</head>
<body>
	<table id="league_dg" class="easyui-datagrid" data-options="
		title: 'Snooker League(double click to edit)',
		singleSelect: true,
		toolbar: '#league_toolbar',
		pagination: true,
		pageSize: 10,
		url: '${ctx}/snooker/league/listLeague.html',
		singleSelect: true,
		onClickRow: onClickRow,
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess
	">
		<thead>
			<tr>
				<th field="leagueId"  editor="numberbox" width="150px">League ID</th>
				<th field="leagueNameCn" editor="text" width="150px">League Name CN</th>
				<th field="leagueNameEn" editor="text" width="150px">League Name EN</th>
				<th field="leagueNameTr" editor="text" width="150px">League Name TR</th>
				<th field="startTime" hidden="true"></th>
				<th field="startTimeStr" editor="datetimebox" width="150px">Start Time</th>
				<th field="endTime" hidden="true"></th>
				<th field="endTimeStr" editor="datetimebox" width="150px">End Time</th>
				<th field="color" editor="text" width="150px">Color</th>
				<th field="remark" editor="text" width="150px">Remark</th>
				<th field="money" editor="numberbox" width="150px">Money</th>
				<th field="lastPublishedDate" formatter="dateFormatter" width="150px">Last Published Date</th>
			</tr>
		</thead>
	</table>
	<br />
	<table id="level_dg" class="easyui-datagrid" data-options="
		title: 'Snooker Level(double click to edit)',
		toolbar: '#level_toolbar',
		striped: true,
		singleSelect: true,
		idField: 'levelId',
		fitColumns: true,
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess,
		onClickRow:onClickRow
	">
		<thead>
			<tr>
				<th field="levelId" hidden="true" >Level ID</th>
				<th field="levelRounds" data-options="editor:'text'" >Level Rounds</th>
				<th field="matchLevels" data-options="editor:'text'" >Match Levels</th>
				<th field="matchGroup" data-options="editor:'numberbox'" >Match Group</th>
				<th field="remark" data-options="editor:'text'" >Remark</th>
				<th field="lastPublishedDate" formatter="dateFormatter">Last Published Date</th>
			</tr>
		</thead>
	</table>	
	
	<div id="league_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="saveLeagueBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveLeague()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('league')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('league')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('league')">Delete</a>
				</td>
				<td align="right">
					<span style="padding-left:20px">League Name: <input type="text" id="leagueNameForSearch" name="leagueNameForSearch" maxlength="50"/></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="level_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="saveLevelBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveLevel()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('level')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('level')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('level')">Delete</a>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>
