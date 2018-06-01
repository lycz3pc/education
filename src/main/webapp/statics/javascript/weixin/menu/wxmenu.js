
// 修改easyUI默认验证提示
//$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
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
		onRename:this.onRename,
		onClick:this.zTreeOnClick
	},
	edit:{
		enable: true,
		showRemoveBtn: false,
		renameTitle: "编辑节点名称",
		showRenameBtn: setRenameBtn,
		},
	data:{
		keep:{
			leaf:false,
			parent:true
		},
		key:{
			name: "name",
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
	
	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
    	setDisable();
    	upd(rowData.id, 0);
    }});

	// 新增弹出层
	$("#add").click(function(){
		
		setAble();
		// 清空from表单
		$('#name').textbox('setValue','');
		$('#url').textbox('setValue','');
		$('#key').textbox('setValue','');
		//var parentId = $("#parentId").combotree('getValue');
		var parentId = $("#parentId").textbox('getValue');
		if(parentId==null || parentId=='' || parentId<1){
			$.messager.alert('提示','请选择目录!','info');
           return
		}
		
		
		$("#menu").click();

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
		url: '/npWxMenuController/findMenuList',
		contentType: "application/json",
		dataType: 'json',
		
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
		url: '/npWxMenuController/findMenuList',
		dataType: 'json',
		success: function(r) {
			var zNodes = r.permiss;
			$("#type").combotree('loadData', zNodes).combotree({
				width: '155px',
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


function save(){
	var text = $("#text").textbox('getValue');
	var name = $("#name").textbox('getValue');
	var url = $("#url").textbox('getValue');

	if(text.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','显示文本不能为空!','info');
		return ;
	}
	if(name.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','名称不能为空!','info');
		return ;
	}
	if(url.replace(/(^\s*)|(\s*$)/g, "")==''){
		$.messager.alert('提示','资源路劲不能为空!','info');
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
				var parentId = $("#parentId").textbox('getValue');
				list(parentId);
				reloadTree();//刷新树
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});
}

function update(){
	$.ajax({
		type: "POST",
		url: '/npWxMenuController/updateMenu',
		data: $("#form").serialize(),
		async: false,
		dataType: "json",
		success: function (data) {
			if (data.type == "success") {
				var parentId =	$("#parentId").textbox('getValue');
				$('#form').form('clear');
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
				url: '/npWxMenuController/deleteMenu',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					if (data.type == "success") {
						$.messager.alert('提示','删除成功','info');
						var parentId = $("#parentId").textbox('getValue');
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
function upd(id,option) {
	option == 1 ? setAble() : 1;
	$('#form').form('clear'); // 清空from表单
	getMission(id);
	$('#dialog').dialog({
		title: option == 1 ? '修改' : '详情'
	});
	$('#dialog').dialog('open');
}

function getMission(id){
			$.ajax({
				async: false,
				type: "POST",
				url: '/npWxMenuController/findMenuObject',
				data:{"id":id},
				dataType : "json",
				success: function (data) {
					$('#name').textbox('setValue',data.npWxMenuT.name);
					$('#url').textbox('setValue',data.npWxMenuT.url);
					$('#key').textbox('setValue',data.npWxMenuT.key);
					$('#id').textbox('setValue',data.npWxMenuT.id);
					$('#parentId').textbox('setValue',data.npWxMenuT.parentId);
					
					$("#s_type").empty(); 
					$("#s_type").append("<option value='请选择'>请选择</option>");
					$("#s_type").append("<option value='view'>view</option>");
					$("#s_type").append("<option value='click'>click</option>");
					
					$("#s_type option[value='"+data.npWxMenuT.type+"']").attr("selected", true);
				}
			});
}

// 栏目树点击事件
function zTreeOnClick(event, treeId, treeNode){
	// 子节点不可点击
	if (treeNode.isParent==false && treeNode.type=="menu") {
		return ;
	}
	$("#parentId").textbox('setValue',treeNode.id);
	list(treeNode.id);
}

function setRenameBtn(treeId, treeNode) {
	
	return treeNode.isParent;
}


function onRename(e, treeId, treeNode) {
	$.ajax({
		async: false,
		type: "POST",
		url: '/npWxMenuController/updateMenu',
		data: {
			"id":treeNode.id,
			"name":treeNode.name,
		},
		dataType: "json",
		success: function (data) {
			if (data.code == 0) {
				var parentId = $("#parentId").textbox('getValue');
				list(parentId);
				reloadTree();//刷新树
				combotree();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示',data.msg,'info');
			}
		}
	});

}

// 查询列表数据
function list(id){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
		type: 'POST',
		url: '/npWxMenuController/findMenuList?_'+$.now(),
		data: {"pid":id},
		dataType: 'json',
		success: function(data) {
			var temp = data.permiss;
			// 操作
			for (var i in temp) {
				var str = '<a class="this_but_red" onclick="upd('+temp[i].id+',1)">修改</a>';
				str += '<a  class="this_but_black" onclick="del(' + temp[i].id + ')">删除</a>';
				temp[i].operation = str;
			}
			$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
            	setDisable();
            	upd(rowData.id, 0);
            }});
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
	
function setDisable(){
	$("#update").hide();
	$("#dialog input").attr("disabled","disabled");
	$("#dialog textarea").attr("disabled","disabled");
	$(".this_but1").attr("style", "display: none");
}

function setAble(){
	$("#update").show();
	$("#save").hide();
	$("#dialog input").removeAttr("disabled");
	$("#dialog textarea").removeAttr("disabled");
	$(".this_but1").removeAttr("style");
}