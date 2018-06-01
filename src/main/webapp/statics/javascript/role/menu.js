
// 修改easyUI默认验证提示
$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
// easyUI拓展验证
$.extend($.fn.validatebox.defaults.rules, {
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
});

var setting = {
	check:{
		enable:false
	},
	callback:{
		onClick:this.zTreeOnClick
	},
	data:{
		keep:{
			leaf:false,
			parent:true
		},
		key:{
			name: "text",
			url:""
		},
		simpleData:{
			enable:true,
			idKey:"id",
			pIdKey:"parentId"
		}
	},
	view:{
		showLine:true
	}
};


$(document).ready(function(){
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	//分页插件
	$('#datagrid').datagrid('getPager').pagination({
		pageSize: 10,
		pageNumber: 1,
		pageList: [10, 20, 30, 40, 50],
		beforePageText: '第',
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onSelectPage: function(pageNumber, pageSize){
			//list();
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			list(nodes[0].id);
		}
	});
	// 查询按钮点击事件
	$("#search").click(function(){
		list();
	});

		// 新增弹出层
		$("#add").click(function(){
			// 清空from表单
			var parentId = $("#parentId").combotree('getValue');
			$('#form').form('clear');
			$("#update").hide();
			$("#save").show();
			$("#parentId").combotree('setValue',parentId);
			$("#menu").click();
			if(parentId==''){
				$.messager.alert('提示','请选择菜单!','info');
               return
			}

		$('#dialog').dialog({
			title: '新增'
		});
		$('#dialog').dialog('open');
	});

	reloadTree();//刷新树
	combotree();
});

// 刷新树
function reloadTree(){
	$.ajax({
		method: 'POST',
		url: '/admin/sys/permission/listChildList',
		dataType: 'json',
		data:{"type":''},
		success: function(r) {
			var zNodes = r.permiss;
			var ztree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree.expandAll(false);
			var nodes = ztree.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
            	ztree.expandNode(nodes[i], true, false, true);
            }
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


// 刷新树
function combotree(){
	$.ajax({
		method: 'POST',
		url: '/admin/sys/permission/listChildList',
		dataType: 'json',
		data:{"type":'dir'},
		success: function(r) {
			var zNodes = r.permiss;
			$("#parentId").combotree('loadData', zNodes).combotree({
				width: '155px',
				onSelect: function(node){
					//返回树对象
					var tree = $(this).tree;
					//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
					var isLeaf = tree('isLeaf', node.target);
					if (!isLeaf) {
						//清除选中
						$('#parentId').combotree("clear");
					}
				}
			});
		}
	});
}



//保存橘角色
function save(){
	var text = $("#text").textbox('getValue');
	if(text.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','显示文本不能为空!','info');
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/sys/role/permissionadd',
		data: $("#form").serialize(),
		async: false,
		dataType: "json",
		success: function (data) {
			if (data.type == "success") {
				var parentId = $("#parentId").combotree('getValue');
				list(parentId);
				reloadTree();//刷新树
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});
}

//修改角色
function update(){
	var text = $("#text").textbox('getValue');
	if(text.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','显示文本不能为空!','info');
		return ;
	}

	$.ajax({
		type: "POST",
		url: '/sys/role/permissionaupd',
		data: $("#form").serialize(),
		async: false,
		dataType: "json",
		success: function (data) {
			if (data.type == "success") {
				var parentId = $("#parentId").combotree('getValue');
				list(parentId);
				reloadTree();//刷新树
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
				url: '/sys/role/permissionaudel',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					if (data.type == "success") {
						$.messager.alert('提示','删除成功','info');
						var parentId = $("#parentId").combotree('getValue');
						list(parentId);
						reloadTree();//刷新树
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
	getMission(id);
	$('#dialog').dialog({
		title: '修改'
	});
	$('#dialog').dialog('open');
}

//获取当前角色原有权限
function getMission(id){
			$.ajax({
				async: false,
				type: "POST",
				url: '/sys/role/mission',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					$('#name').textbox('setValue',data.permission.name);
					$('#url').textbox('setValue',data.permission.url);
					$('#text').textbox('setValue',data.permission.text);
					$('#priority').textbox('setValue',data.permission.priority);
					$('#permission').textbox('setValue',data.permission.permission);
					$('#id').textbox('setValue',data.permission.id);
					$("#parentId").combotree('setValue',data.permission.parentId);
					if(data.permission.type=='dir'){
				          $("#dir").click();
					}
					if(data.permission.type=='menu'){
						$("#menu").click();
					}
				}
			});
}

// 栏目树点击事件
function zTreeOnClick(event, treeId, treeNode){
	// 子节点不可点击
	if (treeNode.isParent==false) {
		return ;
	}
	$("#parentId").textbox('setValue',treeNode.id);
	$("#id").textbox('setValue','');
	list(treeNode.id);
}



// 查询列表数据
function list(id){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
		method: 'POST',
		url: '/sys/role/permission?_' + $.now(),
		contentType: "application/json",
		dataType: 'json',
		data: JSON.stringify({
			id: id,
			current: options.pageNumber,	//页码
			size: options.pageSize	//每页显示行
		}),
		success: function(data) {
			var temp = data.page;
			temp.rows = temp.records;
			// 操作
			for (var i in temp.rows) {
				var str = '<a class="this_but_red" onclick="upd('+temp.rows[i].id+')">修改</a>';
				str += '<a class="this_but_black" onclick="del(' + temp.rows[i].id + ')">删除</a>';
				temp.rows[i].operation = str;
			}
			$('#datagrid').datagrid('loadData', temp);
		},
		error: function() {
			alert('error');
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

		