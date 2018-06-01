$(document).ready(function(){
	
	list();
	// 分页插件
	
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
	
	$('#dialog').dialog('close');
	$('#merchandisesDialog').dialog('close');
});

// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/weixin/purchase/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	current: options.pageNumber,	//页码
        	size: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	console.debug(data);
        	var temp = data.page;
        	temp.rows = temp.records;
        	for ( var i in temp.rows) {
        		var str = '';
        		if (temp.rows[i].shipStatus != 1)
        			str = '<a class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + i + ', 1)">发货</a>';
        		str += '<a class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="detail(\'' + temp.rows[i].id + '\')">订单详情</a>';
        		temp.rows[i].createDate = formatStrDate(temp.rows[i].createDate);
        		temp.rows[i].operation = str;
        		temp.rows[i].shipStatus = temp.rows[i].shipStatus==1?"已发货":"未发货";
			}
            $('#datagrid').datagrid('loadData', temp);
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
	// 判断修改或者新增
	var date = {waybillNo: $("#waybill").textbox('getValue')};
	if ($("#id").textbox('getValue') != 0) {
		date['id'] = $("#id").textbox('getValue');
	}
	$.ajax({
		type: "POST",
		url: '/weixin/purchase/delivery?_' + $.now(),
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
	
	$('#dialog').dialog({
	    title: '发货',
	    width: 250,
	    height: 160,
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


function detail(id){
	$.ajax({
		type: "POST",
		url: '/weixin/purchase/detail?_' + $.now(),
		contentType: "application/json",
		data: JSON.stringify({id: id}),
		dataType: 'json',
		success: function (data) {
			if (data.code == 0) {
				$('#merchandisesDatagrid').datagrid('loadData', data.merchandises);
				$('#merchandisesDialog').dialog({
				    title: '订单详情',
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
        }
	});
}