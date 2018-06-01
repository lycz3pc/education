$(function(){  //初始化

	$('#answer').dialog('close')// 默认关闭弹窗
	$('#answer_detail').dialog('close')// 默认关闭弹窗
	// //选中一行查看详情
	$("#dg").datagrid({
		onDblClickRow : function(index, row){
			// 富文本初始化
			um = UM.getEditor('plcontent');
			$("#attentions").val(row.praise);
			$("#comments").val(row.comments);
			UM.getEditor('plcontent').setContent(row.content, false);
			UM.getEditor('plcontent').setDisabled('fullscreen');
			$("#createusers").val(row.userName);
			$('#answer_detail').dialog({
				title: '详情'
			});
			$("#btnApp").hide();
			$('#answer_detail').dialog('open');
		}
	});

	init();
});


function init() {
	//表格
	$("#dg").datagrid({
		rownumbers: true,
		title: '评论列表',
		singleSelect: true,
		fitColumns: true,
		pagination: true,
		url: '/platform/Interactive/answerindex',
		method: 'post',
		toolbar: '#tb',
		async: false,
		queryParams: {
			id: getUrlParam("id"),
			content: $("#content").val()
		},
		columns: [[
			{field: 'userName', title: '评论人', width: '10%'},
			{field: 'praise', title: '顶量', width: '8%'},
			{field: 'comments', title: '评论量', width: '7%'},
			{field: 'content', title: '内容', width: '30%', formatter:synopsis},
			{field: 'createTime', title: '发布时间', width: '15%'},
			{field: 'status', title: '审核状态', width: '10%', formatter:formatStatus},
			{field: 'but', title: '操作', width: '20%', align: 'center',formatter:formatOpt}
		]],
		onLoadSuccess: function (data) {

			$("#name").val(getUrlParam("name"));
			$("a[name='delete']").linkbutton();
			$("a[name='update']").linkbutton();
		}
	});
}

function synopsis(value, row, index) {
	var text = "";
	if(value !=''){
		if(value.length > 20){
			text =  value.substring(0,20)+'...';
		}else{
			text = value;
		}
	}
	return text;
}

//格式化操作列
function formatOpt(value, row, index){
	var str = '';
	if(row.status == 0){
		str += '<a href="javascript:void(0)" class="this_but_red" name="update"  onclick="update('+index+')">审核</a>&nbsp;&nbsp;';
	}
	str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_black"  name="delete"  onclick="del(this.id)">删除</a>';
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
		url:'/platform/Interactive/answerAuth',
		dataType:'json',
		data:{
			id:row.id,
			status:status
		},
		success:function(res){
			
			if (res.type == "success") {
				$.messager.alert('提示','操作成功!', 'info');
				$("#dg").datagrid("reload");
				$('#answer_detail').dialog('close');
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
	
	// 富文本初始化
	um = UM.getEditor('plcontent');
	$("#attentions").val(row.praise);
	$("#comments").val(row.comments);
	UM.getEditor('plcontent').setContent(row.content, false);
	UM.getEditor('plcontent').setDisabled('fullscreen');
	$("#createusers").val(row.userName);
	$('#answer_detail').dialog({
		title: '审核'
	});
	$("#btnApp").show();
	$('#answer_detail').dialog('open');
}




//查询
function query(){
	$("#dg").datagrid("load",{
		content:$("#content").val(),
		id: getUrlParam("id"),
	});

}

function del(id) {
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			$.ajax({
				url: "/platform/Interactive/answedelete",
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
						$.messager.alert('提示',data.msg,'info');
					}
				}
			});
		}
	});
}

//获取js传递的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return decodeURI(r[2]);
	return null; //返回参数值
}