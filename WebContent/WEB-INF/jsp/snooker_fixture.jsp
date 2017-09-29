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
	cursor: frameer;
	font-size: 12px;
	color: white;
}

tr.changed-row {
	background-color: yellow;
}
</style>

<script type="text/javascript">
	var scoreEditIndex = undefined;
	var frameEditIndex = undefined;
	
	function endEditing(type) {
		var dg = null;
		var editIndex = undefined;
		if (type == "score") {
			dg = $("#score_dg")
			editIndex = scoreEditIndex;
		} else {
			dg = $("#frame_dg")
			editIndex = frameEditIndex;
		}
		if (editIndex == undefined) { return true; }
		if (dg.datagrid('validateRow', editIndex)) {
			dg.datagrid('endEdit', editIndex);
			if (type == "score") {
				scoreEditIndex = undefined;
			} else {
				frameEditIndex = undefined;
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
		if (id == "score_dg") {
			dg = $("#score_dg");
			type = "score";
			editIndex = scoreEditIndex;
			$("#saveScoreBtn").linkbutton("enable");
		} else {
			dg = $("#frame_dg");
			editIndex = frameEditIndex;
			type = "frame";
			$("#saveFrameBtn").linkbutton("enable");
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
			if (id == "score_dg") {
				scoreEditIndex = index;
			} else {
				frameEditIndex = index;
			}
		} else {
			setTimeout(function() {
				dg.datagrid('selectRow', editIndex);
			}, 0);
		}
	}

	function onLoadSuccess(data) {
		var id = $(this).attr("id");
		if (id == "score_dg") {
			$("#saveScoreBtn").linkbutton("disable");
			var score = $("#score_dg").datagrid("getSelected");
			if (!score || undefined == score) {
				$("#frame_dg").datagrid("loadData", {rows : []});
			}
		} else {
			$("#saveFrameBtn").linkbutton("disable");
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
		if (id == 'score_dg') {
			var frameData = row.ottSnookerFrameList;
			if (frameData) {
				$("#frame_dg").datagrid('loadData', frameData);	
			} else {
				$("#frame_dg").datagrid('loadData', {rows : []});
			}
			scoreEditIndex = index;
		} else {
			frameEditIndex = index;
		}
	}

	function doSearch() {
		var leagueNameForSearch = $('#leagueNameForSearch').val();
		leagueNameForSearch = leagueNameForSearch.Trim();
		$('#leagueNameForSearch').val(leagueNameForSearch);
		$('#score_dg').datagrid('load', {
			leagueNameForSearch : leagueNameForSearch
		});
	}

	function clearSearch() {
		$("#leagueNameForSearch").val("");
	}

	function saveScore() {
		if (endEditing('score')) {
			$("#score_dg").datagrid("endEdit", scoreEditIndex);
			if ($("#score_dg").datagrid("getChanges").length) {
				var inserted = $("#score_dg").datagrid('getChanges', 'inserted');
				var updated = $("#score_dg").datagrid('getChanges', 'updated');
				var deleted = $("#score_dg").datagrid('getChanges', 'deleted');
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
				$.post("${ctx}/snooker/fixture/saveScoreChanges.html", effectRow,
					function(response) {
						if (response.success) {
							$.messager.alert("", "Save changes successfully .", "info", function() {
								$("#score_dg").datagrid("reload");
								$("#saveScoreBtn").linkbutton("disable");
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
	
	function saveFrame() {
		$("#frame_dg").datagrid("endEdit", frameEditIndex);
		if ($("#frame_dg").datagrid("getChanges").length) {
			var inserted = $("#frame_dg").datagrid('getChanges', 'inserted');
			var updated = $("#frame_dg").datagrid('getChanges', 'updated');
			var deleted = $("#frame_dg").datagrid('getChanges', 'deleted');
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
			var score = $("#score_dg").datagrid("getSelected");
			effectRow['score'] = JSON.stringify(score);
			$.post("${ctx}/snooker/score/saveFrameChanges.html", effectRow,
				function(response) {
					if (response.success) {
						$.messager.alert("", "Save changes successfully .", "info", function() {
							$("#frame_dg").datagrid("reload");
							$("#saveFrameBtn").linkbutton("disable");
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
		if (type == "score") {
			$("#score_dg").datagrid("rejectChanges");
			$("#saveScoreBtn").linkbutton("disable");
			$("#frame_dg").datagrid("loadData", {rows : []});
		} else {
			$("#frame_dg").datagrid("rejectChanges");
			$("#saveFrameBtn").linkbutton("disable");
		}
	}

	function addRow(type) {
		var dg = null;
		if (type == "score") {
			dg = $("#score_dg");
		} else {
			dg = $("#frame_dg");
			var score = $("#score_dg").datagrid("getSelected");
			if (null == score || undefined == score || undefined == score.scoreId || "" == score.scoreId) {
				$.messager.alert("", "Please select one score above.", "info");
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
			if (type == "score") {
				scoreEditIndex = editIndex;
				$("#frame_dg").datagrid("loadData", {rows : []});
			} else {
				frameEditIndex = editIndex;
			}
			toggleSaveBtn(type);
		}
	}
	
	function deleteRow(type) {
		var dg = null;
		if (type == "score") {
			dg = $("#score_dg");
		} else {
			dg = $("#frame_dg");
		}
		var editIndex = undefined;
		if (type == "score") {
			editIndex = scoreEditIndex;
		} else {
			editIndex = frameEditIndex;
		}
		if (editIndex == undefined) {
			return
		}
		dg.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		if (type == "score") {
			scoreEditIndex = undefined;
			$("#frame_dg").datagrid("loadData", {rows : []});
		} else {
			frameEditIndex = undefined;
		}
		toggleSaveBtn(type);
	}
	
	function toggleSaveBtn(type) {
		var dg = null;
		if (type == "score") {
			dg = $("#score_dg");
			if (dg.datagrid("getChanges").length) {
				$("#saveRankBtn").linkbutton("enable");
			} else {
				$("#saveRankBtn").linkbutton("disable");
			}
		} else {
			dg = $("#frame_dg");
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
	<table id="score_dg" class="easyui-datagrid" data-options="
		title: 'Snooker Fixture(double click to edit)',
		singleSelect: true,
		toolbar: '#fixture_toolbar',
		pagination: true,
		pageSize: 10,
		url: '${ctx}/snooker/fixture/listFixture.html',
		singleSelect: true,
		onClickRow: onClickRow,
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess
	">
		<thead>
			<tr>
				<th field="scoreId"  hidden="true" width="150px">Score ID</th>
				<th field="matchId" editor="numberbox" width="150px">Match ID</th>
				<th field="seasonId" editor="numberbox" width="150px">Season ID</th>
				<th field="matchTime" hidden="true" width="150px">Match Time</th>
				<th field="matchTimeStr" editor="datetimebox" width="150px">Match Time</th>
				<th field="leagueId" editor="numberbox" width="150px">League ID</th>
				<th field="leagueNameCn" editor="text" width="150px">League Name CN</th>
				<th field="leagueNameEn" editor="text" width="150px">League Name EN</th>
				<th field="leagueNameTr" editor="text" width="150px">League Name TR</th>
				<th field="leagueType" editor="text" width="150px">League Type</th>
				<th field="matchLevel1" editor="text" width="150px">Match Level 1</th>
				<th field="matchLevel2" editor="text" width="150px">Match Level 2</th>
				<th field="playerAId" editor="numberbox" width="150px">Player A ID</th>
				<th field="playerBId" editor="numberbox" width="150px">Player B ID</th>
				<th field="playerANameCn" editor="text" width="150px">Player A Name CN</th>
				<th field="playerANameEn" editor="text" width="150px">Player A Name EN</th>
				<th field="playerANameTr" editor="text" width="150px">Player A Name TR</th>
				<th field="playerANameCn" editor="text" width="150px">Player B Name CN</th>
				<th field="playerANameEn" editor="text" width="150px">Player B Name EN</th>
				<th field="playerANameTr" editor="text" width="150px">Player B Name TR</th>
				<th field="playerAWinNum" editor="numberbox" width="150px">Player A Win Num</th>
				<th field="playerBWinNum" editor="numberbox" width="150px">Player B Win Num</th>
				<th field="maxField" editor="numberbox" width="150px">Max Field</th>
				<th field="currentField" editor="numberbox" width="150px">Current Field</th>
				<th field="winnerId" editor="numberbox" width="150px">Winner ID</th>
				<th field="winReason" editor="text" width="150px">Win Reason</th>
				<th field="quiterId" editor="numberbox" width="150px">Quiter ID</th>
				<th field="quitReason" editor="text" width="150px">Quit Reason</th>
				<th field="bestPlayer" editor="numberbox" width="150px">Best Player</th>
				<th field="bestScore" editor="numberbox" width="150px">Best Score</th>
				<th field="status" editor="text" width="150px">Status</th>
				<th field="currentPlayer" editor="numberbox" width="150px">Current Player</th>
				<th field="currentScore" editor="numberbox" width="150px">Current Score</th>
				<th field="sort" editor="numberbox" width="150px">Sort</th>
				<th field="lastPublishedDate" formatter="dateFormatter" width="150px">Last Published Date</th>
			</tr>
		</thead>
	</table>
	<br />
	<table id="frame_dg" class="easyui-datagrid" data-options="
		title: 'Snooker Frame(double click to edit)',
		toolbar: '#frame_toolbar',
		striped: true,
		singleSelect: true,
		idField: 'frameId',
		fitColumns: true,
		onDblClickCell: onDblClickCell,
		onLoadSuccess: onLoadSuccess,
		onClickRow:onClickRow
	">
		<thead>
			<tr>
				<th field="frameId" hidden="true" >Frame ID</th>
				<th field="matchSort" data-options="editor:'numberbox'" >Match Sort</th>
				<th field="sort" data-options="editor:'numberbox'" >Sort</th>
				<th field="scoreA" data-options="editor:'numberbox'" >Score A</th>
				<th field="scoreB" data-options="editor:'numberbox'" >Score B</th>
				<th field="bestPlayer" data-options="editor:'numberbox'" >Best Player</th>
				<th field="cscoreA" data-options="editor:'numberbox'" >Cscore A</th>
				<th field="cscoreB" data-options="editor:'numberbox'" >Cscore B</th>
				<th field="lastPublishedDate" formatter="dateFormatter">Last Published Date</th>
			</tr>
		</thead>
	</table>	
	
	<div id="fixture_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="saveBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveScore()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('score')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('score')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('score')">Delete</a>
				</td>
				<td align="right">
					<span style="padding-left:20px">League Name: <input type="text" id="leagueNameForSearch" name="leagueNameForSearch" maxlength="50"/></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearch()" style="margin-left: 5px;">Reset</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="frame_toolbar" style="padding:5px;height:auto;margin-bottom:5px">
		<table width="100%">
			<tr>
				<td>
						<a id="saveFrameBtn" href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveFrame()" disabled="true">Save</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="reset('frame')">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRow('frame')">Add</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRow('frame')">Delete</a>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>
