var us_kerdown=function (){
	list();
};
// 修改easyUI默认验证提示
$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
// easyUI拓展验证
/*$.extend($.fn.validatebox.defaults.rules, {
	checkTextbox: {
		validator: function(value, param){
			var flag = false;
			$.ajax({
				method: 'POST',
				url: '/admin/demo/check',
				contentType: "application/json",
				dataType: 'json',
				async: false,
				data: JSON.stringify({id: param[0], textbox: value}),
				success: function(data){
					flag = data;
				}
			});
			return flag;
		},
		message: 'Email已存在'
	}
});*/
//验证是否为空
$.extend($.fn.validatebox.defaults.rules, {
	name: {
		validator: function(value, param){
			return $.trim(value)!='';
		},
		message: '不能为空'
	}
});
//验证电话号码
$.extend($.fn.validatebox.defaults.rules, {
	phone: {
		validator: function(value, param){
			
			if (!(/^1[34578]\d{9}$/.test(value))) {
				return false;
			}
			
			return true;
		},
		message: '手机号码不正确'
	}
});
//验证密码前后一致
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
		validator: function(value,param){
			return value == $(param[0]).val();
		},
		message: '两次密码输入不一致'
    }     
});

// ztree设置
var setting = {
	check: {
		enable:true
	},
	callback: {
		// onClick:this.zTreeOnClick
	},
	data: {
		keep: {
			leaf:false,
			parent:true
		},
		key: {
			name:"text",
			url:'',
		},
		simpleData: {
			enable:true,
			idKey:"id",
			pIdKey:"parentId"
		}
	},
	view: {
		showLine:true
	},
};


$(document).ready(function(){
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	// 分页插件
	$('#datagrid').datagrid('getPager').pagination({
		pageSize: 10,
		pageNumber: 1,
		pageList: [10, 20, 30, 40, 50],
		beforePageText: '第',
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onSelectPage: function(pageNumber, pageSize){
			list();
		}
	});
	// 查询按钮点击事件
	$("#search").click(function(){
		list();
	});

		// 新增弹出层
		$("#add").click(function(){
			$("#update").hide();
			$("#save").show();
			$('#form').form('clear');// 清空from表单
			$("#status0").click();
		$('#dialog').dialog({
			title: '新增'
		});
		$('#dialog').dialog('open');
	});

	list();
	//获取所有角色
	getRoles(null);

});


// 查询列表数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
		type: 'POST',
		url: '/admin/sys/admin/list?_' + $.now(),
		dataType: 'json',
		data: {
			name: $("#names").textbox('getValue'),
			current: options.pageNumber,	//页码
			size: options.pageSize	//每页显示行
		},
		success: function(data) {
			var temp = data.page;
			temp.rows = temp.records;
			// 操作
			for (var i in temp.rows) {
				var str = '<a   class="this_but_red"  onclick="upd('+temp.rows[i].id+')">修改</a>';
				str += '<a  class="this_but_black" onclick="del(' + temp.rows[i].id + ')">删除</a>';
				temp.rows[i].operation = str;
			}
			$('#datagrid').datagrid('loadData', temp);
		},
		error: function() {
			alert('error');
		}
	});
}

//保存橘角色
function save(){
	/*var password = $("#password").textbox('getValue');
	var newpassword = $("#newpassword").textbox('getValue');
	if(password.replace(/(^\s*)|(\s*$)/g, "") == ''|| newpassword.replace(/(^\s*)|(\s*$)/g, "") ==''){
		$.messager.alert('提示','密码不能为空','info');
		return;
	}
	if(password.replace(/(^\s*)|(\s*$)/g, "")!=newpassword.replace(/(^\s*)|(\s*$)/g, "") ){
		$.messager.alert('提示','密码不一致','info');
		return;
	}*/

	if (!$("#form").form('validate')) {
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/admin/sys/admin/save',
		data: $("#form").serialize(),
		dataType : "json",
		success: function (data) {
			if (data.type == "success") {
				list();
				$('#dialog').dialog('close');
				$.messager.alert('提示',"新增成功！",'info');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});
}

//修改用户
function update(){
	/*var password = $("#password").textbox('getValue');
	var newpassword = $("#newpassword").textbox('getValue');
	if(password.replace(/(^\s*)|(\s*$)/g, "") == ''|| newpassword.replace(/(^\s*)|(\s*$)/g, "") ==''){
		if(password.replace(/(^\s*)|(\s*$)/g, "")!=newpassword.replace(/(^\s*)|(\s*$)/g, "") ){
			$.messager.alert('提示','密码不一致','info');
			return;
		}
	}*/

	if (!$("#form").form('validate')) {
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/admin/sys/admin/update',
		data: $("#form").serialize(),
		dataType : "json",
		success: function (data) {
			if (data.type == "success") {
				list();
				$('#dialog').dialog('close');
				$.messager.alert('提示',"修改成功！",'info');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});
}

//删除
function del(id){
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: '/admin/sys/admin/delete',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					if (data.type == "success") {
						$.messager.alert('提示','删除成功','info');
						list();
						$('#dialog').dialog('close');
					} else {
						$.messager.alert('提示',data.msg,'info');
					}
				}
			});
		}
	});
}

// 修改弹出层
function upd(id) {
	$("#update").show();
	$("#save1").hide();
	$("#pspw").hide();
	
	$('#password').textbox({
		required:false
	}); 
	$('#newpassword').textbox({
		required:false
	});

	$('#form').form('clear'); // 清空from表单
	
	getAdmin(id);
	$('#dialog').dialog({
		title: '修改'
	});
	$('#dialog').dialog('open');
}
//新增弹出层
function addLayer() {
	$("#pspw").show();
	$('#password').textbox({
		required:true
	}); 
	$('#newpassword').textbox({
		required:true
	});
	$("#password").addClass("easyui-textbox");
	$("#newpassword").addClass("easyui-textbox");
	$("#update").hide();
	$("#save1").show();
	$('#form').form('clear'); // 清空from表单
	$('#username').textbox('textbox').attr('readonly',false);
	$('#dialog').dialog({
		title: '新增'
	});
	$('#dialog').dialog('open');
}

//获取当前角色原有权限
function getAdmin(id){
			$.ajax({
				async: false,
				type: "POST",
				url: '/admin/sys/admin/getAdmin',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					$('#name').textbox('setValue',data.admin.name);
					$('#username').textbox('setValue',data.admin.username);
					$('#phone').textbox('setValue',data.admin.phone);
					$('#email').textbox('setValue',data.admin.email);
					$('#id').textbox('setValue',data.admin.id);
					$('#username').textbox('textbox').attr('readonly',true);
					if(data.admin.status==0){
                         $("#status0").click();
					}
					if(data.admin.status==1){
						$("#status1").click();
					}

				}
			});

}



// 时间格式化
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y + '-' + m + '-' + d;
}

//弹出授予角色框
function assignDialog(){
	var ids = $("#datagrid").datagrid("getSelections");
	//未选中时提示
	if(ids.length == 0){
		$.messager.alert('提示','请至少选中一个管理员','info');
		return;
	}
	
	//清空角色选择
	$("input[name='roleNames']").each(function(){
		$(this).attr("checked", false);
	});
	//当选中管理员为1个时，查询之前的角色
	
	if(ids.length == 1){
		$("#showRoles").empty();
		getRoles(ids[0].id);
	}
	
	$("#grantRoles").dialog({
		title:'授予角色',
		modal:true,
		close:true
	}).dialog("open");
}
//保存授予角色
function assignRoles(){
	//授予的角色数组
	var roles = $("input[name='roleNames']:checked").map(function(){
		return $(this).val();
	}).get();
	if(roles.length == 0){
		$.messager.alert('提示','请至少选中一个角色','info');
		return;
	}
	
	//管理员id数组
	var ids = new Array();
	var admins = $("#datagrid").datagrid("getSelections");
	$.each(admins, function(index, item){
		ids.push(item.id);
	});
	
	//保存
	$.ajax({
		url:'/admin/sys/admin/assignRole?_' + $.now(),
		method:'post',
		dataType:'json',
		data:{
			ids:ids.join(),
			roleNames: roles.join()
		},
		success:function(res){
			if(res.type=="success"){
				$("#grantRoles").dialog("close");
				$("#datagrid").datagrid("clearSelections");
				$.messager.alert('提示','成功！','info');
			}
		}
	});
}

//所有角色
function getRoles(id){
	$.ajax({
		url:'/admin/sys/admin/getRoles?_' + $.now(),
		method:'get',
		dataType:'json',
		data:{
			id:id
		},
		success:function(res){
			var str = '';
			$.each(res.list, function(index, item){
				str += '<input type="checkbox" name="roleNames" value="'+item.name+'"/><span style="margin-right:10px;">'+item.name+'</span>';
			});
			
			$("#showRoles").append(str);
			if(id != null && typeof(id) != "undefined"){
				$("input[name='roleNames']").each(function(){
					var ck = $(this);
					$.each(res.roles, function(index, item){
						if(ck.val() == item.name){
							ck.prop("checked", true);
						}
					});
				});
			}
			
		}
	});
}
		