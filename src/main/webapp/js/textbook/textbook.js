// 富文本编辑器
var um;
var us_kerdown=function (){
	query();
};
	$(function(){

		$('#book').dialog('close');// 默认关闭弹窗
		$('#book_detail').dialog('close');// 默认关闭弹窗
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


		//图片回显
		$("#file").filebox({
			buttonText:'选择图片',
			accept : [ 'image/jpg', 'image/bmp', 'image/jpeg', 'image/gif', 'image/png' ],
			onChange: function (event) {// 获取所选文件的File对象
				var file = $(this).filebox("getFile");
				if (file != null) {
					if(file.size > 3 * 1024 * 1024){
						$.messager.alert('提示','不能超过3M','info');
						$(this).filebox("clear");
						return;
					}// 创建FileReader对象
					var reader = new window.FileReader();// 定义reader的onload事件
					reader.onload = function (e) {// 当读完文件信息后触发onload事件
						$("#coverimg").attr("src", this.result); // reader.result保存着产生的虚拟URL
						$("#coverimg").show();
					}
					reader.readAsDataURL(file); // 读取指定文件并形成URL
				}
				else{
		        	$("#coverimg").attr("src","/statics/images/addimg.png");
		        }
			}
		});


		// //选中一行查看详情
		$("#dg").datagrid({
			onDblClickRow : function(index, row){
				// 富文本初始化
				//um = UM.getEditor('give');
				//um = UM.getEditor('features');

				var textbook = getbook(row.id);
				$("#coverimgs").show();
				$("#booknames").val(textbook.name);
				$("#authors").val(textbook.author);
				//alert(textbook.topic);
				$("#topics").html('<option value="">'+textbook.topic+'</option>');
				$("#types").html('<option value="">'+textbook.typeText+'</option>');
				//defCheck(textbook.flagStr);
				$("#sections").html('<option value="">'+textbook.section+'</option>');
				$("#grades").html('<option value="">'+textbook.grade+'</option>');
				$("#prices").val(textbook.price);
				//$("#hours").val(textbook.hour);
				$("#urls").val(textbook.url);
				//$("#sequences").val(textbook.sequence);
				$("#coverimgs").attr("src",textbook.imgpath);
				/*UM.getEditor('gives').setContent(textbook.give, false);
				UM.getEditor('gives').setDisabled('fullscreen');*/
				$("#covers").val(textbook.coverimg);
				$("#gives").textbox("setValue", textbook.give);
				$("#recommends").textbox("setValue", textbook.recommend);
				setDisable();
				$('#book_detail').dialog({
					title: '详情'
				});
				$('#book_detail').dialog('open');
			}
		});

		limitLength();
		init(); //初始化
	})

function init() {
		//获取主题
		/*$.ajax({
		    type: "POST",
            url: "/platform/constant/getTopicData",
            data:{categoryId : "101"},
            dataType: "json",
            async: "false",
            success: function(data){
            	var topic_arry = data.topic;
            	for(var item in topic_arry){
            		$('#topic').append("<option value='"+topic_arry[item].constantValue+"'>"+topic_arry[item].constantValue+"</option>")
            	}
            }
		})*/
		//获取教材类型
		$.ajax({
		    type: "POST",
            url: "/platform/constant/getTextBookType",
            data:{categoryId : "100"},
            dataType: "json",
            async: "false",
            success: function(data){
            	var type_arry = data.type;
            	for(var item in type_arry){
            		$('#type').append("<option value='"+type_arry[item].constantId+"'>"+type_arry[item].constantValue+"</option>")
            	}
            }
		})
		
		$('#type').change(function(){
        		//$('#section').empty();
        		//typeText= $(this).find("option:selected").text();
				$('#section').empty();
				$('#grade').empty();
				//教材学段年级二级联动
				$.ajax({
				    type: "POST",
		            url: "/platform/constant/getSectionData",
		            dataType: "json",
		            async: "false",
		            success: function(data){
		            	var section_arry = data.section;
		            	$('#section').html("<option value=''>请选择</option>");
		            	for(var item in section_arry){
		            		if($('#type').find("option:selected").text() == '亲子阅读'){
		            			if(section_arry[item].constant_value == '小学' || section_arry[item].constant_value =='幼儿教育'){
		            				$('#section').append("<option value='"+section_arry[item].constant_value+"'>"+section_arry[item].constant_value+"</option>");
		            			}
		            		}else{
		            			$('#section').append("<option value='"+section_arry[item].constant_value+"'>"+section_arry[item].constant_value+"</option>");
		            		}
		            	}
		            }
				})
			
        	})
        	
    	$('#section').change(function(){
    		$('#grade').empty();
    		$.ajax({
			    type: "POST",
	            url: "/platform/constant/getSectionData",
	            dataType: "json",
	            async: false,
	            success: function(data){
	            	 var section_arry = data.section;
	            	 var now_item = $('#section').prop('selectedIndex')-1;
            		 $("#grade").html('<option value="">请选择</option>');
            		 if(now_item > -1){
            			 if($('#type').find("option:selected").text() == '亲子阅读'){
            				 if($('#section').find("option:selected").text() == '幼儿教育'){
                				 var arr  = section_arry[section_arry.length-1].chirdList;
                				 $.each(arr,function(index,item){
                					 console.log(item.constant_value);
                					 $("#grade").append('<option value="'+item.constant_value+'">'+item.constant_value+'</option>');
                				 })
            				 }else{
            					 $.each(section_arry[now_item].chirdList,function(index,item){
                					 $("#grade").append('<option value="'+item.constant_value+'">'+item.constant_value+'</option>');
                				 })
            				 }
            			 }else{
            				 var grade_arry = section_arry[now_item].chirdList;
            				 for(var k in grade_arry){
                    			 $("#grade").append('<option value="'+grade_arry[k].constant_value+'">'+grade_arry[k].constant_value+'</option>');
                    		 }
            			 }
            		 }
	            }
			})
			
    	})
    	
		//获取标签
		/*$.ajax({
		    type: "POST",
            url: "/platform/constant/getTopicData",
            data:{categoryId : "102"},
            dataType: "json",
            async: "false",
            success: function(data){
            	var topic_arry = data.topic;
            	for(var item in topic_arry){
            		$('#flagcheckbox').append('<input name="flag" type="checkbox" value="'+topic_arry[item].constantValue+'" /><span>'+topic_arry[item].constantValue+'</span>');
            		$('#flagcheckboxs').append('<input name="flag" type="checkbox" value="'+topic_arry[item].constantValue+'" /><span>'+topic_arry[item].constantValue+'</span>');
            	}
            }
		})*/
		
		
		//教材学段年级二级联动
		$.ajax({
		    type: "POST",
            url: "/platform/constant/getSectionData",
            dataType: "json",
            success: function(data){
            	var section_arry = data.section;
            	for(var item in section_arry){
        			$('#section').append("<option value='"+section_arry[item].constant_value+"'>"+section_arry[item].constant_value+"</option>")
            	}
            }
		})
		
	//表格
	$("#dg").datagrid({
		rownumbers: true,
		title: '教材列表',
		singleSelect: true,
		fitColumns: true,
		pagination: true,
		url: '/platform/textbook/index',
		method: 'post',
		toolbar: '#tb',
		queryParams: {
			name: $("#name").val()
		},
		columns: [[
			{field: 'name', title: '名称', width: '15%',height:'100px'},
			{field: 'typeText', title: '主题', width: '5%',height:'100px'},
			/*{field: 'flagStr', title: '标签', width: '10%',height:'100px'},*/
			{field: 'section', title: '学段', width: '5%',height:'100px'},
			{field: 'grade', title: '年级', width: '5%',height:'100px'},
			{field: 'imgpath', title: '图片', width: '10%',formatter:formatimg},
			{field: 'url', title: '购买链接', width: '10%',formatter:formaturl},
			{field: 'createtime', title: '同步时间', width: '10%'},
			{field: 'updatetime', title: '修改时间', width: '10%'},
			{field: 'state', title: '状态', width: '5%',formatter:formatstate},
		/*	{field: 'sequence', title: '排序(不是当前排序)', width: '5%'},*/
			{field: 'isTop', title: '置顶', width: '5%',formatter:istop},
			{field: 'opt', title: '操作', width: '20%', align: 'center',formatter:formatOpt}
		]],
		onLoadSuccess:function(data){
			$("a[name='update']").linkbutton();
		},

	});
}


//查询
function query(){
	$("#dg").datagrid("load",{
		name:$.trim($("#name").val())
	});

}

function formatstate(value, row, index) {
	if(row.state==0){
		return '下架';
	}
	if(row.state==1){
		return '上架';
	}
}

function istop(value, row, index) {
	if(row.isTop==0){
		return '否';
	}
	if(row.isTop==1){
		return '是';
	}
}

//格式化操作列
function formatOpt(value, row, index){
	var str = '';
	if(row.state==0){
		str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_green"  name="update"  onclick="del(this.id,1)">上架</a>';
	}
	if(row.state==1){
		str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_gray" name="update"  onclick="del(this.id,0)">下架</a>';
		if(row.isTop==0){
			str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_violet" onclick="replace(this.id,1)"  name="update" >置顶</a>';
		}
		if(row.isTop==1){
			str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_gray" onclick="replace(this.id,0)"  name="update" >取消置顶</a>';
		}
	}
	str += '<a href="javascript:void(0)" id="'+row.id+'" class="this_but_red" onclick="update(this)"  name="update" >修改</a>';
	return str;
}


function del(id,state) {
	var msg='';
	if(state==1){
		msg='是否上架?'
	}
	if(state==0){
		msg='是否下架?'
	}
	$.messager.confirm('确认', msg, function(r){
		if (r){
			$.ajax({
				url: "/platform/textbook/delete",
				type: "POST",
				async: false,
				dataType: "json",
				data: {
					"id": id,"state":state
				}, success: function (data) {
					if (data.type == "success") {
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('提示',data.msg,'info');
					}
				}
			});
		}
	});
}

//重置操作
function replace(id,state) {
	var msg='';
	/*if(id==0){
		msg='是否重置';
	}else{*/
		if(state==1){
			msg='是否置顶';
		}
		if(state==0){
			msg='是否取消置顶';
		}
	//}
	$.messager.confirm('确认', msg, function(r){
		if (r){
			$.ajax({
				url: "/platform/textbook/replace",
				type: "POST",
				async: false,
				dataType: "json",
				data: {
					"id":id,"state":state
				}, success: function (data) {
					if (data.type == "success") {
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('提示',data.msg,'info');
					}
				}
			});
		}
	});
}

//新增教材
function addbook(){
	//清空dialog
	var bookname = $("#bookname").val();
	var author = $("#author").val();
	var topic = $('#topic').val();
	//alert(topic);
	var type= $('#type').val();
	$('#typeText').val($('#type').find("option:selected").text());
	var section = $('#section').val();
	var grade = $('#grade').val();
	var url = $("#url").val();
	var hour = $("#hour").val();
	var sequence = $("#sequence").val();
	var give = $("#give").val();/*UM.getEditor('give').getContent();*/
	var recommend = $("#recommend").val();
	$('#book_form').form('submit', {
		url:'/platform/textbook/add',
		onSubmit: function(){
			if(bookname.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','名称不允许为空','info');
				return false;
			}
			if(author.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','作者不允许为空','info');
				return false;
			}
			if(topic == ''){
				$.messager.alert('提示','请选择主题','info');
				return false;
			}
			if(type == ''){
				$.messager.alert('提示','请选择教材类型','info');
				return false;
			}
			if(section == ''){
				$.messager.alert('提示','请选择对应学段','info');
				return false;
			}
			if(grade == ''){
				$.messager.alert('提示','请选择对应年级','info');
				return false;
			}
			if(url.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','购买链接不允许为空','info');
				return false;
			}
			/*if(url.replace(/(^\s*)|(\s*$)/g, "") ==''){
			 * 
				$.messager.alert('提示','京东链接不允许为空','info');
				return false;
			}*/
			if(give.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','书评不允许为空','info');
				return false;
			}
//			if(hour.replace(/(^\s*)|(\s*$)/g, "") ==''|| hour<=0){
//				$.messager.alert('提示','请正确填写课时','info');
//				return false;
//			}
			/*if(sequence.replace(/(^\s*)|(\s*$)/g, "") ==''|| sequence.replace(/(^\s*)|(\s*$)/g, "") <0){
				$.messager.alert('提示','排序填写不正确','info');
				return false;
			}*/
			if(recommend.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','推荐理由不允许为空','info');
				return false;
			}
			$('#save').attr('onclick','javascript:void();');
		},
		success:function(data){
			var data = eval('(' + data + ')');
			if(data.type =="success"){
				$('#book').dialog('close');
				$("#dg").datagrid("reload");
				$.messager.alert('提示','新增成功','info');
			}else {
				$.messager.alert('提示',data.msg,'info');
			}
			$('#save').attr('onclick','addbook();');
		}
	});
}

//修改教材
function updatebook() {
	var bookname = $("#bookname").val();
	var author = $("#author").val();
	var topic = $('#topic').val();
	var type = $('#type').val();
	$('#typeText').val($('#type').find("option:selected").text());
	var section = $('#section').val();
	var grade = $('#grade').val();
	var url = $("#url").val();
	var hour = $("#hour").val();
	var sequence = $("#sequence").val();
	var give = $("#give").val();
	var recommend = $("#recommend").val();
	$('#book_form').form('submit', {
		url:'/platform/textbook/update',
		onSubmit: function(){
			if(bookname.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','名称不允许为空','info');
				return false;
			}
			if(author.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','作者不允许为空','info');
				return false;
			}
			if(topic == ''){
				$.messager.alert('提示','请选择主题','info');
				return false;
			}
			if(type == ''){
				$.messager.alert('提示','请选择教材类型','info');
				return false;
			}
			if(section == ''){
				$.messager.alert('提示','请选择对应学段','info');
				return false;
			}
			if(grade == ''){
				$.messager.alert('提示','请选择对应年级','info');
				return false;
			}
			/*if(url.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','京东链接不允许为空','info');
				return false;
			}*/
//			if(hour.replace(/(^\s*)|(\s*$)/g, "") ==''|| hour<=0){
//				$.messager.alert('提示','请正确填写课时','info');
//				return false;
//			}
			/*if(sequence.replace(/(^\s*)|(\s*$)/g, "") ==''|| sequence.replace(/(^\s*)|(\s*$)/g, "") <0){
				$.messager.alert('提示','排序填写不正确','info');
				return false;
			}*/
			if(give.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','书评不允许为空','info');
				return false;
			}
			if(recommend.replace(/(^\s*)|(\s*$)/g, "") ==''){
				$.messager.alert('提示','推荐理由不允许为空','info');
				return false;
			}
			$('#upda').attr('onclick','javascript:void();');
		},
		success:function(data){
			var data = eval('(' + data + ')');
			if(data.type =="success"){
				$('#book').dialog('close');
				$("#dg").datagrid("reload");
				$.messager.alert('提示','修改成功','info');
			}else {
				$.messager.alert('提示',data.msg,'info');
			}
			$('#upda').attr('onclick','updatebook();');
		}
	});
}

//新增修改弹出层
function update(val) {
	// 富文本初始化
	//um = UM.getEditor('give');
	//um = UM.getEditor('feature');
	if(val=='add'){
		// 清空富文本
		//清空form
		$("#coverimg").hide();
		$("#coverimg").attr("src","");
		$("#book_form")[0].reset();
		$("#upda").hide();
		$("#save").show();
		$("#cance").show();
		$('#book').dialog({
			title: '新增'
		});
		
		//$('#book_form').form('clear');
		$("#give").textbox("setValue", "");
		$("#recommend").textbox("setValue", "");
//		UM.getEditor('feature').setContent('', false);
		//UM.getEditor('give').setContent('', false);
		
	}else {
		/*setAble();
		alert(444);
		$('#book_form').form('clear');
		alert(555);*/
		//setAble();
		
		$('#file').filebox("clear");
		$('#book').dialog({
			title: '修改'
		});
		var textbook = getbook(val.id);
		$("#coverimg").show();
		$("#save").hide();
		$("#upda").show();
		$("#bookname").val(textbook.name);
		$("#author").val(textbook.author);
		$("#type").val(textbook.type);
		$('#section option').show();
		
		if($('#type').find("option:selected").text() == '亲子阅读'){
			$('#section option').each(function(){
				if($(this).text() == '幼儿教育' || $(this).text() == '小学' || $(this).text() == '请选择'){
					$(this).show();
				}else{
					$(this).hide();
				}
			})
		}
		//defCheck(textbook.flagStr);
		$('#topic').val(textbook.topic);
		$('#section').val(textbook.section);
		$('#section').change();
		$('#grade').val(textbook.grade);
		//$('#grade').change();
		$("#price").val(textbook.price);
		$("#hour").val(textbook.hour);
		$("#url").val(textbook.url);
		$("#id").val(textbook.id);
		$("#sequence").val(textbook.sequence);
		$("#coverimg").attr("src",textbook.imgpath);
//		UM.getEditor('feature').setContent(textbook.feature, false);
		//UM.getEditor('give').setContent(textbook.give, false);
		$("#give").textbox("setValue", textbook.give);
		$("#cover").val(textbook.coverimg);
		$("#recommend").textbox("setValue", textbook.recommend);
		if(textbook.isTop==0){
			$("#isTop0").click();
		}
		if(textbook.isTop==1){
			$("#isTop1").click();
		}
		//$('#book_form').form('clear');		
	}
	$('#book').dialog('open');
}

//获取教材信息
function getbook(id) {
	var textbook='';
	$.ajax({
		url: "/platform/textbook/textbook",
		type: "POST",
		async: false,
		data:{
			"id": id
		}
		, success: function (data) {
			textbook = JSON.parse(data).textbookEntity;
		}
	});
	return textbook;
}

//格式化超链接
function formaturl(value, row, index) {
	var a = '';
	if(value!=''){
		a = '<a href="'+value+'" class="this_but_red"  target="_Blank">购买链接</a>'
	}
	return a;
}

//格式化介绍
function synopsis(value, row, index) {
	if(row.give !=''){
		return  row.give.substring(0,10);
	}
}

//格式化图片
function formatimg(value, row, index) {
	var img = '';
	if(value!=''){
		img = "<img src='"+value+"' style='width:100px;height:50px;'>"
	}
	return img;
}

//默认选中
/*function defCheck(flagStr){
	$('input:checkbox').each(function () {
		$(this).attr('checked',false);
	});
	var flagArry = flagStr.split(",");
	$('input:checkbox').each(function() {
		for(var item in flagArry){
			if ($(this).val() == flagArry[item] ){
				$(this).prop('checked', true);
			}
		}
	});
}*/

function setDisable(){
	$("#book_detail input").attr("disabled","disabled");
	$("#book_detail textarea").attr("disabled","disabled");
	$("#book_detail select").attr("disabled","disabled");
	$(".this_but1").attr("style", "display: none");
	$(".this_but2").attr("style", "display: none");
}

function setAble(){
	$("#book_detail input").removeAttr("disabled");
	$("#book_detail textarea").removeAttr("disabled");
	$("#book_detail select").removeAttr("disabled");
	$(".this_but1").removeAttr("style");
	$(".this_but2").removeAttr("style");
}

//限制输入长度
function limitLength(){
	//限制长度输入
	$('#give').textbox('textbox').attr('maxlength',135);
	$('#recommend').textbox('textbox').attr('maxlength',30);
}
