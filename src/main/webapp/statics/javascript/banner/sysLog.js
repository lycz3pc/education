$(function(){
	//时间框不显示秒
	 $('#start').datetimebox({
		 showSeconds:false,
		 formatter:formatter,
		 parser:parse,
		 editable:false
	 });
	 $('#end').datetimebox({
		 showSeconds:false,
		 formatter:formatter,
		 parser:parse,
		 editable:false
	 });
		
	//初始化
	init();
});

//初始化
function init(){
	$("#dg").datagrid({
		title: '系统日志列表',
		singleSelect:true,
		fitColumns:true,
		url:'/admin/sys/sysLog/list',
		method:'post',
		queryParams:{
			start:$("#start").datebox("getValue"),
			end:$("#end").datebox("getValue")
		}
	});
}
//根据开始时间和结束时间查询
function qryLog(){
	$("#dg").datagrid("load",{
		start:$("#start").datebox("getValue"),
		end:$("#end").datebox("getValue")
	});
}

//格式化日期
function formatter(date){
	//年
	var y = date.getFullYear();
	//月
	var m = date.getMonth()+1;
	//日
	var d = date.getDate();
	//时
	var h = date.getHours();
	//分
	var mi = date.getMinutes();
	//var ss = date.getSeconds();
	m = m < 10?"0"+m:m;
	d = d<10?"0"+d:d;
	
	h = h < 10?"0"+h:h;
	mi = mi<10?"0"+mi:mi;
	
	return 	y+"-"+m+"-"+d+" "+h+":"+mi;
	
}

function parse(s){
	var t = Date.parse(s);
	if (!isNaN(t)){
		return new Date(t);
	} else {
		return new Date();
	}
}


