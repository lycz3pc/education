$(document).ready(function(){
	
	list();
	// 分页插件
	
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
	
	$('#dialog').dialog('close');

	$('#status').combobox({
		onSelect: function(){
			console.debug(123123);
			list();
		}
	});
});


// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/platform/digitCourse/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	current: options.pageNumber,	//页码
        	size: options.pageSize,	//每页显示行
        	digitCourseType: $("#status").combobox('getValue')
        }),
        success: function(data) {
        	var temp = data.page;
        	temp.rows = temp.records;
        	for (var i in temp.rows) {
        		var str = '';
        		if(temp.rows[i].digitCourseType == 1){
        			if (temp.rows[i].status == 0) {
        				str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_green l-btn l-btn-small" onclick="edit(' + temp.rows[i].digitCourseId + ', 1,0)">置顶</a>';
    				} else {
    					str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray l-btn l-btn-small" onclick="edit(' + temp.rows[i].digitCourseId + ', 0,0)">取消置顶</a>';
    				}
        		} else {
	    			if(temp.rows[i].isBookAudio == 0){
	        			str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_green l-btn l-btn-small" onclick="edit(' + temp.rows[i].digitCourseId + ',0,1)">设置每日听书</a>';
	        		}else{
	        			str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray l-btn l-btn-small" onclick="edit(' + temp.rows[i].digitCourseId + ',0,0)">取消设置</a>';
	        		}
    			}
        		temp.rows[i].digitCourseType == 1 ? temp.rows[i].digitCourseType = "视频课程" : temp.rows[i].digitCourseType = "音频课程";
				temp.rows[i].operation = str;
			}
            $('#datagrid').datagrid('loadData', temp);
        },
        error: function() {
            alert('error');
        }
    });
}

function edit(id, option, bookAudio){
	$.ajax({
		type: "POST",
		url: '/platform/digitCourse/edit?_' + $.now(),
		contentType: "application/json",
		data: JSON.stringify({digitCourseId: id, status: option, isBookAudio:bookAudio}),
		dataType: 'json',
		success: function (data) {
			if (data.code == 0) {
				$.messager.alert('提示', '设置成功', 'info');
				list();
			} else {
				$.messager.alert('提示', '设置失败', 'error');
			}
        }
	});
}