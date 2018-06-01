
$.messager.defaults.ok = '确认';
$.messager.defaults.cancel = '取消';
$.fn.filebox.defaults.width = '200px';
$.fn.filebox.defaults.buttonText = '浏览...';
$.fn.pagination.defaults.beforePageText = '第';
$.fn.pagination.defaults.afterPageText = '页    共 {pages} 页';
$.fn.pagination.defaults.displayMsg = '当前显示 {from} - {to} 条记录   共 {total} 条记录';

function upload(file,url,name,path,imgsrc) {
	var formdata = new FormData();
	if(!file || !file.files[0]){
		return;
	}
	formdata.append("file", file.files[0]);
	formdata.append("uploadPath", url);
	document.getElementById(name).value = file.files[0].name;
	$.ajax({
		url: "/sys/upload",
		type : 'post', 
		data : formdata, 
		cache : false,
		contentType : false,
		processData : false,
		dataType : "json",
		beforeSend: function (){
			uploadShade("文件上传中...");
		},
		complete: function() {
			disShade();
		},
		success: function (data) {
			document.getElementById(path).value = data.filePath;
			document.getElementById(imgsrc).src = data.filePath;
		}
	});
}

function setAble(flag,name){
	$("#dialog input").removeAttr("disabled");
	$("#dialog textarea").removeAttr("disabled");
	flag ? UM.getEditor(name).setEnabled() : 1;
	$(".this_but1").removeAttr("style");
	$(".this_but2").removeAttr("style");
}

function load() {
    $("<div class='window-mask'></div>").css({display:"block",width:"100%",height: $(window).height()}).appendTo("body");
}

function uploadShade(msg) {
	$(".window-mask").css('z-index', '999999');
    $("<div class='datagrid-mask-msg'></div>").html(msg).appendTo("body").css({'z-index':'999999',display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

function disLoad() {
    $(".window-mask").remove();
}

function disShade() {
	$(".window-mask").css('z-index', '');
    $(".datagrid-mask-msg").remove();
}

//字符串日期转时间戳(精确到秒)
function strToTime(strdate){
	return new Date(strdate).getTime()/1000;
}
//时间戳转日期
function formatTime(nS) {     
   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
}  

function formatStrDate(time){
	var d = new Date(time * 1000);    //根据时间戳生成的时间对象
	var date = (d.getMonth() + 1)+"/"+(d.getDate())+"/"+(d.getFullYear())+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	return date;
}