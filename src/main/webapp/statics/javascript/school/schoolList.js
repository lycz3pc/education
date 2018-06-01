		var id ;
		var imgPath1;
		var imgPath2;
		var imgPath3;
		var imgPaht4;
		var us_kerdown=function (){
			schoolCriteria();
		};
		$(function(){
			$("#dialog").dialog("close");
			$("#digo_code_url").dialog("close");
			limitLength();
			addressInit('seachprov', 'seachcity', 'seachdistrict');
			init();
			//显示学校上传图片
			$("#schPhoto").filebox({
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
			        	$("#schPhoto").filebox({
			        		required : true
			        	});
			        }
			    }
			});
			
			//显示学校上传logo
			$("#schLogo").filebox({
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
			                $("#img2").attr("src", this.result);
			            }
			            // 读取指定文件并形成URL
			            reader.readAsDataURL(file);
			        }else{
			        	$("#img2").attr("src","/statics/images/addimg.png");
			        	$("#schLogo").filebox({
			        		required : true
			        	});
			        }
			    }
			});
			
			
			//显示头部上传图片
			$("#headBg").filebox({
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
			                $("#img3").attr("src", this.result);
			            }
			            // 读取指定文件并形成URL
			            reader.readAsDataURL(file);
			        }else{
			        	$("#img3").attr("src","/statics/images/addimg.png");
			        	$("#headBg").filebox({
			        		required : true
			        	});
			        }
			    }
			});
			
			//显示尾部上传图片
			$("#footBg").filebox({
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
			                $("#img4").attr("src", this.result);
			            }
			            // 读取指定文件并形成URL
			            reader.readAsDataURL(file);
			        }else{
			        	$("#img4").attr("src","/statics/images/addimg.png");
			        	$("#footBg").filebox({
			        		required : true
			        	});
			        }
			    }
			});   
			
			
			  $('#colorSelector').ColorPicker({
		            color: '#0000ff',
		            onShow: function (colpkr) {
		                $(colpkr).fadeIn(500);
		                return false;
		            },
		            onHide: function (colpkr) {
		                $(colpkr).fadeOut(500);
		                return false;
		            },
		            onChange: function (hsb, hex, rgb) {
		                $('#colorSelector div').css('backgroundColor', '#' + hex);
		            }
		        });
		})
		
		
		function init(){
			$("#dg").datagrid({
				rownumbers:true,
				title: '联盟校园列表',
				singleSelect:true,
				fitColumns:true,
				pagination:true,
				striped:true,
				url:'/platform/school/schoolList',
				method:'post',
				toolbar: '#tb',
				columns:[[
							{field:'schName',title:'学校名称',width:'20%'},    
							{field:'provinceText',title:'省',width:'5%'},
							{field:'cityText',title:'市',width:'5%'},
							{field:'areaText',title:'区',width:'5%'},
							{field:'preName',title:'校长',width:'10%'},
							{field:'status',title:'状态',width:'10%',formatter:formatStatus},
							{field:'createTime',title:'加入联盟时间',width:'10%'},
							{field:'updateTime',title:'最后一次修改时间',width:'10%'},
							{field:'opt',title:'操作',width:'25%',align:'center',formatter:formatOper}
				]],
				/* queryParams:{
					location:$("#location").val()
				},
				*/
				onLoadSuccess:function(data){
					$(data.rows).each(function(i,item){
						if(item.isTop == 0){
							$("a[name='isTop']").linkbutton();
							$("a[name='create']").linkbutton();
					        $("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();   
						}else{
							$("a[name='create']").linkbutton();
							$("a[name='update']").linkbutton();
					        $("a[name='delete']").linkbutton();   
						}
						$("a[name='create']").linkbutton();
						$("a[name='check']").linkbutton();
					});
				},
				onDblClickCell:function(index,field,value){
					//addressInit('seachprov2', 'seachcity2', 'seachdistrict2');
					$('#dg').datagrid('selectRow',index);// 关键在这里
					var row = $('#dg').datagrid('getSelected');
					showDialog('查看联盟校园');
					$('#schoolForm').form('load', row);
					showImgs(row.resourceStr);
					$('#img1').attr('src',imgPath1);
					//$('#img1').show();
					$('#img2').attr('src',imgPath2);
					//$('#img2').show();
					$('#img3').attr('src',imgPath3);
					//$('#img3').show();
					$('#colorSelector div').css('backgroundColor', row.themeColor);
					$("#seachprov2 option[value='"+row.province+"'] ").attr("selected",true);
					$("#seachprov2").change();
					$("#seachcity2 option[value="+row.city+"] ").attr("selected",true);
					$("#seachcity2").change();
					$("#seachdistrict2 option[value="+row.area+"] ").attr("selected",true);
					//alert(5555);
					requiredFiled();
					$(".this_textarea input").attr("disabled","disabled");
				}
			});
		}
		
		function showDialog(t){
			$('#schPhoto').filebox({  
				width : '300px',  
				multiple : true,  
				validType : ['fileSize[3,"MB"]' ],  
				buttonText : '请选择',  
				buttonAlign : 'right',  
				prompt : '请选择一个图片类型的文件',  
				accept : [ 'image/jpg', 'image/jpeg', 'image/png' ]  
			});  
			$('#schLogo').filebox({  
				width : '300px',  
				multiple : true,  
				validType : ['fileSize[3,"MB"]' ],  
				buttonText : '请选择',  
				buttonAlign : 'right',  
				prompt : '请选择一个图片类型的文件',  
				accept : [ 'image/jpg', 'image/jpeg', 'image/png' ]  
			});  
			$('#headBg').filebox({  
				width : '300px',  
				multiple : true,  
				validType : ['fileSize[3,"MB"]' ],  
				buttonText : '请选择',  
				buttonAlign : 'right',  
				prompt : '请选择一个图片类型的文件',  
				accept : [ 'image/jpg', 'image/jpeg', 'image/png' ]  
			}); 
			$('#footBg').filebox({  
				width : '300px',  
				multiple : true,  
				validType : ['fileSize[3,"MB"]' ],  
				buttonText : '请选择',  
				buttonAlign : 'right',  
				prompt : '请选择一个图片类型的文件',  
				accept : [ 'image/jpg', 'image/jpeg', 'image/png' ]  
			}); 
			//清空dialog
			$('#schoolForm').form('clear');			
			initImg();
			addressInit('seachprov2', 'seachcity2', 'seachdistrict2','0','0','0');
			if(t == '新增联盟校园'){
				//设置是否禁用表单控件
				isfobidden(false);
				validateFile(true);
				requiredFiled();
				/*$(".this_textarea input").attr("disabled",false);*/
				/*$(".this_table tr").eq(3).find(".this_textarea input").on("blur",function(){
					$(this).attr("disabled",false);	
				})*/
				$('#status').combobox('setValue', 0);
				$(".this_table tr td .this_but2").text("清空");
				$(".this_table tr td .this_but1").show();
				$(".this_table tr td .this_but2").show();
			}
			else if(t == '编辑联盟校园'){
				//设置是否禁用表单控件
				isfobidden(false);
				validateFile(false);
				requiredFiled();
				/*$(".this_textarea input").on("blur",function(){
					$(this).attr("disabled",false);	
				})*/
				$(".this_table tr td .this_but2").text("重置");
				$(".this_table tr td .this_but1").show();
				$(".this_table tr td .this_but2").show();
			}else if(t == '查看联盟校园'){
				//设置是否禁用表单控件
				isfobidden(true);
				$(".this_table tr td .this_but1").hide();
				$(".this_table tr td .this_but2").hide();
			}
			var str=t+"";
			//addressInit('seachprov2', 'seachcity2', 'seachdistrict2');
			$('#dd').dialog({
				  title : str ,
				  top: 80,
				  left: 350,
				  resizable:false,
				  draggable:false,
				  modal:true
			});
			$('#dd').window('center');
		}
		
		
	   function formatOper(val,row,index){
	  		 var str='';
	  		 if(row.status == 0){
	  			 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_blue" name="check"  onclick="check('+index+')">审核</a>';
	  		 }else{
	  			 if(row.isTop == 0){
	  				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_violet" name="isTop"  onclick="isTop('+index+')">置顶</a>';
	  			 }else{
	  				 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_gray" name="isTop"  onclick="isTop('+index+')">取消置顶</a>';
	  			 }
	  			 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_green" name="create"  onclick="createStation(' + row.id + ')">';
	  			 // 区分是否已生成子站
	  			 if (row.stationStatus) {
	  				str += '重新';
	  			 }
	  			 str += '生成</a>';
	  		 }
	  		 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="editUser('+index+')">修改</a>';
	  		 
	  		 var qrCodeUrl = row.qrCodeUrl;
	  		 if(qrCodeUrl=='' || qrCodeUrl==null){
	  			str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="genderQrCode"  onclick="genderQrCode('+index+')">生成二维码</a>';
	  		 }else{
	  			str += "<a href='javascript:void(0)' class='easyui-linkbutton this_but_black' name='showQrCode'  onclick=\"showQrCode('"+qrCodeUrl+"')\">查看二维码</a>";
	  		 }
	  		 str += '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="deleteUser('+index+')">删除</a>';
			 return str;
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
								url:"/platform/school/check",
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
	   
	   //置顶联盟校园
	   function isTop(index) {
		   $('#dg').datagrid('selectRow',index);// 关键在这里
		   var row = $('#dg').datagrid('getSelected');
		   if(row){
			   $.ajax({
					url:"/platform/school/isTop",
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
	   
	   //修改联盟校园
	   function editUser(index){
			$('#dg').datagrid('selectRow',index);// 关键在这里
			var row = $('#dg').datagrid('getSelected');
			if (row){
				showDialog('编辑联盟校园');
				addressInit('seachprov2', 'seachcity2', 'seachdistrict2',row.province,row.city,row.area);
				$('#schoolId').val(row.id);
				$('#resourceStr').val(row.resourceStr);
				$('#schName').textbox('setValue',row.schName);
				$('#address').textbox('setValue',row.address);
				$('#unitSitPhone').val(row.unitSitPhone);
				$('#unitTelPhone').val(row.unitTelPhone);
				$('#infreceiman').textbox('setValue',row.infreceiman);
				$('#infreceiSitP').val(row.infreceiSitP);
				$('#infreceiphone').val(row.infreceiphone);
				$('#preSitPho').val(row.preSitPho);
				$('#prePhone').val(row.prePhone);
				showImgs(row.resourceStr);
				$('#img1').attr('src',imgPath1);
				//$('#img1').show();
				$('#schDescript').textbox('setValue',row.schDescript);
				$('#preName').textbox('setValue',row.preName);
				$('#preSaying').textbox('setValue',row.preSaying);
				$('#img2').attr('src',imgPath2);
			//$('#img2').show();
				$('#img3').attr('src',imgPath3);
				//$('#img3').show();
				$('#colorSelector div').css('backgroundColor', row.themeColor);
				$("input:radio[value='"+row.themeColor+"']").attr('checked','true');
				/*$("#seachprov2 option[value='"+row.province+"'] ").attr("selected",true);
				$("#seachprov2").change();
				$("#seachcity2 option[value="+row.city+"] ").attr("selected",true);
				$("#seachcity2").change();
				$("#seachdistrict2 option[value="+row.area+"] ").attr("selected",true);*/
				$('#position').textbox('setValue',row.position); 
				$('#status').combobox('setValue', row.status);
				requiredFiled();
				if(row.status==1){
					$('#status').combobox('disable'); 
				}else{
					$('#status').combobox('enable'); 
				}
				
				id = row.id;
			}
		}
	   
	   //删除联盟校园
	   function deleteUser(index){
		   $('#dg').datagrid('selectRow',index);// 关键在这里
		   var row = $('#dg').datagrid('getSelected');
		   if(row){
			   $("#tip").text("该操作将永久删除，确认删除吗？");
			   $("#tip").dialog({
					title:'确认删除',
					buttons:[{
						text:'确认',
						iconCls:'icon-ok',
						handler:function(){
							$.ajax({
								url:"/platform/school/delete",
								type:'post',
								dataType:'json',
								data:{
									ids:row.id
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
	   
	   //生成二维码
	   function genderQrCode(index){
		   $('#dg').datagrid('selectRow',index);
		   var row = $('#dg').datagrid('getSelected');
		   if(row){
			   $("#tip").text("二维码生成后不可更改，确认生成吗？");
			   $("#tip").dialog({
					title:'确认生成',
					buttons:[{
						text:'确认',
						iconCls:'icon-ok',
						handler:function(){
							$.ajax({
								url:"/platform/school/genderQrcode",
								type:'post',
								dataType:'json',
								data:{
									id:row.id
								},
								success:function(data){
									if(data.type=="success"){
										$('#tip').dialog('close');
										$("#img_code_url").attr("src",data.qrcode);
										$("#digo_code_url").dialog("open");
										
										$("#tip").dialog("close");
										$("#dg").datagrid("reload");
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
		
		//保存联盟校园
		function doSaveSchool(){
			//alert();
			$('#colors').val($('#colorSelector div').css('backgroundColor'));
			$('#provText2').val($('#seachprov2').find("option:selected").text());
			$('#cityText2').val($('#seachcity2').find("option:selected").text());
			$('#areaText2').val($('#seachdistrict2').find("option:selected").text());
			$('#schoolForm').form('submit', {
			    url:'/platform/school/save',
			    onSubmit: function(){
			    	if(($.trim($('#schName').textbox('getValue')) == '') || ($.trim($('#schDescript').textbox('getValue')) == '') || ($.trim($('#preName').textbox('getValue')) == '') || ($.trim($('#preSaying').textbox('getValue')) == '') ){
			    		$.messager.alert('提示','请填写所有必填项','error');
			    		return false;
			    	}
			    	if($('#seachprov2').val() == '0'){
			    		$.messager.alert('提示','请选择省市区','error');
			    		return false;
			    	}
			    	if($('#schoolId').val() == ''){
			    		if($('#schPhoto').filebox('getValue') == ''){
			                return false;
				    	}
				    	if($('#schLogo').filebox('getValue') == ''){
			                return false;
				    	}
			    	}
			    	if($("input[name=files]" )[0].files.length>0){
			    		var files = $("input[name=files]" )[0].files;
			    		$(files).each(function(i,item){
			    			if(item.size > 3 * 1024 * 1024 ){
			    				$.messager.alert('提示','单个文件不能大于3M','info');
				                return false;
			    			}
			    		})
			    	}
			    	if($("#schoolForm").form('validate') == false){
						 return false;
					 }
			    	$('.this_table_center .this_but1').attr('onclick','javascript:void();');
		    	},
			    success:function(data){
			    	var data = eval('(' + data + ')');
			    	if(data.type =="success"){
						$('#dd').dialog('close');
						$("#dg").datagrid("reload");
						$.messager.alert('提示','保存成功','info');
					}else {
						$.messager.alert('提示','保存失败','error');
					}
			    	$('.this_table_center .this_but1').attr('onclick','doSaveSchool();');
			    }
			});
		}
		
		//根据条件查询
		function schoolCriteria(){
			$('#dg').datagrid('load',{
				province:$('select[name="province"]').val(),
				city:$('select[name="city"]').val(),
				area:$('select[name="area"]').val(),
				schName:$.trim($("#querySch").textbox('getValue'))
			});
		}
		
		//查询资源表中所有图片
		function showImgs(resourceStr){
			$.ajax({
				url:"/platform/school/queryImgs",
				type:'post',
				dataType:'json',
				async: false,
				data:{
					resourceStr:resourceStr
				},
				success:function(data){
					var imgList = data.imgList;
					if(imgList != ''){
						$.each(imgList,function(index,item){
							if(item != null){
								if(item.path != null){
									$('#img' + (index+1)).attr('src',item.path);
								}
							}
							
						})
					}
				}
			});
		}
		
		function clearForm(){
			if($('#schoolId').val() == ''){
				//新增时清空数据
				$('#schoolForm').form('clear');
				initImg();
				addressInit('seachprov2', 'seachcity2', 'seachdistrict2');
			}else{
				//更新时重置数据
				$('#schoolForm').form('clear');
				var row = $("#dg").datagrid("getSelected");
				$('#schoolForm').form('load', row);
				$("#seachprov2 option[value='"+row.province+"'] ").attr("selected",true);
				$("#seachprov2").change();
				$("#seachcity2 option[value="+row.city+"] ").attr("selected",true);
				$("#seachcity2").change();
				$("#seachdistrict2 option[value="+row.area+"] ").attr("selected",true);
				showImgs(row.resourceStr);
				$('#img1').attr('src',imgPath1);
				$('#img2').attr('src',imgPath2);
				$('#img3').attr('src',imgPath3);
			}
		}
		
		//表单控件是否禁用
		function isfobidden(bool){
			if(bool){
				$('#dd input').attr("disabled","disabled");
				$("#dd textarea").attr("disabled","disabled");
				$('#dd select').attr("disabled","disabled");
				
				
				//alert(1)
				//$('#unitSitPhone').validatebox("disableValidation");
				
				//$(".easyui-validatebox").attr("readonly",true);
				//$('#unitSitPhone').attr("readonly",true);
				//$('#unitSitPhone').validatebox('destroy');
				//$('#unitSitPhone').validatebox("disableValidation");
				//$("#schoolForm :input").attr("readonly", "readonly");
				//取消颜色选择器点击事件
				//$('#colorSelector div').removeAttr('backgroundColor');
			}else{
				$("#dd input").removeAttr("disabled");
				$("#dd textarea").removeAttr("disabled");
				$("#dd select").removeAttr("disabled");
			}
		}
		
		//初始化预览图片
		function initImg(){
			$("#img1").attr("src","/statics/images/addimg.png");
			$("#img2").attr("src","/statics/images/addimg.png");
			$("#img3").attr("src","/statics/images/addimg.png");
			$("#img4").attr("src","/statics/images/addimg.png");
		}
		
		//限制输入长度
		function limitLength(){
			//限制长度输入
			$('#schName').textbox('textbox').attr('maxlength',60);
			$('#schDescript').textbox('textbox').attr('maxlength',500);
			$('#preName').textbox('textbox').attr('maxlength',60);
			$('#preSaying').textbox('textbox').attr('maxlength',200);
		}

// 生成子站
function createStation(id){
	$('.this_buttton').show();
	$('#this_buttton').html('取消');
	$("#schoolStationId").val(id);
	$.ajax({
		type: 'POST',
		url: '/statics/json/school/schoolColumn.json',
		dataType: 'json',
		async: false,
		beforeSend: function (){
			// 当第一次进入该方法，#schoolColumns下无栏目元素，请求json文件创建复选框
			// 否则不执行Ajax
			return $(".schoolColumn").length == 0;
		},
		success: function(data){
			for (var i = 0; i < data.length; i++) {
				$('#schoolColumns').append('<input value="' + data[i].id + '" type="checkbox" name="schoolColumn" class="schoolColumn" data-column="' + data[i].id + '~~' + data[i].text + '~~' + data[i].column + '"><span class="schoolColumn">' + data[i].text + '</span>');
				if (i % 3 == 2) {
					$('#schoolColumns').append('<br class="schoolColumn">');
				}
			}
		}
	});
	// 根据id查找该校级子站的栏目，并勾选
	$.ajax({
		type: 'POST',
		url: '/platform/schoolColumn/list',
		contentType: "application/json",
		data: JSON.stringify({schoolId: id}),
		dataType: 'json',
		success: function(data){
			var temp = [];
			for (var i in data.schoolColumns) {
				temp.push(data.schoolColumns[i].columnId);
			}
			var check = $("input[name='schoolColumn']");
			for(var i = 0;i < check.length;i++){
				check[i].checked = false;
				if($.inArray(parseInt(check[i].value), temp) > -1){
					check[i].checked = true;
				}
			}
		}
	});
	$("#dialog").dialog("open");
}

function updateStation() {
	var num = 0;;
	var temp = {schoolColumns: []};
	var check = $("input[name='schoolColumn']");
	for(var i = 0;i < check.length;i++){
		if (check[i].checked) {
			var schoolColumns = {};
			$("input[name='schoolColumn']").attr("data-column", function(index, value){
				if (i == index) {
					schoolColumns = {
						schoolId: $("#schoolStationId").val(),
						columnId: value.split("~~")[0],
						columnText: value.split("~~")[1],
						columnUrl: value.split("~~")[2]}
				}
			});
			num++;
			temp.schoolColumns.push(schoolColumns);
		}
	}
	if (num == 0) {
		return ;
	}
	$.ajax({
		type: 'POST',
		url: '/platform/school/createStation',
		contentType: "application/json",
		data: JSON.stringify(temp),
		dataType: 'json',
		success: function(data){
			if (data.code == 0) {
				$("#dialog").dialog("close");
				$.messager.alert('提示','生成成功','info');
			} else {
				$("#dialog").dialog("close");
				$.messager.alert('提示','生成失败','error');
			}
		}
	});
}

/*设置字段必填*/
function requiredFiled(){
	$('#unitSitPhone').validatebox({ 
		required:true 
	});
	$('#unitTelPhone').validatebox({ 
		required:true 
	});
	$('#preSitPho').validatebox({ 
		required:true 
	});
	$('#prePhone').validatebox({ 
		required:true 
	});
	$('#infreceiSitP').validatebox({ 
		required:true 
	});
	$('#infreceiphone').validatebox({ 
		required:true 
	});
	$('#infreceiman').validatebox({ 
		required:true 
	});
}

//验证文件
function validateFile(bool){
	$("#schPhoto").filebox({
		required : bool
	});
	$("#schLogo").filebox({
		required : bool
	});
}

function formatStatus(val,row){
	if(val == 0){
		return '待审核';
	}else{
		return '已通过';
	}
}


function showQrCode(codeUrl){
   $("#img_code_url").attr("src",codeUrl);
   $("#digo_code_url").dialog("open");
}
//

		