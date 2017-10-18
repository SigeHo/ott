$.extend($.fn.validatebox.defaults.rules, {
	nonCHS:{
		validator:function(value,param){
			if (value){
				return /^[a-zA-Z\d\s\`\-\=\[\]\\\;\'\,\.\/\~\!\@\#\$\%\^\&\*\(\)\_\+\{\}\|\:\"\<\>\?\*]*$/.test(value);
			} else {
				return true;
			}
		},
		message:'Alpha & Numeric only.'
	},
	alpha:{
		validator:function(value,param){
			if (value){
				return /^[a-zA-Z\s]*$/.test(value) && value.length >= param[0] && value.length <= param[1];
			} else {
				return true;
			}
		},
		message:'Alpha only. {0} to {1} characters.'
	},
	alphanum:{
		validator:function(value,param){
			if (value){
				return /^([a-zA-Z0-9\s])*$/.test(value) && value.length >= param[0] && value.length <= param[1];
			} else {
				return true;
			}
		},
		message:'Alpha & Numeric only. {0} to {1} characters.'
	},
	username:{
		validator:function(value,param){
			if(value){
				return /^([a-zA-Z0-9])*$/.test(value) && value.length >= param[0] && value.length <= param[1];
			}else{
				return true;
			}
		},
		message:'Alpha & Numeric only. {0} to {1} characters.'
	},
	positive_int:{
		validator:function(value,param){
			if (value){
				return /^[0-9]*[1-9][0-9]*$/.test(value);
			} else {
				return true;
			}
		},
		message:'Postive integer only.'
	},
	numeric:{
		validator:function(value,param){
			if (value){
				return /^[0-9]*(\.[0-9]+)?$/.test(value);
			} else {
				return true;
			}
		},
		message:'只能输入数字.'
	},
	appId:{
		validator:function(value,param){
			if (value){
				return /^([0-9])*$/.test(value) && value.length >= param[0] && value.length <= param[1];
			} else {
				return true;
			}
		},
		message:'Numeric only. {0} to {1} characters.'
	},
	chinese:{
		validator:function(value,param){
		if (value){
			 return /[^\u4E00-\u9FA5]/g.test(value);
		} else {
			return true;
		}
	},
	message:'只能输入中文'
	},
	channelNos:{
		validator:function(value,param){
			if (value){
//				return /^([0-9])+([0-9\,])*([0-9])+$/.test(value) ;
				return /^(([0-9])+(\,)?)*([0-9]+)$/.test(value) ;
			} else {
				return true;
			}
		},
		message:'You must input in the way like "101,102,103,104,105"(numberic only, separated by ",").'		
	},
	pccwEmail:{
		validator:function(value,param){
			if (value){
				return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@pccw.com$/i.test(value);
			} else {
				return true;
			}
		},
		message:'Please enter a valid email address of pccw.com.'		
	}
		
});
/*
*@param elemId 元素的id
*删除easyui已有的验证
*/
function delRules(elemId){
	var v = $("#"+elemId)[0] ;
	if($.data(v,'validatebox')){
		$.data(v,'validatebox').options={};
		$.data(v,'validatebox').tip=null;
		$("#"+elemId).removeClass('validatebox-invalid');
	}
}
