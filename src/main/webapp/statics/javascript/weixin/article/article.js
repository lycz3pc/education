// 实例化编辑器
var um;

	
$(document).ready(function(){
	// 富文本初始化
	um = UM.getEditor('rConten');
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	// 默认关闭详情弹窗
	$('#getArticle').dialog('close');
	
	$('#authorDialog').dialog('close');
	
	$('#tagDialog').dialog('close');
	
	$('#themeDialog').dialog('close');
	
	
	//限制简介的长度
	$('#detail').textbox('textbox').attr('maxlength',100);
	
	// 查询右边内容发布的数据
	list();
	
	//获取热文详情
	$("#datagrid").datagrid({
		onDblClickRow : function(index, row){
			$('#article_form').form('clear');
			$.ajax({
				type: "POST",
				url: '/article/npWxArticleT/findArticleObject',
				data: {"rid": row.rId},
				dataType : "json",
				success: function(data){
					$("#rTitles").textbox('setValue', data.npWxArticleT.rTitle);
					$("#sources").textbox('setValue', data.npWxArticleT.source);
					$("#browseNums").textbox('setValue', data.npWxArticleT.browseNum);
					$("#collectionNums").textbox('setValue', data.npWxArticleT.collectionNum);
					$("#details").textbox('setValue', data.npWxArticleT.detail);
					$("#coverImgs").attr('src', (data.npWxArticleT.coverResources || []).path || '/statics/images/addimg.png');
					UM.getEditor('rContens').setContent(data.npWxArticleT.rConten, false);
					UM.getEditor('rContens').setDisabled('fullscreen');
					
				}
			});
			setDisable();
			$('#getArticle').dialog({
				    title: '热文详情',
					width: 700,
				    height: 480,
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
			$('#getArticle').dialog('open');
			
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
		setAble(true,'rConten');
		// 清空from表单
		$('#form').form('clear');
		// 清空预览显示
		$("#coverImg").attr('src', '/statics/images/addimg.png');
		// 清空富文本
		um.setContent('', false);
		
		$("#from").textbox({icons: []});
		
		$("#accessoryFile").empty();
		
		$('#dialog').dialog({
		    title: '热文发布',
		    width: 700,
		    height: 480,
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
	$("#getArticle input").attr("disabled","disabled");
	$("#getArticle textarea").attr("disabled","disabled");
}


// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/article/npWxArticleT/findarticleList',
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
        		temp.rows[i].updateDate = temp.rows[i].updateDate>0?temp.rows[i].updateDate= formatTime(temp.rows[i].updateDate):null;
        			
        		var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + temp.rows[i].rId+ ')">修改</a>';
        		str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(' + temp.rows[i].rId + ')">删除</a>';
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
		rId: $("#rId").textbox('getValue'),
        rTitle: $("#rTitle").textbox('getValue'),
		browseNum: $("#browseNum").textbox('getValue'),
		collectionNum: $("#collectionNum").textbox('getValue'),
		source: $("#source").textbox('getValue'),
		detail:$("#detail").textbox('getValue'),
		rConten: um.getContent(),
		coverResources: {
			id: $("#coverId").val(),
			name: $("#coverName").val(),
			path: $("#coverPath").val()
		}
		
	};
	// 判断修改或者新增
	$("#rId").textbox('getValue') == 0 ? 1 : date['id'] = $("#rId").textbox('getValue');
	if (!$("#form").form('validate')) {
		return ;
	}
	if (!um.hasContents()) {
		um.focus();
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/article/npWxArticleT/saveOrEditArticle',
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

function edit(rid){
	$('#form').form('clear');
	$.ajax({
		type: "POST",
		url: '/article/npWxArticleT/findArticleObject',
		data: {"rid": rid},
		dataType : "json",
		success: function(data){
			$('#form').form('load', data.npWxArticleT);
			$('#from').textbox('setValue', data.npWxArticleT);
			
			$("#coverImg").attr('src', (data.npWxArticleT.coverResources || []).path || '/statics/images/addimg.png');
			um.setContent(data.npWxArticleT.rConten, false);
			$("#coverUrl").textbox('setValue', data.npWxArticleT.coverResources.name);
		}
	});

	

	
	$('#dialog').dialog({
		title: '内容修改',
	    width: 700,
	    height: 480,
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
				url: '/article/npWxArticleT/saveOrEditArticle',
				contentType: "application/json",
				data: JSON.stringify({rId: id,isDelete:1}),
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

