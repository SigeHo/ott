/**
 * Methods
 */
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
            var ed = $(this).datagrid('getEditor', param);
            if (ed){
                if ($(ed.target).hasClass('textbox-f')){
                    $(ed.target).textbox('textbox').focus();
                } else {
                    $(ed.target).focus();
                }
            }
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	},
    enableCellEditing: function(jq){
        return jq.each(function(){
            var dg = $(this);
            var opts = dg.datagrid('options');
            opts.oldOnClickCell = opts.onClickCell;
            opts.onClickCell = function(index, field){
                if (opts.editIndex != undefined){
                    if (dg.datagrid('validateRow', opts.editIndex)){
                        dg.datagrid('endEdit', opts.editIndex);
                        opts.editIndex = undefined;
                    } else {
                        return;
                    }
                }
                dg.datagrid('selectRow', index).datagrid('editCell', {
                    index: index,
                    field: field
                });
                opts.editIndex = index;
                opts.oldOnClickCell.call(this, index, field);
            }
        });
    }
});

/**
 * Rules
 */
$.extend($.fn.validatebox.defaults.rules, {
	equalTo: {
        validator: function(value, param){
        	return $(param[0]).val() == value;
        },
        message: 'Dose not match.'
    }
});

/**
 * Editors
 */
$.extend($.fn.datagrid.defaults.editors,{  
    textarea: {  
        init: function(container, options){  
            var input = $("<textarea class='datagrid-editable-input' style='resize:none;' rows="+options.rows + "></textarea>").validatebox(options).appendTo(container);  
                return input;  
          },  
        getValue: function(target){  
            return $(target).val();  
        },  
        setValue: function(target, value){  
            $(target).val(value);  
        },  
        resize: function(target, width){  
            var input = $(target);  
            if($.boxModel == true){  
               input.width(width - (input.outerWidth() - input.width()));  
            }else{  
               input.width(width);  
            }  
        }  
    }
});
