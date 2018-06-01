 var us_kerdown=function (){
	 query();
	};
$(function(){  //初始化
	um = UM.getEditor('describe');
	$('#questions').dialog('close')// 默认关闭弹窗
	$('#questions_detail').dialog('close')// 默认关闭弹窗
	// //选中一行查看详情
	$("#dg").datagrid({
		onDblClickRow : function(index, row){
			$("#plname").val(row.name);
			$("#tags").val(row.tag);
			$("#attentions").val(row.attention);
			$("#browses").val(row.browse);
			um.setContent(row.describ, false);
			um.setDisabled('fullscreen');
			$("#createusers").val(row.userName);

			$('#questions_detail').dialog({
				title: '详情'
			});
			$("#btnApp").hide();
			$('#questions_detail').dialog('open');
		}
	});
	init();
});

function init() {
	//表格
	$("#dg").datagrid({
		rownumbers: true,
		title: '问答列表',
		singleSelect: true,
		fitColumns: true,
		pagination: true,
		url: '/platform/Interactive/index',
		method: 'post',
		toolbar: '#tb',
		async: false,
		queryParams: {
			name: $("#name").val()
		},
		columns: [[
			{field: 'name', title: '标题', width: '22%',formatter:synopsis},
			{field: 'tag', title: '标签', width: '10%'},
			{field: 'attention', title: '关注数', width: '7%'},
			{field: 'browse', title: '浏览数', width: '7%'},
			{field: 'userName', title: '发布人', width: '8%'},
			{field: 'createDate', title: '发布时间', width: '13%'},
			{field: 'status', title: '审核状态', width: '10%', formatter:formatStatus},
			{field: 'opt', title: '操作', width: '23%', align: 'center',formatter:formatOpt}
		]],
		onLoadSuccess: function (data) {
			$("a[name='update']").linkbutton();
			$("a[name='delete']").linkbutton();
		}
	});
}

 function synopsis(value, row, index) {
	 if(value !=''){
		 return  value.substring(0,10)+'...';
	 }
 }

//查询
function query(){
	$("#dg").datagrid("load",{
		name:$("#names").val()
	});

}

//格式化操作列
function formatOpt(value, row, index){
	var str = '';
	if(row.status==0){
		str += '<a href="javascript:void(0)" class="this_but_red" name="update"  onclick="update('+index+')">审核</a>&nbsp;&nbsp;';
	}
	str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_black" name="delete"  onclick="del(this.id)">删除</a>&nbsp;&nbsp;';
	str += '<a href="/admin/Interactive/answerindex.html?id='+row.id+'&name='+row.name+'" class="this_but_yellow"  name="delete" >查看评论</a>';
	return str;
}
//格式化审核状态
function formatStatus(value, row, index){
	var str = '未审核';
	if(row.status==1){
		str = "通过";
	}else if(row.status==2){
		str = "不通过";
	}
	return str;
}

//审核提交
function submitForm(status){
	var row = $("#dg").datagrid("getSelected");
	
	$.ajax({
		type:'post',
		url:'/platform/Interactive/questionAuth',
		dataType:'json',
		data:{
			id:row.id,
			status:status
		},
		success:function(res){
			
			if (res.type == "success") {
				$.messager.alert('提示','操作成功!', 'info');
				$("#dg").datagrid("reload");
				$('#questions_detail').dialog('close');
			} else {
				$.messager.alert('提示', data.msg, 'info');
			}
		}
	});
}

//审核
function update(index){
	$("#dg").datagrid("selectRow", index);
	var row = $("#dg").datagrid("getSelected");
	
	$("#plname").val(row.name);
	$("#tags").val(row.tag);
	$("#attentions").val(row.attention);
	$("#browses").val(row.browse);
	um.setContent(row.describ, false);
	um.setDisabled('fullscreen');
	$("#createusers").val(row.userName);

	$('#questions_detail').dialog({
		title: '审核'
	});
	
	$("#btnApp").show();
	$('#questions_detail').dialog('open');
}

function del(id) {
	var b = checkdel(id);//验证是否有子评论
	if(b == true){
		$.messager.confirm('确认', '确认删除？', function(r){
			if (r) {
				$.ajax({
					url: "/platform/Interactive/delete",
					type: "POST",
					async: false,
					dataType: "json",
					data: {
						"id": id
					}, success: function (data) {
						if (data.type == "success") {
							$.messager.alert('提示','删除成功!', 'info');
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert('提示', data.msg, 'info');
						}
					}
				});
			}
		});
	}else {
		$.messager.confirm('确认', '确认删除？', function(r){
			if (r){
				$.ajax({
					url: "/platform/Interactive/delete",
					type: "POST",
					async: false,
					dataType: "json",
					data: {
						"id": id
					}, success: function (data) {
						if (data.type == "success") {
							$("#dg").datagrid("reload");
							$.messager.alert('提示','删除成功','info');
							layer.closeAll();
						} else {
							$.messager.alert('提示',data.msg,'info');
						}
					}
				});
			}
		});
	}
}


function checkdel(id) {
	var bool = '';
	$.ajax({
		url: "/platform/Interactive/checkdel",
		type: "POST",
		async: false,
		dataType: "json",
		data: {
			"id": id
		}, success: function (data) {
			bool = data.bools;
		}
	});
	return bool;
}