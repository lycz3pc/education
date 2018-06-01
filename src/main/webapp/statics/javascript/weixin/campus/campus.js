
	
$(document).ready(function(){
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	// 默认关闭详情弹窗
	$('#getCampus').dialog('close');
	
	// 查询右边内容发布的数据
	list();
	
	//获取校区详情
	$("#datagrid").datagrid({
		onDblClickRow : function(index, row){
			$('#campus_form').form('clear');
			$("#qrcode_source_ids").attr('src', '/statics/images/addimg.png');
			$.ajax({
				type: "POST",
				url: '/npWxCampusT/findCampusObject',
				data: {"id": row.id},
				dataType : "json",
				success: function(data){
					$("#campusNames").textbox('setValue', data.npWxCampusT.campusName);
					$("#contactAddrs").textbox('setValue', data.npWxCampusT.contactAddr);
					$("#userNames").textbox('setValue', data.npWxCampusT.userName);
					$("#telPhones").textbox('setValue', data.npWxCampusT.telPhone);
					$("#registernums").textbox('setValue', data.npWxCampusT.registerNum);
					
					$("#qrcode_source_ids").attr('src', data.npWxCampusT.qrCodeResources.path);
					
					
				}
			});
			setDisable();
			$('#campus_form').dialog({
				    title: '校区详情',
				    width: 500,
				    height: 450,
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
			$('#campus_form').dialog('open');
			
			
		}
	});
	
	// 分页插件
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
	
	$("#search").click(function(){
		list();
	});
	
	$("#add").click(function(){
		// 清空from表单
		$('#form').form('clear');
		
		$("#from").textbox({icons: []});
		
		$('#dialog').dialog({
		    title: '新增校区',
		    width: 500,
		    height: 300,
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

function setDisable(){
	$("#campus_form input").attr("disabled","disabled");
	$("#campus_form textarea").attr("disabled","disabled");
}

// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/npWxCampusT/findCampusList',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	title: $.trim($("#searchTitle").textbox('getValue')),
        	page: options.pageNumber,	//页码
        	rows: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	var temp = data.page;
        	temp.rows = temp.records;
        	for ( var i in temp.rows) {
        		temp.rows[i].createDate= formatTime(temp.rows[i].createDate);
        		var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + temp.rows[i].id+ ')">修改</a>';
        		str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(' + temp.rows[i].id + ')">删除</a>';
        		temp.rows[i].operation = str;
			}
            $('#datagrid').datagrid('loadData', temp);
        },
        error: function() {
            alert('error');
        }
    });
}

function save(){
	var date = {
		id: $("#id").textbox('getValue'),
		campusName: $("#campusName").textbox('getValue'),
		contactAddr: $("#contactAddr").textbox('getValue'),
		userName: $("#userName").textbox('getValue'),
		telPhone: $("#telPhone").textbox('getValue')
	};
	// 判断修改或者新增
	$("#id").textbox('getValue') == 0 ? 1 : date['id'] = $("#id").textbox('getValue');
	if (!$("#form").form('validate')) {
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/npWxCampusT/saveCampus',
		contentType: "application/json",
		data: JSON.stringify(date),
		dataType: 'json',
		beforeSend: function (){
			uploadShade("数据提交中...");
		},
		complete: function() {
			disShade();
			load();
			disLoad();
		},
		success: function (data) {
			if (data.code == 0) {
				$('#from').textbox('setValue', null);
				$.messager.alert('提示','保存成功','info');
				$('#from').textbox('setValue', null);
				// reloadTree();
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示','保存失败','error');
			}
        }
	});
}

function edit(id){
	$('#form').form('clear');
	$.ajax({
		type: "POST",
		url: '/npWxCampusT/findCampusObject',
		data: {"id": id},
		dataType : "json",
		success: function(data){
			$('#form').form('load', data.npWxCampusT);
			$('#from').textbox('setValue', data.npWxCampusT);
		}
	});

	

	
	$('#dialog').dialog({
		title: '校区修改',
	    width: 500,
	    height: 300,
	    editable:false,
		resizable: false,
		draggable: false,
		onOpen: function(){
			load();
		},
		onClose: function() {
			$('#from').textbox('setValue', null);
			disLoad();
		}
	});
}

function del(id){
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			
			$.ajax({
				type: "POST",
				url: '/npWxCampusT/saveCampus',
				contentType: "application/json",
				data: JSON.stringify({id: id,isDelete:1}),
				dataType: 'json',
				beforeSend: function (){
					load();
					uploadShade("数据删除中...");
				},
				complete: function() {
					disShade();
					disLoad();
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
//列表图片列格式化
function formatImg(value, row, index){
	var str = "";
	if(value!=""){
		str = "<img src="+value+" style='width:25px;height:25px;'>";
	}
	return str;
	
}
