//easyui扩展方法equals,该方法比较内容是否一直
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
		validator: function(value,param){
			return value == $(param[0]).val();
		},
		message: '两次密码输入不一致'
    }
});

//easyui扩展方法equals,该方法比较内容是否一直
$.extend($.fn.validatebox.defaults.rules, {
    validatePwd: {
		validator: function(value,param){
			var reg = /^[0-9A-Za-z]{6,18}$/;
			return reg.exec(value);
		},
		message: '只能输入6-18位数字、字母'
    }
});


//当前登录用户
var admin = {};
//查询当前登录用户
$.ajax({
	url:'/sys/getAdmin?_' + $.now(),
	method:'post',
	dataType:'json',
	async:false,
	cache:false,
	success:function(res){
		if(res.type=="success"){
			admin = res.admin;	
		}
	}
});
//当前登录名
$(".pf-user-name").empty()
				  .append(admin.name);

//用户信息
function showInfo(){
	//姓名 infoName
	$("#infoName").empty().append(admin.name);
	//账号 infousername
	$("#infousername").empty().append(admin.username);
	//电话/手机 infoPhone
	$("#infoPhone").empty().append(admin.phone);
	//邮箱 infoEmail
	$("#infoEmail").empty().append(admin.email);
	
	$("#userInfo").dialog({
		title:'用户信息',
		modal:true,
		close:true
	});
}

//修改密码弹出框
function popChangePwd(){
	$("#changePwd").dialog({
		title:'修改密码',
		modal:true,
		close:true
	});
}
//修改密码
function changePwd(){
	var password = $("#password").val();
	var newPassword = $("#newPassword").val();
	
	if (!$("#changePwd").form('validate')) {
		return ;
	}
	
	$.ajax({
		url:'/admin/sys/admin/password',
		method:'post',
		dataType:'json',
		cache:false,
		data:{
			password:password,
			newPassword:newPassword
		},
		success:function(res){
			if(res.type=="success"){
				$.messager.alert('提示','成功！','info');
			}else{
				$.messager.alert('提示',res.msg,'info');
			}
			
			$("#changePwd").dialog("close");
		}
	});	
}





