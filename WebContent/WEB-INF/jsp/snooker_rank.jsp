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

tr.changed-row {
	background-color: yellow;
}
</style>

<script type="text/javascript">
	var rankEditIndex = undefined;
	var pointEditIndex = undefined;
	
	function endEditing(type) {
		var dg = null;
		var editIndex = undefined;
		if (type == "rank") {
			dg = $("#rank_dg")
			editIndex = rankEditIndex;
		} else {
			dg = $("#point_dg")
			editIndex = pointEditIndex;
		}
		if (editIndex == undefined) { return true; }
		if (dg.datagrid('validateRow', editIndex)) {
			dg.datagrid('endEdit', editIndex);
			if (type == "rank") {
				rankEditIndex = undefined;
			} else {
				pointEditIndex = undefined;
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
		if (id == "rank_dg") {
			dg = $("#rank_dg");
			type = "rank";
			editIndex = rankEditIndex;
			$("#saveRankBtn").linkbutton("enable");
		} else {
			dg = $("#point_dg");
			editIndex = pointEditIndex;
			type = "point";
			$("#savePointBtn").linkbutton("enable");
		}
		
		if (endEditing(type)) {
			dg.datagrid('selectRow', index).datagrid('beginEdit', index);
			var ed = dg.datagrid('getEditor', {
				index : index,
				field : field
			});
			if (ed) {
				($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
			}
			if (id == "rank_dg") {
				rankEditIndex = index;
			} else {
				pointEditIndex = index;
			}
		} else {
			setTimeout(function() {
				dg.datagrid('selectRow', editIndex);
			}, 0);
		}
	}
	
	function onLoadSuccess(data) {
		var id = $(this).attr("id");
		if (id == "rank_dg") {
			$("#saveBtn").linkbutton("disable");
			var rank = $("#rank_dg").datagrid("getSelected");
			if (!rank || undefined == rank) {
				$("#point_dg").datagrid("loadData", {rows : []});
			}
		} else {
			$("#savePointBtn").linkbutton("disable");
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
		if (id == "rank_dg") {
			var pointData = row.snookerPointList;
			if (pointData) {
				$("#point_dg").datagrid('loadData', pointData);
			} else {
				$("#point_dg").datagrid('loadData', {rows : []});
			}
			//rankEditIndex = index;
		} else {
			//pointEditIndex = index;
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

	function saveRank() {
		if (endEditing('rank')) {
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
				$.post("${ctx}/snooker/rank/saveRankChanges.html", effectRow,
					function(response) {
						if (response.success) {
							$.messager.alert("", "Save changes successfully .", "info", function() {
								$("#rank_dg").datagrid("reload");
								$("#saveRankBtn").linkbutton("disable");
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
	
	function savePoint() {
		if ($("#point_dg").datagrid("getChanges").length) {
			var inserted = $("#point_dg").datagrid('getChanges', 'inserted');
			var updated = $("#point_dg").datagrid('getChanges', 'updated');
			var deleted = $("#point_dg").datagrid('getChanges', 'deleted');
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
			var rank = $("#rank_dg").datagrid("getSelected");
			effectRow['rank'] = JSON.stringify(rank);
			$.post("${ctx}/snooker/rank/savePointChanges.html", effectRow,
				function(response) {
					if (response.success) {
						$.messager.alert("", "Save changes successfully .", "info", function() {
							$("#point_dg").datagrid("reload");
							$("#savePointBtn").linkbutton("disable");
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
		if (type == "rank") {
			$("#rank_dg").datagrid("rejectChanges");
			$("#saveRankBtn").linkbutton("disable");
			$("#point_dg").datagrid("loadData", {rows : []});
		} else {
			$("#point_dg").datagrid("rejectChanges");
			$("#savePointBtn").linkbutton("disable");
		}
	}

	function addRow(type) {
		var dg = null;
		if (type == "rank") {
			dg = $("#rank_dg");
		} else {
			dg = $("#point_dg");
			var rank = $("#rank_dg").datagrid("getSelected");
			if (null == rank || undefined == rank || undefined == rank.rankId || "" == rank.rankId) {
				$.messager.alert("", "Please select one rank above.", "info");
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
			if (type == "rank") {
				rankEditIndex = editIndex;
				$("#point_dg").datagrid("loadData", {rows : []});
			} else {
				pointEditIndex = editIndex;
			}
			toggleSaveBtn(type);
		}
	}
	
	function deleteRow(type) {
		var dg = null;
		if (type == "rank") {
			dg = $("#rank_dg");
		} else {
			dg = $("#point_dg");
		}
		var editIndex = undefined;
		if (type == "rank") {
			editIndex = rankEditIndex;
		} else {
			editIndex = pointEditIndex;
		}
		if (editIndex == undefined) {
			return
		}
		dg.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		if (type == "rank") {
			rankEditIndex = undefined;
			$("#point_dg").datagrid("loadData", {rows : []});
		} else {
			pointEditIndex = undefined;
		}
		toggleSaveBtn(type);
	}
	
	function toggleSaveBtn(type) {
		var dg = null;
		if (type == "rank") {
			dg = $("#rank_dg");
			if (dg.datagrid("getChanges").length) {
				$("#saveRankBtn").linkbutton("enable");
			} else {
				$("#saveRankBtn").linkbutton("disable");
			}
		} else {
			dg = $("#point_dg");
			if (dg.datagrid("getChanges").length) {
				$("#savePointBtn").linkbutton("enable");
			} else {
				$("#savePointBtn").linkbutton("disable");
			}
		}

	}
</script>
</head>
<body>
	<table id="rank_dg" class="easyui-datagrid" data-options="
		title: 'Snooker Rank (double click to edit)',
		singleSelect: true,
		toolbar: '#rank_toolbar',
		pagination: true,
		pageSize: 10,
		url: '${ctx}/snooker/rank/listRank.html',
		singleSelect: true,
		onClickRow: onClickRow,
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess
	">
		<thead>
			<tr>
				<th field="rankId" rowspan="2" hidden="true" width="150px">Rank Id</th>
				<th field="rankTitle" rowspan="2" editor="text" width="150px">Rank Title</th>
				<th field="rankYear" rowspan="2" editor="text" width="100px">Season</th>
				<th field="playerId" rowspan="2" editor="numberbox" sortable="true" width="100px">Player ID</th>
				<th field="nameCn" rowspan="2" editor="text" width="150px">Player Name CN</th>
				<th field="nameEn" rowspan="2" editor="text" sortable="true" width="150px">Player Name EN</th>
				<th field="nameTr" rowspan="2" editor="text" width="150px">Player Name TR</th>
				<th field="nationality" rowspan="2" editor="text" width="150px">Nationality</th>
				<th field="rank" rowspan="2" editor="numberbox" sortable="true" width="100px">Rank</th>
				<th colspan="3">Point</th>
				<th field="ptcPoint" rowspan="2" editor="numberbox" width="100px">PTC Point</th>
				<th field="totalPoint" rowspan="2" editor="numberbox" width="100px">Total Point</th>
				<th field="lastPublishedDate" rowspan="2" formatter="dateFormatter" width="150px">Last Updated Time</th>
			</tr>
			<tr>
				<th field="point1" editor="numberbox" width="100px">Season 1</th>
				<th field="point2" editor="numberbox" width="100px">Season 2</th>
				<th field="point3" editor="numberbox" width="100px">Season 3</th>
			</tr>
		</thead>
	</table>
	<br />
	<table id="point_dg" class="easyui-datagrid" data-options="
		toolbar: '#point_toolbar',
		striped: true,
		singleSelect: true,
		idField: 'pointId',
		fitColumns: true,
		title: 'Point Details (double click to edit)',
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess,
		onClickRow: onClickRow
	">
		<thead>
			<tr>
				<th field="pointId" hidden="true" >Point ID</th>
				<th field="leagueId" data-options="editor:'numberbox'" >League ID</th>
				<th field="leagueNameCn" data-options="editor:'text'" >League Name CN</th>
				<th field="leagueNameEn" data-options="editor:'text'" >League Name EN</th>
				<th field="leagueNameTr" data-options="editor:'text'" >League Name TR</th>
				<th field="sn" data-options="editor:'numberbox'" >SN</th>
				<th field="lastPublishedDate" formatter="dateFormatter">Last Updated Time</th>
			</tr>
		</thead>
	</table>	
	
	<div id="rank_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="saveRankBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRank()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('rank')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('rank')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('rank')">Delete</a>
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
				<td>
						<a id="savePointBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePoint()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('point')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('point')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('point')">Delete</a>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>