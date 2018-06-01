var us_kerdown=function (){
		query();
	};
	$(function(){
	
		//显示专家上传图片
		$("#expertPho").filebox({
		    onChange: function (event) {

		        // 获取所选文件的File对象
		        var file = $(this).filebox("getFile");

		        if (file != null) {
		            // 创建FileReader对象
		            var reader = new window.FileReader();
		            // 定义reader的onload事件
		            // 当读完文件信息后触发onload事件
		            reader.onload = function (e) {
		                // reader.result保存着产生的虚拟URL
		                $("#img1").attr("src", this.result);
		            }
		            // 读取指定文件并形成URL
		            reader.readAsDataURL(file);
		        }else{
		        	$("#img1").attr("src","/statics/images/addimg.png");
		        }
		    }
		});
		
		
		$('#expertPho').filebox({  
			width : '300px',  
			multiple : true,  
			validType : ['fileSize[3,"MB"]' ],  
			buttonText : '请选择',  
			buttonAlign : 'right',  
			prompt : '请选择一个图片类型的文件',  
			accept : [ 'image/jpg', 'image/jpeg', 'image/png' ]  
		});  
		
	/* 	$.extend($.fn.validatebox.defaults.rules, {
			sortExist: {
				validator: function(value,param){
					var text =$.ajax({
						url:"/platform/expert/validateSort",
						method:"post",
						data:{
							sort:value,
							id:$(param[0]).val() 
						},
						async:false,
						cache:false,
					}).responseText;
					var dataObj = eval("("+text+")");//转换为json对象  
					return dataObj.type=="success";
				},
				message: '该专家排序已存在！'
			}
		}); */
		
		/* $('#email').textbox({
			validType:"checkTextbox['#eid']"
		});  */
		limitLength();
		init();
		
		//获取主题
		$.ajax({
		    type: "POST",
            url: "/platform/constant/getTopicData",
            data:{categoryId : "101"},
            dataType: "json",
            async: "false",
            success: function(data){
            	var topic_arry = data.topic;
            	//alert(topic_arry.length);
            	$.each(topic_arry,function(item){
            		$('#type').append("<option value='"+topic_arry[item].constantId+"'>"+topic_arry[item].constantValue+"</option>");
            	})
            	/* for(var item in topic_arry){topic_arry[item].constantId
            		alert(topic_arry[item].constantValue);
            		$('#type').append("<option value='"+topic_arry[item].constantValue+"'>"+topic_arry[item].constantValue+"</option>")
            	} */
            }
		})
		
		//获取学段
		$.ajax({
		    type: "POST",
            url: "/platform/constant/getSectionData",
            dataType: "json",
            async: "false",
            success: function(data){
            	var section_arry = data.section;
            	$.each(section_arry,function(index,item){
            		$('#section').append("<option value='"+item.id+"'>"+item.constant_value+"</option>")
            		}
            	)
            }
		})
		
	})
	
	function init(){
		
		$("#dg").datagrid({
			rownumbers:true,
			title: '专家列表',
			singleSelect:true,
			fitColumns:true,
			pagination:true,
			striped:true,
			url:'/platform/expert/list',
			method:'post',
			toolbar: '#tb',
			columns:[[
						{field:'name',title:'专家名称',width:'10%'},    
						{field:'topic',title:'主题',width:'5%'},    
						{field:'email',title:'专家邮箱',width:'17%'},
						{field:'achievement',title:'职务',width:'20%'},
						{field:'status',title:'状态',width:'6%',formatter:formatStatus},
						{field:'unit',title:'单位',width:'10%'},
						{field:'createtime',title:'创建时间',width:'11%'},
						{field:'opt',title:'操作',width:'20%',align:'center',formatter:formatOpt}
			]],
			/* queryParams:{
				location:$("#location").val()
			},
			*/
			onLoadSuccess:function(data){
				$(data.rows).each(function(i,item){
					if(item.isTop == 0 ){
						$("a[name='isTop']").linkbutton();
						if(item.status == 0){
							$("a[name='check']").linkbutton();
					        $("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();    					       
						}else{
							$("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();   
						}
					}else{
						$("a[name='isTop']").linkbutton();
						if(item.status == 0){
							$("a[name='check']").linkbutton();
					        $("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();   
						}else{
							$("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();   
						}
					}
				});
		        
			},
			onDblClickCell:function(index,field,value){
				$('#dg').datagrid('selectRow',index);// 关键在这里
				var row = $('#dg').datagrid('getSelected');
				$('#add').dialog({
					  title : '查看' ,
					  resizable:false,
					  draggable:false,
					  modal: true
				});
				isfobidden(true);
				
				UM.getEditor('textarea1').setContent(row.teachSitu, false);
				UM.getEditor('textarea2').setContent(row.scificRes, false);
				UM.getEditor('textarea3').setContent(row.scificAwards, false);
				$(".this_table tr td .this_but1").hide();
				$(".this_table tr td .this_but2").hide();
				$('#ff').form('load', row);
				showImgs(row.expertImg);
			}
		});
	}
	
	//格式化操作列
	function formatOpt(value, row, index){
		 var str='';
		 if(row.status == 0){
			 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_blue" name="check"  onclick="check('+index+')">审核</a>';
			 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="update('+index+')">修改</a>';
			 if(row.logicStatus == 1){
				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delExpert('+index+')">删除</a>';
			 }
		 }else{
			 if(row.isTop == 0){
				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_violet" name="isTop"  onclick="isTop('+index+')">置顶</a>';
				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="update('+index+')">修改</a>';
				 if(row.logicStatus == 1){
					 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delExpert('+index+')">删除</a>';
				 }
			 }else{
				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray" name="isTop"  onclick="isTop('+index+')">取消置顶</a>';
				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="update('+index+')">修改</a>';
				 if(row.logicStatus == 1){
					 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delExpert('+index+')">删除</a>';
				 }
			 }
		 }
		 return str;
	}
	
	function resetPassword(){
		
	}
	
	 //置顶专家
	 function isTop(index) {
		   $('#dg').datagrid('selectRow',index);// 关键在这里
		   var row = $('#dg').datagrid('getSelected');
		   if(row){
			   $.ajax({
					url:"/platform/expert/isTop",
					type:'post',
					dataType:'json',
					data:{
						id:row.id
					},
					success:function(data){
						if(data.type=="success"){
							$.messager.alert('提示','操作成功','info');
							$("#dg").datagrid("reload");
						}
					}
				});
		   }
	   }  
	
	//审核专家
	function check(index){
		$("#dg").datagrid("selectRow", index);
		var row = $("#dg").datagrid("getSelected");
		if(row){
			$("#tip").text("确认审核通过吗？");
			$("#tip").dialog({
				title:'确认通过',
				buttons:[{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						$.ajax({
							url:"/platform/expert/check",
							type:'post',
							dataType:'json',
							data:{
								id:row.id
							},
							success:function(data){
								if(data.type=="success"){
									$("#tip").dialog("close");
									$("#dg").datagrid("reload");
									$.messager.alert('提示','审核成功','info');
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
	}
	
	//删除专家
	function delExpert(index){
		$("#dg").datagrid("selectRow", index);
		var row = $("#dg").datagrid("getSelected");
		if(row){
			$("#tip").text("该操作将永久删除，确认删除吗？");
			$("#tip").dialog({
				title:'确认删除',
				buttons:[{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						$.ajax({
							url:"/platform/expert/delete",
							type:'post',
							dataType:'json',
							data:{
								id:row.id
							},
							success:function(data){
								if(data.type=="success"){
									$("#tip").dialog("close");
									$("#dg").datagrid("reload");
									$.messager.alert('提示','删除成功','info');
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
	}
	
	//新增
	function append(){
		// 富文本初始化
		
		$('#img1').attr('src',"/statics/images/addimg.png");
		isfobidden(false);
		$("#add").dialog({
			title:'新增',
			resizable:false,
			draggable:false,
			modal: true
		});
		
		$('#ff').form('clear');
		$('#sex').val('1');
		$('#password').textbox('setValue','123456');
		validateFile(true);
		UM.getEditor('textarea1').setContent('', false);
		UM.getEditor('textarea2').setContent('', false);
		UM.getEditor('textarea3').setContent('', false);
	/* 	um1 = UM.getEditor('textarea1');
		um2 = UM.getEditor('textarea2');
		um3 = UM.getEditor('textarea3'); */
		$('#status').combobox('enable'); 
		$('#status').combobox('setValue', 0);
		$(".this_table tr td .this_but1").show();
		$(".this_table tr td .this_but2").text('清空');
		$(".this_table tr td .this_but2").show();
	}
	
	
	//修改
	function update(index){
		$('#ff').form('clear');
		//弹出框
		
		$("#add").dialog({
			title:'修改',
			resizable:false,
			draggable:false,
			modal: true
		});
		//$('#zjmm').css("display","none");
		validateFile(false);
		isfobidden(false);
	//	$('#password').textbox('setValue','');
		$('#password').textbox('disable');
		//$('#password').attr('readonly',true);
		$(".this_table tr td .this_but1").show();
		$(".this_table tr td .this_but2").show();
		$('#status').combobox('disable'); 
		$("#dg").datagrid("selectRow", index);
		var row = $("#dg").datagrid("getSelected");
		if(row){
			$('#ff').form('load', row);
		}
		
		$("#chargeRate").numberbox("setValue",parseFloat(row.chargeRate)*100);
	//	$("#chargeRate").val(parseFloat();
		UM.getEditor('textarea1').setContent(row.teachSitu, false);
		UM.getEditor('textarea2').setContent(row.scificRes, false);
		UM.getEditor('textarea3').setContent(row.scificAwards, false);
		showImgs(row.expertImg);
	}
	
	function submitForm(){
		$('#topicIds').val($('#type').find("option:selected").text());
		$('#sectionId').val($('#section').find("option:selected").text());
		var charge = $('#charge').numberbox('getValue');
		var chargeRate = $('#chargeRate').numberbox('getValue');
	    var topic = $('#type').val();
	    var section = $('#section').val();
		var eid= $('#eid').val();
		$('#ff').form('submit',{
			url:'/platform/expert/save',
			onSubmit: function(param){
				 if(topic == null){
					 $.messager.alert('提示','主题不能为空','info');
					 return false;
				 }
				 if(section == null){
					 $.messager.alert('提示','学段不能为空','info');
					 return false;
				 }
				 if(parseFloat(charge)*parseFloat(chargeRate)/100 < 0.01){
					 $.messager.alert('提示','围观支付额不能低于0.01元','info');
					 return false;
				 }
				 if($("#ff").form('validate') == false){
					 return false;
				 }
				// return $("#ff").form('validate');
				 $('.this_table tr td .this_but1').attr('onclick','javascript:void();');
		    },
			success:function(data){
				var data = eval('(' + data + ')');
				if(data.type=="success" ){
					$("#add").dialog("close");
					$("#dg").datagrid("reload");
					if(eid != ''){
						$.messager.alert('提示','操作成功','info');
					}else{
						$.messager.alert('提示','操作成功!</br>默认密码:123456','info');
						$(".messager-window .messager-body .messager-icon + div").css({"line-height":"24px","text-align":"left"});
					}
				}
				$('.this_table tr td .this_but1').attr('onclick','submitForm();');
			}
		});
	}
	
	function clearForm(){
		//更新时重置数据
		if($(".this_table tr td .this_but2").text() == '清空'){
			$('#ff').form('clear');
			$('#status').combobox('setValue','0');
		}else{
			var row = $("#dg").datagrid("getSelected");
			$('#ff').form('clear');
			$('#status').combobox('setValue','0');
			$('#ff').form('load', row);
		}
	}
	
	function formatStatus(val,row){
		if(val == 0){
			return '待审核';
		}else{
			return '已通过';
		}
	}
	
	function query(){
		$("#dg").datagrid("load",{
			name:$("#queryName").textbox('getValue')
		});
		//$("a[name='isTop']").linkbutton();
	}
	
	//限制输入长度
	function limitLength(){
		//限制长度输入
		$('#name').textbox('textbox').attr('maxlength',100);
		//$('#telephone').textbox('textbox').attr('maxlength',11);
		$('#password').textbox('textbox').attr('maxlength',64);
		//$('#email').textbox('textbox').attr('maxlength',100);
		//$('#synopsis').textbox('textbox').attr('maxlength',250);
		$('#achievement').textbox('textbox').attr('maxlength',250);
		$('#unit').textbox('textbox').attr('maxlength',250);
		//$('#sort').textbox('textbox').attr('maxlength',10);
	}
	
	//表单控件是否禁用
	function isfobidden(bool){
		if(bool){
			$('#ff :input').each(function(){//获取所有输入框遍历
				 try{
					 //放入try..catch中，放置不是easyui控件报错
					 //$(this).textbox('disable')//禁用
					  $(this).attr('readonly',true)//只读
					  $(this).filebox('disable');
				 }catch(e){
					 
				 }
			})
			//$("#type").attr("disabled","disabled");  
		    $("#ff select").attr("disabled","disabled");  
		   
			UM.getEditor('textarea1').setDisabled('fullscreen');
			UM.getEditor('textarea2').setDisabled('fullscreen');
			UM.getEditor('textarea3').setDisabled('fullscreen');
		}else{
			$('#ff :input').each(function(){//获取所有输入框遍历
				 try{
					 //放入try..catch中，放置不是easyui控件报错
				   $(this).removeAttr('readonly')//清除只读
				   $(this).attr("disabled", false);//可用
				   $(this).textbox('enable');
				   $(this).filebox('enable');
				 }catch(e){
					 
				 }
			})
		    //$('#zjmm').css("display","block");
			UM.getEditor('textarea1').setEnabled();
			UM.getEditor('textarea2').setEnabled();
			UM.getEditor('textarea3').setEnabled();
		}
	}

	//查询资源表中所有图片
	function showImgs(resourceStr){
		$.ajax({
			url:"/platform/expert/queryImgs",
			type:'post',
			dataType:'json',
			async: false,
			data:{
				resourceStr:resourceStr
			},
			success:function(data){
				$('#img1').attr('src',data.path);
			}
		});
	}
	
	//验证文件
	function validateFile(bool){
		$("#expertPho").filebox({
			required : bool
		});
	}