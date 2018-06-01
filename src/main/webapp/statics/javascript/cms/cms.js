var us_kerdown=function (){
	list();
};
// 实例化编辑器
var um;
// ztree设置
var setting = {
	check: {
		enable:false
	},
	callback: {
		onClick:this.zTreeOnClick
	},
	data: {
		keep: {
			leaf:false,
			parent:true
		},
		key: {
			name:"cloumnName",
			url:"url2"
		},
		simpleData: {
			enable:true,
			idKey:"id",
			pIdKey:"parentId"
		}
	},
	view: {
		showLine:true
	}
};
	
$(document).ready(function(){
	// 富文本初始化
	um = UM.getEditor('content');
	// 默认关闭弹窗
	$('#dialog').dialog('close');
	
	$('#authorDialog').dialog('close');
	
	$('#tagDialog').dialog('close');
	
	$('#themeDialog').dialog('close');
	// 刷新页面左边的栏目树
	reloadTree();
	// 查询右边内容发布的数据
	list();
	
	$('#datagrid').datagrid({onDblClickRow: function(rowIndex, rowData){
    	setDisable(true);
    	edit(rowIndex, 0);
    }});
	
	// 分页插件
    $('#datagrid').datagrid('getPager').pagination({  
        pageSize: 10,  
        pageNumber: 1,  
        pageList: [10, 20, 30, 40, 50],  
        onSelectPage: function(pageNumber, pageSize){
    		list();
    	}
	});
	
	$("#search").click(function(){
		list();
	});
	
	$("#add").click(function(){
		setAble(true);
		// 清空from表单
		$('#form').form('clear');
		// 清空预览显示
		$("#coverURL").attr('src', '/statics/images/addimg.png');
		// 清空富文本
		um.setContent('', false);
		
		$("#from").textbox({icons: []});
		
		$("#accessoryFile").empty();
		
		$.ajax({
			method: 'POST',
		    url: '/platform/cms/getTagAndThemeList?_' + $.now(),
		    contentType: "application/json",
		    data: JSON.stringify({id: 0}),
		    dataType: 'json',
		    success: function(data) {
		      	var temp = data.tags;
		      	var themeTemp = data.themes;
		      	$("#tagTable").empty();
		      	$("#themeTable").empty();
		      	var tempStr = '<tr>';
		      	var themeTempStr = '<tr>';
		      	for (var i in temp) {
					tempStr += '<td><input value="' + temp[i].constantId + '" type="checkbox" name="tag" /><span>' + temp[i].constantValue + '</span></td>';
		      		if (i % 3 == 2) {
		      			tempStr += "</tr><tr>";
					}
				}
		      	for (var i in themeTemp) {
					themeTempStr += '<td><input value="' + themeTemp[i].constantId + '" type="checkbox" name="theme" /><span>' + themeTemp[i].constantValue + '</span></td>';
		      		if (i % 3 == 2) {
		      			themeTempStr += "</tr><tr>";
					}
				}
		      	$("#tagTable").append(tempStr);
		      	$("#themeTable").append(themeTempStr);
		    },
		    error: function() {
		        alert('error');
		    }
		});
		
		$('#dialog').dialog({
		    title: '内容发布',
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

	$("#accessory").filebox({
		onChange: function(){
			var accessoryFile = document.getElementById('filebox_file_id_1');
			upload(accessoryFile, "/upload/accessory", 2)
		}
	});
	
	$("#coverUrl").filebox({
		onChange: function(){
			var coverUrlFile = document.getElementById('filebox_file_id_2');
			upload(coverUrlFile, "/upload/cover", 1)
		}
	});
	
	$("#fromAuthors").hide();
	
	$("#type").combotree({
		onChange: function(newValue, oldValue){
			$("#fromName").empty();
			if (newValue == "a7aebef6470b462d9af65612fe37e81c") {
				$("#fromName").append("作者:");
				$(".themeTd").show();
	        	$("#from").textbox({
	        		editable: false,
	        		icons: [{
						iconCls:'icon-search',
						handler: function(e){
							var v = $(e.data.target).textbox('getValue');
							expertList();
							$('#authorDialog').dialog({
								title: '作者选择',
							    width: 540,
							    height: 360,
							    editable:false,
								resizable: false,
								draggable: false,
//								onOpen: function(){
//									load();
//								},
//								onClose: function() {
//									disLoad();
//								}
							});
						}
					}]
	        	}).textbox('setValue', '');
			} else {
				$("#fromName").append("来源:");
				$(".themeTd").hide();
				$("#from").textbox({
					editable: true,
					icons: []
				}).textbox('setValue', '');
			}
		}
	});
	
	$("#tag").textbox({
		editable: false,
		icons: [{
			iconCls:'icon-search',
			handler: function(e){
				$('#tagDialog').dialog({
					title: '标签选择',
				    width: 400,
				    height: 300,
				    editable:false,
					resizable: false,
					draggable: false,
//					onOpen: function(){
//						load();
//					},
//					onClose: function() {
//						disLoad();
//					}
				});
			}
		}]
	});
	
	$("#themeName").textbox({
		editable: false,
		icons: [{
			iconCls:'icon-search',
			handler: function(e){
				$('#themeDialog').dialog({
					title: '主题选择',
				    width: 400,
				    height: 300,
				    editable:false,
					resizable: false,
					draggable: false,
//					onOpen: function(){
//						load();
//					},
//					onClose: function() {
//						disLoad();
//					}
				});
			}
		}]
	});
	
});
// 刷新页面左边的栏目树
function reloadTree(){
	$.ajax({
        method: 'POST',
        url: '/platform/cmsColumn/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({id: -1}),
        success: function(r) {
			var zNodes = r.columns;
			var ztree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$('#type').combotree('loadData', zNodes);
			ztree.expandAll(true);
			$('#type').combotree({
				onSelect: function(node){
					//返回树对象  
			        var tree = $(this);  
			        var path = new Array();
			        do {
                        path.unshift(node.id);
                        var node = tree.tree('getParent', node.target);
                    } while (node);
			        $('#typePath').val(path.join(","));
				}
			});
        }
	});
}
// 栏目树点击事件
function zTreeOnClick(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	list();
}

// 查询右边内容发布的数据
function list(){
	var temp = $.fn.zTree.getZTreeObj("treeDemo");
	var cms = null;
	if (temp != null) {
		cms = temp.getSelectedNodes();
	}
	var options = $("#datagrid").datagrid("getPager").data("pagination").options;
	$.ajax({
        method: 'POST',
        url: '/platform/cms/list?_' + $.now(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify({
        	title: $.trim($("#searchTitle").textbox('getValue')),
        	type: (cms == null || cms.length == 0) ? -1 : cms[0].id,	//栏目ID
        	current: options.pageNumber,	//页码
        	size: options.pageSize	//每页显示行
        }),
        success: function(data) {
        	var temp = data.page;
        	temp.rows = temp.records;
        	for ( var i in temp.rows) {
        		var str = '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red l-btn l-btn-small" onclick="edit(' + i + ', 1)">修改</a>';
        		str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black l-btn l-btn-small" onclick="del(\'/platform/cms/delete\',' + temp.rows[i].id + ')">删除</a>';
        		if (cms != null && cms.length != 0) {
        			if (temp.rows[i].position == 0) {
        				str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_violet l-btn l-btn-small" onclick="setTop(' + temp.rows[i].id + ', 1)">头条</a>';
					} else {
						str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray l-btn l-btn-small" onclick="setTop(' + temp.rows[i].id + ', 0)">取消头条</a>';
					}
				}
        		if (cms != null && cms.length != 0 && (
        				temp.rows[i].typePath.indexOf("a7aebef6470b462d9af65612fe37e81c") >= 0 || 
        				temp.rows[i].typePath.indexOf("66815b1c74e8405f8136bb5af4993759") >= 0)) {
        			if (temp.rows[i].news == 0) {
        				str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_green l-btn l-btn-small" onclick="setTop(' + temp.rows[i].id + ', 3)">置顶</a>';
					} else {
						str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray l-btn l-btn-small" onclick="setTop(' + temp.rows[i].id + ', 2)">取消置顶</a>';
					}
				}
				temp.rows[i].operation = str;
			}
            $('#datagrid').datagrid('loadData', temp);
        },
        error: function() {
            alert('error');
        }
    });
}

function expertList() {
	$.ajax({
      method: 'POST',
      url: '/platform/cmsExpert/list?_' + $.now(),
      contentType: "application/json",
      dataType: 'json',
      data: JSON.stringify({
      	  current: 1,	//页码
      	  size: 2147483647	//每页显示行
      }),
      success: function(data) {
      	  var temp = data.page.records;
      	  $("#authorTable").empty();
      	  var tempStr = '<tr>';
      	  for (var i in temp) {
      		  if ($('#from').textbox('getValue') == temp[i].name) {
      			  tempStr += '<td class="tagTable_input" style="border:1px solid #1da028;"><input value="' + temp[i].id + '" type="radio" checked="checked" name="from" /><span>' + temp[i].name + '</span></td>';
			  } else {
				  tempStr += '<td><input value="' + temp[i].id + '" type="radio" name="from" /><span>' + temp[i].name + '</span></td>';
			  }
      		  if (i%5 == 4) {
      			  tempStr += "</tr><tr>";
			  }
		  }
      	  tempStr += '</tr><tr><td><span>其他</span><input value="-1" type="radio" name="from"></input></td><td colspan="11"><input id="fromExpert" placeholder="若勾选其他，请在此输入专家姓名" class="textbox-text validatebox-text" disabled></input></td></tr>';
      	  $("#authorTable").append(tempStr);
      	  $("input[name='from']").click(function(){
      		  var id = $(this).val();
      		  var name = $(this).next("span").text();
    		  if (id != '-1') {
    			  $('#expert').val(id);
    			  $('#from').textbox('setValue', name);
    			  $('#authorDialog').dialog('close');
    			  load();
    			  $("#this_but1").hide()
			  }
    	  });
      },
      error: function() {
          alert('error');
      }
  });
}

function authorSave() {
	var name = $("input[type='radio']:checked").val();
	console.debug($("input[type='radio']:checked"));
	if ($("input[type='radio']:checked").length == 0) {
		alert('请选择专家！');
		return ;
	}
	if (name != '-1') {
		name = $("input[type='radio']:checked").next("span").text();
		$('#from').textbox('setValue', name);
	} else {
		tempName = $('#fromExpert').val();
		if (tempName == '') {
			alert("请填写专家名称");
			return;
		}
		$.ajax({
			type: "POST",
			url: '/platform/cmsExpert/save',
			contentType: "application/json",
			data: JSON.stringify({name: tempName}),
			dataType : "json",
			beforeSend: function (){
				uploadShade("数据提交中...");
			},
			complete: function() {
				disShade();
			},
			success: function(data){
				$("#expert").val(data.expertId);
				$('#from').textbox('setValue', tempName);
			}
		});
	}
	$('#authorDialog').dialog('close');
	load();
}

function tagSave() {
	var chk_value =[];
	var chk_name =[];
	$('input[name="tag"]:checked').each(function(){
		chk_value.push($(this).val());
		chk_name.push($(this).next().text());
	});
	$('#tagId').val(chk_value.join(","));
	$('#tag').textbox('setValue', chk_name.join(","));
	$('#tagDialog').dialog('close');
}

function themeSave() {
	var chk_value =[];
	var chk_name =[];
	$('input[name="theme"]:checked').each(function(){
		chk_value.push($(this).val());
		chk_name.push($(this).next().text());
	});
	$('#themeId').val(chk_value.join(","));
	$('#themeName').textbox('setValue', chk_name.join(","));
	$('#themeDialog').dialog('close');
}


function save(){
	var date = {
		title: $("#title").textbox('getValue'),
		subheading: $("#subheading").textbox('getValue'),
		type: $("#type").combotree('getValue'),
		from: $("#from").textbox('getValue'),
		expert: $("#expert").val(),
		tag: $("#tag").textbox('getValue'),
		tagId: $("#tagId").val(),
		theme: $("#themeId").val(),
		typePath: $("#typePath").val(),
		content: um.getContent(),
		coverResources: {
			id: $("#coverId").val(),
			name: $("#coverName").val(),
			path: $("#coverPath").val()
		},
		accessoryResources: {
			id: $("#accessoryId").val(),
			name: $("#accessoryName").val(),
			path: $("#accessoryPath").val()
		}
	};
	// 判断修改或者新增
	$("#id").textbox('getValue') == 0 ? 1 : date['id'] = $("#id").textbox('getValue');
	if (!$("#form").form('validate')) {
		return ;
	}
	if (!um.hasContents() || $.trim(um.getContent()).length == 0) {
		um.focus();
		alert('"内容"项为必填项！');
		return ;
	}
	$.ajax({
		type: "POST",
		url: '/platform/cms/save?_' + $.now(),
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
				$.messager.alert('提示','保存成功','info');
				// reloadTree();
				list();
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('提示','保存失败','error');
			}
        }
	});
}

function edit(index, option){
	option == 1 ? setAble(true) : 1;
	var rows = $('#datagrid').datagrid('getRows');
	var temp = rows[index];
	$('#form').form('clear');
	$("#coverName").val('');
	$("#coverPath").val('');
	$("#coverURL").attr('src', '/statics/images/addimg.png');
	$("#accessoryName").val('');
	$("#accessoryPath").val('');
	$("#accessoryFile").empty();
	$('#form').form('load', temp);
	$('#from').textbox('setValue', temp.from);
	um.setContent(temp.content, false);
	if (temp.coverUrl != '') {
		$.ajax({
			type: "POST",
			url: '/resources/selectById',
			contentType: "application/json",
			data: JSON.stringify({id: temp.coverUrl}),
			dataType : "json",
			success: function(data){
				$("#coverName").val(data.resource.name);
				$("#coverPath").val(data.resource.path);
				$("#coverURL").attr('src', data.resource.path);
			}
		});
	}
	if (temp.accessory != '') {
		$.ajax({
			type: "POST",
			url: '/resources/selectById',
			contentType: "application/json",
			data: JSON.stringify({id: temp.accessory}),
			dataType : "json",
			success: function(data){
				$("#accessoryFile").empty();
				$("#accessoryName").val(data.resource.name);
				$("#accessoryPath").val(data.resource.path);
				$("#accessoryFile").append("<a href='/resources/" + data.resource.id + "' target='_blank'>" + data.resource.name + "</a>");
			}
		});
	}
	
	$.ajax({
		method: 'POST',
	    url: '/platform/cms/getTagAndThemeList?_' + $.now(),
	    contentType: "application/json",
	    data: JSON.stringify({id: temp.id}),
	    dataType: 'json',
	    success: function(data) {
	      	var temp = data.tags;
	      	var themeTemp = data.themes;
	      	$("#tagTable").empty();
	      	$("#themeTable").empty();
	      	var tempStr = '<tr>';
	      	var themeTempStr = '<tr>';
	      	var themeStr = [];
	      	for (var i in temp) {
	      		if ($.inArray(temp[i].constantValue, $('#tag').textbox('getValue').split(",")) > -1) {
	      			tempStr += '<td class="tagTable_input"><input value="' + temp[i].constantId + '" type="checkbox" checked="checked" name="tag" /><span>' + temp[i].constantValue + '</span></td>';
				} else {
					tempStr += '<td><input value="' + temp[i].constantId + '" type="checkbox" name="tag" /><span>' + temp[i].constantValue + '</span></td>';
				}
	      		if (i % 3 == 2) {
	      			tempStr += "</tr><tr>";
				}
			}
	      	for (var i in themeTemp) {
	      		if ($.inArray(themeTemp[i].constantId.toString(), $('#themeId').val().split(",")) > -1) {
	      			themeTempStr += '<td class="tagTable_input"><input value="' + themeTemp[i].constantId + '" type="checkbox" checked="checked" name="theme" /><span>' + themeTemp[i].constantValue + '</span></td>';
	      			themeStr.push(themeTemp[i].constantValue);
	      		} else {
					themeTempStr += '<td><input value="' + themeTemp[i].constantId + '" type="checkbox" name="theme" /><span>' + themeTemp[i].constantValue + '</span></td>';
				}
	      		if (i % 3 == 2) {
	      			themeTempStr += "</tr><tr>";
				}
			}
	      	$("#tagTable").append(tempStr);
	      	$("#themeTable").append(themeTempStr);
	      	$("#themeName").textbox("setValue", themeStr.join(","));
	    },
	    error: function() {
	        alert('error');
	    }
	});
	
	$('#dialog').dialog({
		title: option == 1 ? '内容修改' : '查看',
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
					console.debug(data);
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

function setTop(id, option){
	var temp = $.fn.zTree.getZTreeObj("treeDemo");
	var type = temp.getSelectedNodes()[0].id;
	$.ajax({
		type: "POST",
		url: '/platform/cms/setTop?_' + $.now(),
		contentType: "application/json",
		data: JSON.stringify({id: id, type: type, position: option}),
		dataType: 'json',
		beforeSend: function (){
			load();
			uploadShade("数据设置中...");
		},
		complete: function() {
			disLoad();
			disShade();
		},
		success: function (data) {
			if (data.code == 0) {
				console.debug(option);
				if (option != 3) {
					$.messager.alert('提示', '设置成功', 'info');
				} else {
					$.messager.alert('提示', '<p style="line-height: 20px;">设置成功<br>(置顶超过3条,将不生效)</p>', 'info');
				}
				list();
			} else {
				$.messager.alert('提示', '设置失败', 'error');
			}
        }
	});
}

$(window.document).on("click",".this_tabs td input",function(){
	if($(this).parent().hasClass("tagTable_input")){
		if($(this).parent().find("span").html()=="其他"){
			$(this).parent().not(".tagTable_input").removeClass("tagTable_input").css("border","1px solid #dddddd");
		}else{
			$(this).parent().removeClass("tagTable_input").css("border","1px solid #dddddd");
		}
	}else{
		if($(this).parent().find("span").html()=="其他"){
			$("#authorTable").find("td").removeClass("tagTable_input").css("border","1px solid #dddddd");
			$(this).parent().next("td").find("input").removeAttr("disabled");
			$("#this_but1").show().css("display","inline-block");
		}
			$(this).parent().addClass("tagTable_input").css("border","1px solid #1da028");
	}
})