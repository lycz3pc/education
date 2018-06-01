
var us_kerdown=function (){
	list();
};
// 修改easyUI默认验证提示
//$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
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
$.extend($.fn.validatebox.defaults.rules, {
	name: {
		validator: function(value, param){
			return $.trim(value)!='';
		},
		message: '不能为空'
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
			var treeObj = $.fn.zTree.getZTreeObj('treeDemo');
			treeObj.checkAllNodes(false);
			$("#update").hide();
			$("#save").show();
			// 清空from表单
			$('#form').form('clear');
		$('#dialog').dialog({
			title: '新增'
		});
		$('#dialog').dialog('open');
	});

	list();
	reloadTree();//刷新树

});

// 刷新树
function reloadTree(){
	$.ajax({
		method: 'POST',
		url: '/admin/sys/permission/listChildList',
		contentType: "application/json",
		dataType: 'json',
		data:{},
		success: function(r) {
			var zNodes = r.permiss;
			var ztree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree.expandAll(true);
			$('#type').combotree('loadData', [zNodes]).combotree('setValue', -1);
			$('#type').combotree({
				onSelect: function(node){
					//返回树对象
					var tree = $(this).tree;
					//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
					var isLeaf = tree('isLeaf', node.target);
					if (!isLeaf) {
						//清除选中
						$('#type').combotree("clear");
					}
				}
			});
		}
	});
}

// 查询列表数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
		method: 'POST',
		url: '/sys/role/list?_' + $.now(),
		contentType: "application/json",
		dataType: 'json',
		data: JSON.stringify({
			name: $("#names").textbox('getValue'),
			current: options.pageNumber,	//页码
			size: options.pageSize	//每页显示行
		}),
		success: function(data) {
			/*console.log(data)*/
			var temp = data.page;
			temp.rows = temp.records;
			// 操作
			for (var i in temp.rows) {
				var str = '<a  class="this_but_red"  onclick="upd('+temp.rows[i].id+')">修改</a>';
				str += '<a  class="this_but_black" onclick="del(' + temp.rows[i].id + ')">删除</a>';
				temp.rows[i].operation = str;
			}
			$('#datagrid').datagrid('loadData', temp);
			for (j=0;j<temp.rows.length;j++) {
				$("td[field=name]").eq(j+1).attr("title", temp.rows[j].name);
			}	
		},
		error: function() {
			alert('error');
		}
	});
}

//保存橘角色
function save(){
	var chk_value =[];
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");//获取勾选权限
	var nodes = treeObj.getCheckedNodes(true);
	for (var i = 0; i < nodes.length; i++) {
		chk_value.push(nodes[i].id);
	}

	var name = $("#name").textbox('getValue');
	if(name.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','名称不允许为空!','info');
		return ;
	}

	if(chk_value.length == 0){
		$.messager.alert('提示','至少勾选一列菜单!','info');
		return ;
	}
	if (!$("#form").form('validate')) {
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/sys/role/save',
		contentType: "application/json",
		data: JSON.stringify({
			name: $("#name").val(),
			chkvalue:chk_value
		}),
		dataType : "json",
		success: function (data) {
			if (data.type == "success") {
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});
}

//修改角色
function update(){
	var chk_value =[];
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");//获取勾选权限
	var nodes = treeObj.getCheckedNodes(true);
	for (var i = 0; i < nodes.length; i++) {
		chk_value.push(nodes[i].id);
	}
	var name = $("#name").textbox('getValue');
	if(name.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','名称不允许为空!','info');
		return ;
	}

	if(chk_value.length == 0){
		$.messager.alert('提示','至少勾选一列菜单!','info');
		return ;
	}
	if (!$("#form").form('validate')) {
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/sys/role/update',
		contentType: "application/json",
		data: JSON.stringify({
			id:$("#id").val(),
			name: $("#name").val(),
			chkvalue:chk_value
		}),
		dataType : "json",
		success: function (data) {
			if (data.type == "success") {
				list();
				$('#dialog').dialog('close');
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
				async: false,
				type: "POST",
				url: '/sys/role/del',
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
	$("#save").hide();
	$('#form').form('clear'); // 清空from表单
	$('#id').val(id);
	var role = JSON.parse(getRole(id));
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkAllNodes(false);
		for (var j  in role) {
			var node = zTree.getNodeByParam("id",role[j].permissionId);
			zTree.checkNode(node, true, false);
		}
	$('#dialog').dialog({
		title: '修改'
	});
	$('#dialog').dialog('open');
}

//获取当前角色原有权限
function getRole(id){
	var val=[]
			$.ajax({
				async: false,
				type: "POST",
				url: '/sys/role/role',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					$('#name').textbox('setValue',data.role.name);
				 val = JSON.stringify(data.rolePermissions);
				}
			});
	return val
}



// 时间格式化
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y + '-' + m + '-' + d;
}
		