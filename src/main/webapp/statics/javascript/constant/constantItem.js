//对easyui验证扩展，添加了nameExist方法，该方法验证名称是否重复
$.extend($.fn.validatebox.defaults.rules, {
	nameExist: {
		validator: function(value,param){
			var text = $.ajax({
				url:"/platform/constant/category/checkCategoryName",
				method:'post',
				data:{
					id:$(param[0]).val(),
					name:value
				},
				async:false,
				cache:false,
			}).responseText;
			var dataObj=eval("("+text+")");//转换为json对象  
			return dataObj.type=="success";
		},
		message: '常量类型名称已存在！'
	}
});

//对easyui验证扩展，添加了blank方法，该方法验证名称是否为空
$.extend($.fn.validatebox.defaults.rules, {
	blank: {
		validator: function(value){
			
			return value.trim()!="";
		},
		message: '常量类型名称不能为空！'
	}
});

$(function(){
	$("#categoryId").combobox({
		valueField:'categoryId',
		textField:'categoryName',
		onSelect:function(res){
			$("#categoryName").textbox("setValue", res.categoryName);
			
		}
	});
	//查询
	$("#qryCategoryId").combobox({
		valueField:'categoryId',
		textField:'categoryName',
		onChange:function(newVal, oldVal){
			var categoryId = newVal;
			categoryId = categoryId == "" ? null : categoryId;
			
			$("#datagrid").datagrid("load",{
				categoryId : categoryId
			});
		}
	});
	
	$('#categoryId').combobox({
		 onSelect: function(record){
			 $('#categoryName').textbox('setValue', record.categoryName);
			 $('#constantValue').textbox('setValue', $('#constantValue').textbox('getValue'));
		 }
	});
	
	//获取常量类型列表
	$.ajax({
		url:'/platform/constant/category/allConstantCategory',
		type:'post',
		dataType:'json',
		success:function(res){
			$("#categoryId").combobox('loadData', res.list);
			$("#qryCategoryId").combobox('loadData', res.list);
		}
	});
	
	//列表
	$("#datagrid").datagrid({
		title: '常量类型列表',
		singleSelect:true,
		fitColumns:true,
		url:'/platform/constant/item/list',
		method:'post',
		onLoadSuccess:function(data){
	        $("a[name='update']").linkbutton();
	        $("a[name='delete']").linkbutton();   
		}
	});
});

//格式化操作列
function formatOpt(value, row, index){
	 var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="updateCat('+index+')">修改</a>';
	 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delCat('+index+')">删除</a>';
	 return str;
}

//新增弹出
function popAdd(){
	dialogInit("新增常量");
	$("#form").form("clear");
	//列表重新加载，为了清空选中
	$("#datagrid").datagrid("reload");
	$(".this_table tr td .this_but3").text("清空");
}

//修改弹出
function updateCat(index){
	//选中index行
	$("#datagrid").datagrid("selectRow", index);
	//获得选中行对象
	var row = $("#datagrid").datagrid("getSelected");
	
	dialogInit("修改常量");
	$("#form").form("clear");
	//form加载数据
	$("#form").form("load",row);
	$(".this_table tr td .this_but3").text("重置");
}

//提交
function submitForm(){
	$("#form").form("submit",{
		url:'/platform/constant/item/addOrUpdate',
		success:function(data){
			var data = eval('(' + data + ')'); 
			if(data.type=="success"){
				$("#dialog").dialog("close");
				$("#datagrid").datagrid("reload");
				$.messager.alert('提示',"操作成功",'info');
				//刷新新父母前台常量
				refreshConstant();
			}else{
				$.messager.alert('提示',"操作失败",'info');
			}
		}
	});
}

//刷新家校共育常量
function refreshConstant(){
	$.ajax({
		url:'/platform/constant/item/refreshWebConstantItem',
		type:'post'
	});
}

//删除
function delCat(index){
	//选中index行
	$("#datagrid").datagrid("selectRow", index);
	//获得选中行对象
	var row = $("#datagrid").datagrid("getSelected");
	
	$("#tip").text("该操作将常量永久删除，确认删除吗？");
	$("#tip").dialog({
		title:'确认删除',
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$.ajax({
					url:"/platform/constant/item/delete",
					type:'post',
					dataType:'json',
					data:{
						id:row.constantId
					},
					success:function(data){
						if(data.type=="success"){
							$("#tip").dialog("close");
							$("#datagrid").datagrid("reload");
							$.messager.alert('提示','删除成功！','info');
							//刷新新父母前台常量
							refreshConstant();
						}else{
							$.messager.alert('提示',res.msg,'info');
						}
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$("#tip").dialog("close");
			}
		}]
	});
	
}

//根据常量类型查询
function qryConstants(){
	var categoryId = $("#qryCategoryId").combobox("getValue");
	categoryId = categoryId == "" ? null : categoryId;
	
	$("#datagrid").datagrid("load",{
		categoryId : categoryId
	});
}


//弹出框初始化
function dialogInit(title){
	$("#dialog").dialog({
		title:title,
		modal:true,
		close:true
	});
}

//清空
function clearForm(){
	
	if($("#constantId").val()==""){
		//新增时清空数据
		$('#form').form('clear');
	}else{
		//更新时重置数据
		var row = $("#datagrid").datagrid("getSelected");
		//清空表单
		$('#form').form('clear');
		
		dialogInit("修改常量");
		$('#form').form('load', row);	
	}
	
}

$.extend($.fn.validatebox.defaults.rules, {
	constantValue: {
		validator: function(value, param){
			var flag = false;
			$.ajax({
				method: 'POST',
				url: '/platform/constant/check',
				contentType: "application/json",
		        dataType: 'json',
		        async: false,
		        data: JSON.stringify({constantId: param[0] == '' ? 0 : param[0], categoryId: param[1] == '' ? 0 : param[1], constantValue: value}),
		        success: function(data){
		        	flag = data;
		        }
			});
			return flag;
		},
		message: '常量值已存在'
    }
});