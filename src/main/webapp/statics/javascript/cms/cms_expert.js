$(document).ready(function(){
	
	list();
	// 分页插件
	
	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
    	setDisable(false);
    	edit(rowIndex, 0);
    }});
	
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
	
	$('#dialog').dialog('close');
	
	$("#add").click(function (){
		setAble(false);
		$('#form').form('clear');
		$('#dialog').dialog({
		    title: '新增专家',
		    width: 400,
		    height: 250,
    		editable:false,
    		resizable: false,
    		draggable: false,
    		onOpen: function(){
    			load();
    		},
    		onClose: function() {
    			disLoad();
    		}
		});
	});
});

// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/platform/cmsExpert/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	id: 0,
        	current: options.pageNumber,	//页码
        	size: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	var temp = data.page;
        	temp.rows = temp.records;
        	for ( var i in temp.rows) {
        		var str = '<a class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + i + ', 1)">修改</a>';
        		str += '<a class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(\'' + temp.rows[i].id + '\')">删除</a>';
				temp.rows[i].operation = str;
			}
            $('#datagrid').datagrid('loadData', temp);
            $("#theme").empty();
            for (var i = 0; i < data.themes.length; i++) {
            	var theme = data.themes[i];
            	$("#theme").append('<input value="' + theme.constantId + '" type="checkbox" name="specialized" /><span>' + theme.constantValue + '</span>');
            	if (i % 3 == 2 && (i < data.themes.length - 1)) {
            		$("#theme").append('<br>');
				}
			}
        },
        error: function() {
            alert('error');
        }
    });
}

function save(){
	if (!$("#form").form('validate')) {
		return ;
	}
	var specialized = [];
	$('input[name="specialized"]:checked').each(function(){
		specialized.push($(this).val());
	});
	if (specialized.length > 5 || specialized.length == 0) {
		alert('专家相关主题为必选项，且最多选择5项');
		return ;
	}
	// 判断修改或者新增
	var date = {
		name: $("#name").textbox('getValue'),
		tag: specialized.join(",")};
	if ($("#id").textbox('getValue') != 0) {
		date['id'] = $("#id").textbox('getValue');
	}
	$.ajax({
		type: "POST",
		url: '/platform/cmsExpert/save?_' + $.now(),
		contentType: "application/json",
		data: JSON.stringify(date),
		dataType: 'json',
		beforeSend: function (){
			uploadShade("数据提交中...");
		},
		complete: function() {
			disShade();
		},
		success: function (data) {
			if (data.code == 0) {
				$.messager.alert('提示','保存成功','info');
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示','保存失败','error');
			}
        }
	});
}

function edit(index, option){
	option == 1 ? setAble(false) : 1;
	var rows = $('#datagrid').datagrid('getRows');
	var temp = rows[index];
	$('#form').form('clear');
	$('#form').form('load', temp);
	
	var check = $("input[name='specialized']");
	for(var i = 0;i < check.length;i++){
		if($.inArray(check[i].value, temp.tag.split(",")) > -1){
			check[i].checked=true;
		}
	}
	
	$('#dialog').dialog({
	    title: option == 1 ? '修改专家信息' : '查看',
	    width: 400,
	    height: 250,
	    editable:false,
		resizable: false,
		draggable: false,
		onOpen: function(){
			load();
		},
		onClose: function() {
			disLoad();
		}
	});
}


function del(id){
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: '/platform/cmsExpert/delete?_' + $.now(),
				contentType: "application/json",
				data: JSON.stringify({id: id}),
				dataType: 'json',
				beforeSend: function (){
					load();
					uploadShade("数据删除中...");
				},
				complete: function() {
					disLoad();
					disShade();
				},
				success: function (data) {
					if (data.code == 0) {
						$.messager.alert('提示','删除成功','info');
						list();
					} else {
						$.messager.alert('提示','删除失败','error');
					}
	            }
			});
		}
	});
}

$.extend($.fn.validatebox.defaults.rules, {
	expertName: {
		validator: function(value, param){
			var flag = false;
			$.ajax({
				method: 'POST',
				url: '/platform/cmsExpert/check',
				contentType: "application/json",
		        dataType: 'json',
		        async: false,
		        data: JSON.stringify({id: param[0] == '' ? 0 : param[0], name: value}),
		        success: function(data){
		        	flag = data;
		        }
			});
			return flag;
		},
		message: '专家名称已存在'
    }
});