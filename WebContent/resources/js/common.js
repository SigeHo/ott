/*
 * Trim space
 */
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.LTrim = function() {
	return this.replace(/(^\s*)/g, "");
}

String.prototype.RTrim = function() {
	return this.replace(/(\s*$)/g, "");
}


/**
 * Format date
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}


/**
 * Check if date format is yyyy-mm-dd or yyyy-m-d
 */
function isDate(dateString) {
	if (dateString.Trim() == "") {
		return false;
	}
	var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
	
	return (num != 0);
}

/**
 * Check if date format is yyyy-mm-dd or yyyy-m-d ignore space
 */
function isDateIgnoreSpace(dateString) {
	if (dateString.Trim() == "") {
		return true;
	}
	var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
	
	return (num != 0);
}

/**
 * Check if the email is valid
 */
function isValidEmail(email){
	if(email == '') {
		return false;
	}
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(myreg.test(email)) {
		return true;
	}
	return false;
}

function showLoadingMsg() {
	$("<div id=\"loadingBox_mask\" class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo($("body"));
	$("<div id=\"loadingBox_mask_msg\" class=\"datagrid-mask-msg\"></div>").html("Processing, please wait...").appendTo($("body")).css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

function hideLoadingMsg() {
	$("body").find("#loadingBox_mask").remove();
	$("body").find("#loadingBox_mask_msg").remove();
}

/* Contants */
var USER_NAME_MAX = 50;
var USER_DESC_MAX = 100;
var ROLE_NAME_MAX = 50;
var ROLE_DESC_MAX = 100;
var PERMISSION_NAME_MAX = 110;
var PERMISSION_DESC_MAX = 200;
var PERMISSION_URL_MAX = 200;

/*
 * Get string's length  
 */
function getStringLength(str) {
	var totalLength = 0;
	var list = str.split("");
	for(var i = 0; i < list.length; i++) {
		var s = list[i];
		if (s.match(/[\u0000-\u00ff]/g)) { //鍗婅
		    totalLength += 1; 
		} else if (s.match(/[\u4e00-\u9fa5]/g)) { //涓枃  
		    totalLength += 3; 
		} else if (s.match(/[\uff00-\uffff]/g)) { //鍏ㄨ 
		    totalLength +=3;
		}
	}   
	return totalLength;
}//澶囨敞锛�涓枃鐨勭紪鐮佷负UTF-8,鍗婅鍗犱竴涓瓧鑺傦紝涓枃鍙婂叏瑙掑崰鐢ㄤ笁涓瓧鑺�
	

/*
 * Check string's length, show alert message if too large and return false, otherwise return true
 */
function checkLength(str, maxLength, inputName) {
	if(getStringLength(str) > maxLength) {
		$.messager.alert('',inputName +'\'s length is too large.'); 
		return false;
	} else {
		return true;
	}
}


function dateBoxFormatter(date){ 
	var fullYear = date.getFullYear();
	var month = date.getMonth()+1;
	var date = date.getDate();
	if(month<10) {
		month = '0' + month;
	}
	if(date<10) {
		date = '0' + date;
	}		
	return fullYear + '-' + month + '-' + date;
}

function dateBoxParser(date){
	if (date && !isNaN(Date.parse(date))){
		return new Date(Date.parse(date.replace(/-/g,"/")));
	} else {
		return new Date();
	}
}  