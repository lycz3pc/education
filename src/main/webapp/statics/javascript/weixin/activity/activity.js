// 实例化编辑器
var um;

	
$(document).ready(function(){
	// 富文本初始化
	um = UM.getEditor('hContent');
	um.setContent('${content}',false)
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	// 默认关闭详情弹窗
	$('#getActivity').dialog('close');
	
	// 默认关闭参与详情弹窗
	$('#activityPartake').dialog('close');
	
	$('#authorDialog').dialog('close');
	
	$('#tagDialog').dialog('close');
	
	$('#themeDialog').dialog('close');
	//限制简介的长度
	$('#detail').textbox('textbox').attr('maxlength',100);
	
	// 查询右边内容发布的数据
	list();
	
	//获取活动详情
	$("#datagrid").datagrid({
		onDblClickRow : function(index, row){
			$('#activity_form').form('clear');
			$.ajax({
				type: "POST",
				url: '/npWxActivityT/findActivityObject',
				data: {"hid": row.hId},
				dataType : "json",
				success: function(data){
					data.npWxActivityT.startDate = formatStrDate(data.npWxActivityT.startDate);
					data.npWxActivityT.endDate = formatStrDate(data.npWxActivityT.endDate);
					$("#hTitles").textbox('setValue', data.npWxActivityT.hTitle);
					$("#activityTypes").textbox('setValue', data.npWxActivityT.activityType);
					$("#activityPrices").textbox('setValue', data.npWxActivityT.activityPrice);
					$("#partakeNums").textbox('setValue', data.npWxActivityT.partakeNum);
					$("#startDates").datetimebox('setValue',data.npWxActivityT.startDate);
					$("#endDates").datetimebox('setValue',data.npWxActivityT.endDate);
					$("#details").textbox('setValue', data.npWxActivityT.detail);
					$("#coverImgs").attr('src', (data.npWxActivityT.activityResources || []).path || '/statics/images/addimg.png');
					UM.getEditor('hContents').setContent(data.npWxActivityT.hContent, false);
					UM.getEditor('hContents').setDisabled('fullscreen');
					
				}
			});
			setDisable();
			$('#activity_form').dialog({
				    title: '活动详情',
				    width: 700,
				    height: 480,
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
			$('#activity_form').dialog('open');
			
		}
	});
	
	// 分页插件
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
 // 分页插件
    $('#dg').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
        var hid	=localStorage.getItem("hid");
        	acList(hid);
    	}
	});
	
	$("#search").click(function(){
		list();
	});
	
	$("#add").click(function(){
		setAble(true,'hContent');
		// 清空from表单
		$('#form').form('clear');
		// 清空预览显示
		$("#coverImg").attr('src', '/statics/images/addimg.png');
		// 清空富文本
		um.setContent('', false);
		
		$("#from").textbox({icons: []});
		
		$("#accessoryFile").empty();
		$("#isShow").hide();
		$('#dialog').dialog({
		    title: '活动发布',
		    width: 700,
		    height: 480,
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
	
	

	$("#coverUrl").filebox({
		onChange: function(){
			var coverUrlFile = document.getElementById('filebox_file_id_1');
			upload(coverUrlFile, "/upload/cover", 'coverName','coverPath','coverImg');
		}
	});
});

function setDisable(){
	$("#activity_form input").attr("disabled","disabled");
	$("#activity_form textarea").attr("disabled","disabled");
}

// 查询右边内容发布的数据
function list(){
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/npWxActivityT/findNpWxActivityList',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	title: $.trim($("#searchTitle").textbox('getValue')),
        	page: options.pageNumber,	//页码
        	rows: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	var temp = data.page;
        	temp.rows = temp.records;
        	for ( var i in temp.rows) {
        		temp.rows[i].createDate= formatStrDate(temp.rows[i].createDate);
        		temp.rows[i].startDate= formatStrDate(temp.rows[i].startDate);
        		temp.rows[i].endDate= formatStrDate(temp.rows[i].endDate);
        		if(temp.rows[i].activityStatus==0){
        			temp.rows[i].activityStatus= "筹备中";
        		}else if(temp.rows[i].activityStatus==1){
        			temp.rows[i].activityStatus= "进行中";
        		}else if(temp.rows[i].activityStatus==2){
        			temp.rows[i].activityStatus= "已结束";
        		}
        		
        		var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="acList(' + temp.rows[i].hId+ ')">活动情况</a>';
        		str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + temp.rows[i].hId+ ')">修改</a>';
        		str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(' + temp.rows[i].hId + ')">删除</a>';
        		temp.rows[i].operation = str;
			}
            $('#datagrid').datagrid('loadData', temp);
        },
        error: function() {
            alert('error');
        }
    });
}

function acList(hid){
	localStorage.setItem("hid", hid);
	var options = $("#dg").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/activityPartake/npWxActivityPartakeT/findNpWxActivityPartakeList',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	hid: hid,
        	page: options.pageNumber,	//页码
        	rows: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	
        	$.ajax({
        		type: "POST",
        		url: '/npWxActivityT/findActivityObject',
        		data: {"hid": hid},
        		dataType : "json",
        		success: function(acData){
        			var temp = data.page;
                	temp.rows = temp.records;
                	for ( var i in temp.rows) {
                		temp.rows[i].createDate= formatStrDate(temp.rows[i].createDate);
                		temp.rows[i].activityId=acData.npWxActivityT.hTitle;  
                		if(temp.rows[i].status==0){
                			temp.rows[i].status="未支付";
                		}else if(temp.rows[i].status==1){
                			temp.rows[i].status="已支付";
                		}
        			}
                	  $('#dg').datagrid('loadData', temp);
                },
                error: function() {
                    alert('error');
        		}
        	});
        	
        }
    });
	$('#activityPartake').dialog({
		title: '活动参与详情',
		onOpen: function(){
			load();
		},
		onClose: function() {
			disLoad();
		}
	});
	
}

function save(){
	var date = {
		hId: $("#hId").textbox('getValue'),
        hTitle: $("#hTitle").textbox('getValue'),
        partakeNum: $("#partakeNum").textbox('getValue'),
        activityPrice: $("#activityPrice").textbox('getValue'),
        activityType: $("#activityType").textbox('getValue'),
        startDate: strToTime($("#startDate").textbox('getValue')),
        endDate: strToTime($("#endDate").textbox('getValue')),
        activityStatus:$('input:radio:checked').val(),
        detail:$("#detail").textbox('getValue'),
        hContent: um.getContent(),
        activityResources: {
			id: $("#coverId").val(),
			name: $("#coverName").val(),
			path: $("#coverPath").val()
		}
		
	};
	// 判断修改或者新增
	$("#hId").textbox('getValue') == 0 ? 1 : date['id'] = $("#hId").textbox('getValue');
	if (!$("#form").form('validate')) {
		return ;
	}
	if (!um.hasContents()) {
		um.focus();
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/npWxActivityT/saveOrEditActivity',
		contentType: "application/json",
		data: JSON.stringify(date),
		dataType: 'json',
		beforeSend: function (){
			uploadShade("数据提交中...");
		},
		complete: function() {
			disShade();
			load();
			disLoad();
		},
		success: function (data) {
			if (data.code == 0) {
				$('#from').textbox('setValue', null);
				$.messager.alert('提示','保存成功','info');
				$('#from').textbox('setValue', null);
				// reloadTree();
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示','保存失败','error');
			}
        }
	});
}

function edit(hid){
	$('#form').form('clear');
	$("#isShow").show();
	$.ajax({
		type: "POST",
		url: '/npWxActivityT/findActivityObject',
		data: {"hid": hid},
		dataType : "json",
		success: function(data){
			
			data.npWxActivityT.startDate = formatStrDate(data.npWxActivityT.startDate);
			data.npWxActivityT.endDate = formatStrDate(data.npWxActivityT.endDate);
			
			
			
			$('#form').form('load', data.npWxActivityT);
			$('#from').textbox('setValue', data.npWxActivityT);
			$('#activityStatus'+data.npWxActivityT.activityStatus).attr("checked","checked");
			
			$("#coverImg").attr('src', (data.npWxActivityT.activityResources || []).path || "/statics/images/addimg.png");
			um.setContent(data.npWxActivityT.hContent, false);
			$("#coverUrl").textbox('setValue', data.npWxActivityT.activityResources.name);
		}
	});

	

	
	$('#dialog').dialog({
		title: '内容修改',
	    width: 700,
	    height: 480,
	    editable:false,
		resizable: false,
		draggable: false,
		onOpen: function(){
			load();
		},
		onClose: function() {
			$('#from').textbox('setValue', null);
			disLoad();
		}
	});
}

function del(id){
	$.messager.confirm('确认', '确认删除？', function(r){
		if (r){
			
			$.ajax({
				type: "POST",
				url: '/npWxActivityT/saveOrEditActivity',
				contentType: "application/json",
				data: JSON.stringify({hId: id,isDelete:1}),
				dataType: 'json',
				beforeSend: function (){
					load();
					uploadShade("数据删除中...");
				},
				complete: function() {
					disShade();
					disLoad();
				},
				success: function (data) {
					if (data.code == 0) {
						$.messager.alert('提示','删除成功','info');
						list();
					} else {
						$.messager.alert('提示','删除失败','error');
					}
	            }
			});
		}
	});
	
}

