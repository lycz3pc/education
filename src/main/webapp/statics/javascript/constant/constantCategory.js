//对easyui验证扩展，添加了nameExist方法，该方法验证名称是否重复
$.extend($.fn.validatebox.defaults.rules, {
	nameExist: {
		validator: function(value,param){
			var text = $.ajax({
				url:"/platform/constant/category/checkCategoryName",
				method:'post',
				data:{
					categoryId:$(param[0]).val(),
					categoryName:value
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

$(function(){
	//列表
	$("#datagrid").datagrid({
		title: '常量类型列表',
		singleSelect:true,
		fitColumns:true,
		url:'/platform/constant/category/list',
		onLoadSuccess:function(data){
			//console.log(data);
	        $("a[name='update']").linkbutton();
	        $("a[name='delete']").linkbutton();   
		}
	});
});


//格式化操作列
function formatOpt(value, row, index){
	 var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="updateCat('+index+')">修改</a>';
	 /*str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delCat('+index+')">删除</a>';*/
	 return str;
}

//新增弹出
function popAdd(){
	dialogInit("新增常量类型");
	$("#form").form("clear");
	
	//列表重新加载，为了清空选中
	$("#dg").datagrid("reload");
	$(".this_table tr td .this_but3").text("清空");
}

//修改弹出
function updateCat(index){
	//选中index行
	$("#datagrid").datagrid("selectRow", index);
	//获得选中行对象
	var row = $("#datagrid").datagrid("getSelected");
	
	dialogInit("修改常量类型");
	$("#form").form("clear");
	//form加载数据
	$("#form").form("load",row);
	$(".this_table tr td .this_but3").text("重置");
}

//提交
function submitForm(){
	$("#form").form("submit",{
		url:'/platform/constant/category/addOrUpdate',
		success:function(data){
			var data = eval('(' + data + ')'); 
			if(data.type=="success"){
				$("#dialog").dialog("close");
				$("#datagrid").datagrid("reload");
				$.messager.alert('提示',"操作成功",'info');
			}else{
				$.messager.alert('提示',"操作失败",'info');
			}
		}
	});
}

//删除
function delCat(index){
	//选中index行
	$("#datagrid").datagrid("selectRow", index);
	//获得选中行对象
	var row = $("#datagrid").datagrid("getSelected");
	
	$.ajax({
		url:'/platform/constant/item/hasConstant',
		method:'post',
		async:false,
		cache:false,
		dataType:'json',
		data:{
			categoryId:row.categoryId
		},
		success:function(res){
			if(res.type=="success"){
				$("#tip").empty().text("该常量类型下存在常量，此操作将常量类型和常量一并删除，确认删除吗？");
			}else{
				$("#tip").text("该操作将常量类型永久删除，确认删除吗？");
			}
		}
	});
	
	$("#tip").dialog({
		title:'确认删除',
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$.ajax({
					url:"/platform/constant/category/delete",
					type:'post',
					dataType:'json',
					data:{
						categoryId:row.categoryId
					},
					success:function(data){
						if(data.type=="success"){
							$("#dialog").dialog("close");
							$("#datagrid").datagrid("reload");
							$.messager.alert('提示','删除成功！','info');
							
						}else{
							$.messager.alert('提示',res.msg,'info');
						}
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialog").dialog("close");
			}
		}]
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
	
	if($("#categoryId").val()==""){
		//新增时清空数据
		$('#form').form('clear');
	}else{
		//更新时重置数据
		var row = $("#datagrid").datagrid("getSelected");
		//清空表单
		$('#form').form('clear');
		
		dialogInit("修改常量类型");
		$('#form').form('load', row);	
	}
	
}
