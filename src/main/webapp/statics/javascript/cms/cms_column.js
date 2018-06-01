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
				name: "cloumnName",
			url:"url2"
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
	$('#dialog').dialog('close');
	
	reloadTree();
	
	list();
	
	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
    	setDisable(false);
    	edit(rowIndex, 0);
    }});
	
	$("#add").click(function (){
		$('#form').form('enableValidation');
		setAble(false);
		$('#form').form('clear');
		$("#parentId").combotree('setValue', '4bb6b88cf84a44f986c5155eadb825b6');
		$('#dialog').dialog({
		    title: '新增栏目',
		    width: 260,
		    height: 214,
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

function reloadTree(){
	$.ajax({
		type: "POST",
		url: "/platform/cmsColumn/list?_" + $.now(),
		contentType: "application/json",
		data: JSON.stringify({id: -1}),
		dataType: 'json',
		async: false,
		success: function(r){
			var temp = r.columns;
			var zNodes = [];
			for (var i = 0; i < temp.length; i++) {
				// 仅保留学术成果及其子节点可选择
				if (temp[i].id == '4bb6b88cf84a44f986c5155eadb825b6') {
					zNodes.push(temp[i]);
				}
			}
			var ztree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree.expandAll(true);
			$('#parentId').combotree('loadData', zNodes);
		}
	});
}

// 查询右边内容发布的数据
function list(){
	var temp = $.fn.zTree.getZTreeObj("treeDemo");
	var column;
	if (temp != null) {
		column = temp.getSelectedNodes();
	}
	$.ajax({
        method: 'POST',
        url: '/platform/cmsColumn/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	id: (column == null || column.length == 0) ? 1 : column[0].id
        }),
        success: function(data) {
        	var temp = {};
        	temp.total = data.columns.length;
        	temp.rows = data.columns;
        	for ( var i in temp.rows) {
        		var str = '<a class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + i + ', 1)">修改</a>';
        		// str += '<a class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(\'/platform/cmsColumn/delete\',' + temp.rows[i].id + ')">删除</a>';
				temp.rows[i].operation = str;
			}
        	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
        		$('#form').form('disableValidation');
        		setDisable(false);
            	edit(rowIndex, 0);
            }});
            $('#datagrid').datagrid('loadData', temp);
        },
        error: function() {
            alert('error');
        }
    });
}

function zTreeOnClick(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	list();
}


function save(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (!$("#form").form('validate')) {
		return ;
	}
	// 判断修改或者新增
	var date = {
		cloumnName: $("#cloumnName").textbox('getValue'),
		parentId: $("#parentId").combotree('getValue')
	};
	
	$("#id").textbox('getValue') == 0 ? 1 : date['id'] = $("#id").textbox('getValue');
	if (date.id == date.parentId) {
		alert('数据错误，不能将自身修改为自身子级');
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/platform/cmsColumn/save?_' + $.now(),
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
				reloadTree();
				treeObj.selectNode(nodes[0]);
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示','保存失败','error');
			}
        }
	});
}

function edit(index, option){
	if(option == 1){
		setAble(false);
		$('#form').form('enableValidation');
	}
	var rows = $('#datagrid').datagrid('getRows');
	var temp = rows[index];
	$('#form').form('clear');
	$('#form').form('load', temp);
	$('#dialog').dialog({
	    title: option == 1 ? '修改栏目' : '查看',
	    width: 260,
	    height: 214,
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


function del(url, id){
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: url + '?_' + $.now(),
				contentType: "application/json",
				data: JSON.stringify({id: id}),
				dataType: 'json',
				beforeSend: function (){
					load();
					uploadShade("数据删除中...");
				},
				complete: function() {
					disLoad();
					disShade();
				},
				success: function (data) {
					if (data.code == 0) {
						$.messager.alert('提示','删除成功','info');
						list();
						reloadTree();
					} else {
						$.messager.alert('提示','删除失败','error');
					}
	            }
			});
		}
	});
}

$.extend($.fn.validatebox.defaults.rules, {
	cloumnName: {
		validator: function(value, param){
			var flag = false;
			$.ajax({
				method: 'POST',
				url: '/platform/cmsColumn/check',
				contentType: "application/json",
		        dataType: 'json',
		        async: false,
		        data: JSON.stringify({id: param[0] == '' ? 0 : param[0], cloumnName: value}),
		        success: function(data){
		        	flag = data;
		        }
			});
			return flag;
		},
		message: '栏目名称已存在'
    }
});