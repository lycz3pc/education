

	
$(document).ready(function(){

	// 默认关闭弹窗
	$('#dialog').dialog('close');
	
	// 默认关闭详情弹窗
	$('#getBanner').dialog('close');
	
	$('#authorDialog').dialog('close');
	
	$('#tagDialog').dialog('close');
	
	$('#themeDialog').dialog('close');
	
	
	// 查询右边内容发布的数据
	list();
	
	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
		$.ajax({
			type: "POST",
			url: '/banner/npBanner/findbannerObject',
			data: {"id": rowData.id},
			dataType : "json",
			success: function(data){
				$('#titles').filebox('setValue', data.npBanner.title);
				$('#urls').filebox('setValue', data.npBanner.url);
				$("#coverImgs").attr("src",data.npBanner.img);
			
			}
		});
		setDisable();
		$('#banner_form').dialog({
			title: '内容详情',
		    width: 500,
		    height: 350,
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
            
    }});
	
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
	
	$("#add").click(function(){//弹出框
		// 清空from表单
		$('#form').form('clear');
		// 清空预览显示
		$("#coverImg").attr('src', '/statics/images/addimg.png');
		$("#from").textbox({icons: []});
		$("#accessoryFile").empty();
		$('#url').textbox("setValue","http://");
		$('#dialog').dialog({
		    title: 'banner图发布',
		    width: 500,
		    height: 400,
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

	$("#coverUrl").filebox({
		onChange: function(){
			var coverUrlFile = document.getElementById('filebox_file_id_1');
			upload(coverUrlFile, "/upload/cover", 'coverName','coverPath','coverImg');
		}
	});
});

function setDisable(){
	$("#banner_form input").attr("disabled","disabled");
	$("#banner_form textarea").attr("disabled","disabled");
}

//列表图片列格式化
function formatImg(value, row, index){
	var str = "";
	if(value!=""){
		str = "<img src="+value+" style='width:100px;height:50px;'>";
	}
	return str;
	
}


// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/banner/npBanner/findbannerList',
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
        title: $("#title").textbox('getValue'),
        url: $("#url").textbox('getValue'),
        img: $("#coverPath").val(),
        source: 1
	};

	// 判断修改或者新增
	
	$("#id").textbox('getValue') == 0 ? 1 : date['id'] = $("#id").textbox('getValue');
	if (!$("#form").form('validate')) {
		return ;
	}
	
	$.ajax({
		type: "POST",
		url: '/banner/npBanner/saveOrEditBanner',
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
		url: '/banner/npBanner/findbannerObject',
		data: {"id": id},
		dataType : "json",
		success: function(data){
			$('#form').form('load', data.npBanner);
			$('#from').textbox('setValue', data.npBanner);
			$('#from').filebox('setValue', data.npBanner);
			$("#coverImg").attr("src",data.npBanner.img);
			$('#coverUrl').filebox('setValue', data.npBanner.img);
		
		}
	});

	
	$('#dialog').dialog({
		title: '内容修改',
	    width: 500,
	    height: 400,
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
				url: '/banner/npBanner/deleteBanner',
				contentType: "application/json",
				data: JSON.stringify({id: id}),
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

