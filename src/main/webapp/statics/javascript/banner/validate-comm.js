//给Array新增contains方法，该方法是判断数组中是否含有某值
Array.prototype.contains = function (obj) {  
    var i = this.length;  
    while (i--) {  
        if (this[i] === obj) {  
            return true;  
        }  
    }  
    return false;  
}  

$(function(){
	//验证不能为空
	$.extend($.fn.validatebox.defaults.rules, {
	    blank: {
			validator: function(value,param){
				return $.trim(value)!='';
				
			},
			message: '不能为空字符串'
	    }
	});
	
	//对EasyUI进行的拓展，添加了getFile方法。该方法能够获取当前FileBox对象选中的文件的File对象。
	$.extend($.fn.filebox.methods, {
	    getFile: function (myself) {
	        var temp = $(myself).next().children("[type='file']");
	        var file = document.getElementById(temp.attr("id"));

	        if (file.files.length > 0) {
	            // 若选中一个文件，则返回该文件的File对象
	            return file.files[0];
	        }

	        // 若未选中不论什么文件，则返回null
	        return null;
	    }
	});
	
});
