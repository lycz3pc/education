$.messager.defaults.ok = '确认';
$.messager.defaults.cancel = '取消';
$.fn.filebox.defaults.width = '200px';
$.fn.filebox.defaults.buttonText = '浏览...';
$.fn.pagination.defaults.beforePageText = '第';
$.fn.pagination.defaults.afterPageText = '页    共 {pages} 页';
$.fn.pagination.defaults.displayMsg = '当前显示 {from} - {to} 条记录   共 {total} 条记录';

function setDisable(flag){
	$("#dialog input").attr("disabled","disabled");
	$("#dialog textarea").attr("disabled","disabled");
	flag ? UM.getEditor('content').setDisabled('fullscreen') : 1;
	$(".this_but1").attr("style", "display: none");
	$(".this_but2").attr("style", "display: none");
}

function setAble(flag){
	$("#dialog input").removeAttr("disabled");
	$("#dialog textarea").removeAttr("disabled");
	flag ? UM.getEditor('content').setEnabled() : 1;
	$(".this_but1").removeAttr("style");
	$(".this_but2").removeAttr("style");
}

function upload(file, url, index) {
	var formdata = new FormData();
	formdata.append("file", file.files[0]);
	formdata.append("uploadPath", url);
	document.getElementById(index == 1 ? "coverName" : 'accessoryName').value = file.files[0].name;
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
			document.getElementById(index == 1 ? "coverPath" : 'accessoryPath').value = data.filePath;
			index == 1 ? document.getElementById("coverURL").src = data.filePath : 1;
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
		        data: JSON.stringify({id: param[0], cloumnName: value}),
		        success: function(data){
		        	flag = data;
		        }
			});
			return flag;
		},
		message: '栏目名称已存在'
    },
    trimBlanForNotNull: {
    	validator: function(value, param){
    		var temp = $.trim(value);
    		return temp.length > 0;
    	},
    	message: '必填项，不可为空格'
    }
});

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