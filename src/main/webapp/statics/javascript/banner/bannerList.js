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
			name:"text",
			url:"url2"
		},
		simpleData: {
			enable:true,
			idKey:"id",
			pIdKey:"pid"
		}
	},
	view: {
		showLine:true
	}
};

//只能是内部链接的位置
var locs = ["X1","ZJ1","J1"];
var X1 = "X1";
var ZJ1 = "ZJ1";
var J1 = "J1";

$(function(){
	//初始化
	init();
	//初始化树
	reloadTree();
	//初始化内部表格
	innerInit();
	
	//位置
	$("#location2").combotree({
		url:'/statics/javascript/banner/location.json',
		required:true,
		onBeforeSelect: function(node) {
			//只能选择叶子节点
		    if (!$(this).tree('isLeaf', node.target)) {  
		        return false;  
		    }  
		},  
		onClick: function(node) {  
		    //选中的不是叶子节点，展示列表框
		    if (!$(this).tree('isLeaf', node.target)) {  
		        $('#location2').combo('showPanel');  
		    }  
		},
		onSelect: function(node){
		    //位置选中时，更改位置描述
		    var text = node.describe;
		    $("#locationtext").textbox("setValue", text);
		},
		onChange:function(newVal,oldVal){
		    var t = $(this).combotree("tree");
		    var node = t.tree("getSelected");
		    
		    //排序个数
		    var number = node.number;
		    //设置排序选项
		    $("#sort").combobox("setValue", "");
		    var combo =[];  
            for(var i=1;i<=number;i++){  
                combo.push({"text":i,"id":i});  
            }  
            $("#sort").combobox("loadData", combo);
            
            //排序
			var sort = $("#sort").combobox("getValue");
			//验证位置排序
			validateSort(newVal, sort);
            
		},
		onShowPanel: function(){
			//删除不在新增修改的位置
			var t = $(this).combotree("tree");
		    for(var i=0; i<locs.length; i++){
		    	var node = t.tree('find', locs[i]);
		    	if(node != null){
		    		t.tree("remove", node.target);
		    	} 
		    }
		}
	});
	
	//确认排序是否存在
	$("#sort").combobox({
		onChange: function(newVal, oldVal){
			//位置
			var site = $("#location2").combotree("getValue");
			//验证位置排序
			validateSort(site, newVal);
			
		}
	});
		
});

//刷新页面左边的栏目树
function reloadTree(){
	$.ajax({
        method: 'post',
        url: '/statics/javascript/banner/location.json',
        dataType: "json",
        success: function(data) {
			var zNodes = data;
			var ztree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree.expandAll(true);
			//默认选中id为"B1"的节点
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node = zTree.getNodeByParam("id","B1");
			zTree.selectNode(node);
			setting.callback.onClick(null, zTree.setting.treeId, node);
			
        }
	});
}
// 栏目树点击事件
function zTreeOnClick(event, treeId, treeNode){
	// 父节点不可点击
	if (treeNode.isParent) {
		return ;
	}
	//重新加载列表
	reloadGrid();
	
}

//重新加载表
function reloadGrid(location){
	
	//得到树选择的节点
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if(location){
		var node = zTree.getNodeByParam("id",location);
		zTree.selectNode(node);
	}
	var nodes = zTree.getSelectedNodes();
	
	//表列
	var cols = [];
	//url
	var url = "";
	//表头
	var ttitle = "";
	//状态
	var state = null;
	
	//设置表列和url
	switch(nodes[0].id){
	case X1:
		url = "/platform/school/schoolList";
		ttitle = "联盟学校列表";
		$("#addTool").hide();
		cols = [[
					{field:'schName',title:'学校名称',width:'18%'},    
					{field:'province',title:'地区',width:'25%', formatter:formatArea},
					{field:'preName',title:'校长',width:'10%'},
					{field:'createTime',title:'加入联盟时间',width:'15%'},
					{field:'updateTime',title:'最后一次修改时间',width:'15%'},
					{field:'isTop',title:'是否置顶',width:'5%',formatter:formatIsTop},
					{field:'opt',title:'操作',width:'10%',align:'center',formatter:formatOpt}
			]];
		break;
	case ZJ1:
		url = "/platform/expert/list";
		ttitle = "专家列表";
		$("#addTool").hide();
		cols = [[
		         	{field:'name',title:'专家姓名',width:'10%'},    
		         	{field:'achievement',title:'职务',width:'33%'},
		         	{field:'unit',title:'单位',width:'22%'},
		         	{field:'isTop',title:'是否置顶',width:'10%',formatter:formatIsTop},
		         	{field:'opt',title:'操作',width:'25%',align:'center',formatter:formatOpt}
			]];
		break;
	case J1:
		url = "/platform/textbook/index";
		ttitle = "教材列表";
		state = 1;
		$("#addTool").hide();
		cols = [[
		         	{field: 'name', title: '名称', width: '30%'},
		         	{field: 'createtime', title: '同步时间', width: '20%'},
		         	{field: 'updatetime', title: '修改时间', width: '20%'},
		         	{field:'isTop',title:'是否置顶',width:'10%',formatter:formatIsTop},
		         	{field:'opt',title:'操作',width:'20%',align:'center',formatter:formatOpt}
			]];
		break;
	default:
		url = "/platform/banner/list";
		$("#addTool").show();
		ttitle = "banner列表";
		cols = [[
				{field:'location',title:'位置',width:'10%'},
				{field:'img',title:'图片',width:'20%',formatter:formatImg},
				{field:'url',title:'链接',width:'25%',align:'left'},
				{field:'sort',title:'排序',width:'10%'},
				{field:'upDown',title:'状态',width:'10%',formatter:formatStatus},
				{field:'opt',title:'操作',width:'25%',align:'center',formatter:formatOpt}
		]];
		break;
	
	}
	//重新设置表格url和列
	$("#dg").datagrid({
		title:ttitle,
		url:url,
		columns:cols,
		queryParams:{
			location:nodes[0].id,
			isTop:1,
			state:state
		}
	});
	//初始化页码
	$('#dg').datagrid('getPager').pagination({  
		pageNumber: 1
	});
}

	/**
	  *初始化
	  */
	function init(){
		//表格
		$("#dg").datagrid({
			title: 'banner列表',
			singleSelect:true,
			fitColumns:true,
			url:'/platform/banner/list',
			method:'post',
			onLoadSuccess:function(data){
				$("a[name='upOrDown']").linkbutton();
		       $("a[name='update']").linkbutton();
		        $("a[name='delete']").linkbutton(); 
			},
			onDblClickRow:function(rowIndex,row){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				
				//当行是有更新功能时，双击行查看详细信息
				if(!locs.contains(nodes[0].id)){
					//链接方式 ckTypeUrl
					$("#ckTypeUrl").textbox("setValue",row.urlType=="inner"?"内部链接":"外部链接");
					//标题 cktitle
					$("#cktitle").textbox("setValue",row.title);
					//位置 ckLoc
					$("#ckLoc").textbox("setValue",row.location);
					//位置描述 ckLoctext
					$("#ckLoctext").textbox("setValue",row.locationText);
					//链接 ckUrl
					$("#ckUrl").textbox("setValue",row.url);
					//排序 ckSort
					$("#ckSort").textbox("setValue",row.sort);
					//图片 ckImg
					$("#ckImg").attr("src",row.img);
					
					//弹出查看框
					$("#check").dialog({
						title:'查看banner',
						modal:true,
						close:true
					}).dialog("open");
				}
				
				//其他详情
				detail(nodes[0].id,row);	
			}
		});
		
		//弹出框
		$("#add").dialog({
			title:'新增banner',
			modal:true,
			close:true
		}).dialog("close");
		
		//链接方式下拉框
		$("#urlType").combobox({
			onChange:function(newVal,oldVal){
				
				if(newVal=="inner"){
					changeHeight("81%");
					//选中内部链接时
					$("table .inner").show();
					$("table .outer").hide();
					
				}else{
					changeHeight("70%");
					
					$("#type").val("");
					$("#innerDg").datagrid("load");
					$("table .inner").hide();
					$("table .outer").show();
				}
				//从内部转外部时初始化
				if(oldVal=="inner"){
					
					$("#url").textbox("setValue", "");
					$("#title").textbox("setValue", "");
					$("#type").val("");
					$("#cmsType").combotree("setValue", "");
					$("#innerDg").datagrid("load");
				}
				
			}
		});
	}
	
	//格式化操作列
	function formatOpt(value, row, index){
		var str = "";
		var cname = "";
		//得到树选择的节点
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getSelectedNodes();
		//操作按钮名称
		var name = "";
		if(row.upDown=="1"||row.isTop=="1"){
			name = "下架";
			cname = "this_but_gray";
		}else if(row.upDown=="0"){
			name = "上架";
			cname = "this_but_green";
		}
		if(row.isTop=="1"){
			name = "取消置顶";
			cname = "this_but_gray";
		}else if(row.isTop=="0"){
			name = "置顶";
			cname = "this_but_violet";
		}
		
		if(!locs.contains(nodes[0].id)){
			str += '<a href="javascript:void(0)" class="easyui-linkbutton '+cname+'" name="upOrDown"  onclick="upOrDown('+index+')">'+name+'</a>';
			str +=  '<a href="javascript:void(0)" class="easyui-linkbutton this_but_red" name="update"  onclick="updateRow('+index+')">更新</a>';
			str +=  '<a href="javascript:void(0)" class="easyui-linkbutton this_but_black" name="delete"  onclick="delRow('+index+')">删除</a>'
		}else{
			str += '<a href="javascript:void(0)" class="easyui-linkbutton '+cname+'" name="upOrDown"  onclick="upOrDown('+index+','+nodes[0].id+')">'+name+'</a>';
		}
		
		return str;
	}
	//列表图片列格式化
	function formatImg(value, row, index){
		var str = "";
		if(value!=""){
			str = "<img src="+value+" style='width:100px;height:50px;'>";
		}
		return str;
		
	}
	//列表状态栏格式化
	function formatStatus(value, row, index){
		return row.upDown == "1"?"上架":"下架";
	}
	//列表地区格式化
	function formatArea(value, row, index){
		return row.provinceText + " " + row.cityText + " " + row.areaText;
	}
	//格式化是否置顶
	function formatIsTop(value, row, index){
		return value==1?"是":"否";
	}
	
	//新增
	function append(){
		$(".this_table tr td .this_but1").text("保存");
    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
		
		//弹出框
		$("#add").dialog({
			title:'新增banner'
		});
		$("#sortTip").hide();
		//清空form
		$('#ff').form('clear');
		$("#urlType").combobox("setValue","outer");
		//初始化图片
		initImg();
		validateFile(true);
		$("#add").dialog("open");
		$("table .inner").hide();
		$("table .outer").show();
		
		//列表重新加载，为了清空选中
		$("#dg").datagrid("reload");
		$(".this_table tr td .this_but3").text("清空");
	}
	//更新
	function updateRow(index){
		$(".this_table tr td .this_but1").text("保存");
    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
		//选中index行
		$("#dg").datagrid("selectRow", index);
		//获得选中行对象
		var row = $("#dg").datagrid("getSelected");
		
		$("#add").dialog({
			title:'修改banner'
		});
		$("#sortTip").hide();
		//初始化cms表格
		$('#ff').form('clear');
		//初始化图片
		initImg();
		$("#add").dialog("open");
	
		if(row.urlType=="outer"){
			//当修改数据是外部链接时
			$("table .inner").hide();
			$("table .outer").show();
			
		}else{
			//当修改数据是内部链接时
			$("#cmsType").combotree("setValue", row.type);
			var selectId = row.url.split("=")[1];
			$("table .inner").show();
			$("table .outer").hide();
			$("#innerDg").datagrid({
				onLoadSuccess:function(data){
					$.each(data.rows, function(index, item){
						if(item.id == selectId){
							
							$('#innerDg').datagrid('selectRow',index);
						}
					});
				}
			});
		}
		
		$("#img").attr("src", row.img);
		//为外部链接时，存在图片就不验证file，否则，验证file
		if(row.img != ""){
			validateFile(false);
		}else{
			validateFile(true);
		}
		
		//表单加载行数据
		$('#ff').form('load', row);
		$(".this_table tr td .this_but3").text("重置");
		
	}
	
	//提交
	function submitForm(){
		var val = $("#urlType").combobox("getValue");
		//当提交数据为内部链接时
		if(val == "inner"){
			var row = $("#innerDg").datagrid("getSelected");
			if (row){
				$("#url").textbox("setValue", row.id);
				$("#title").textbox("setValue", row.id);
			}
		}
		
		$('#ff').form('submit',{
			url:'/platform/banner/add',
			onSubmit: function(param){
				$(".this_table tr td .this_but1").text("正在提交...");
		    	$(".this_table tr td .this_but1").attr("onclick","javascript:void(0);");
				
				if(val == "inner"){
					//当内部链接未选择图文数据时，提示，中断数据提交
					var row = $("#innerDg").datagrid("getSelected");
					if (!row){
						center("请选择内部链接");
						$(".this_table tr td .this_but1").text("保存");
				    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
						return false;
					}
				}
				
				//上传图片文件大小不超过3M
				if(typeof($("input[name=file]" )[0].files[0])!='undefined'){
					if($("input[name=file]" )[0].files[0].size > 3 * 1024 * 1024){
						center("图片不能超过3M");
						$(".this_table tr td .this_but1").text("保存");
				    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
						return false;
					}
				}
				
				var result = $('#ff').form("validate")
				if(!result){
					$(".this_table tr td .this_but1").text("保存");
			    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
				}
				
				return result;
		    },
			success:function(data){
				var data = eval('(' + data + ')');
				if(data.type=="success"){
					$("#add").dialog("close");
					//得到选择的位置
					var t = $("#location2").combotree("tree");
			    	var node = t.tree("getSelected");
					//将列表中以重新加载
					reloadGrid(node.id);
					
					center("操作成功");		
				}
				$(".this_table tr td .this_but1").text("保存");
		    	$(".this_table tr td .this_but1").attr("onclick","submitForm()");
			}
		});
	}
	
	//删除
	function delRow(index){
		
		//选中index行
		$("#dg").datagrid("selectRow", index);
		//获得选中行对象
		var row = $("#dg").datagrid("getSelected");
		
		$("#tip").text("该操作将永久删除，确认删除吗？");
		$("#tip").dialog({
			title:'确认删除',
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$.ajax({
						url:"/platform/banner/delete",
						type:'post',
						dataType:'json',
						data:{
							ids:row.id
						},
						success:function(data){
							if(data.type=="success"){
								$("#tip").dialog("close");
								//将列表中以重新加载
								reloadGrid();
								center("删除成功");
								
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
	//上架或下架
	function upOrDown(index,loc){
		$("#dg").datagrid("selectRow", index);
		var row = $("#dg").datagrid("getSelected");
		
		var img = "";
		//当行中upDown字段为1时，执行下架，否则执行上架
		if(typeof(row.upDown)!="undefined"){
			if(row.upDown == "1"){
				img = "确认下架吗？";
			}else{
				img = "上架将会使已存在的记录下架，确认上架吗？";
			}
			loc = row.location;
		}else {
			if(row.isTop == "1"){
				img = "确认取消置顶吗？";
			}else{
				img = "确认置顶吗？";
			}
		}
		$("#tip").text(img);
		$("#tip").dialog({
			title:'提示',
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$.ajax({
						url:"/platform/banner/upOrDown",
						type:'post',
						dataType:'json',
						data:{
							id:row.id,
							location:loc
						},
						success:function(data){
							if(data.type=="success"){
								$("#tip").dialog("close");
								//重新加载列表
								reloadGrid();
								center("操作成功");
								
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
	
	//内部链接列表初始化
	function innerInit(fields, titles){
		$("#innerDg").datagrid({
			rownumbers:false,
			title: '内部链接',
			singleSelect:true,
			fitColumns:true,
			pagination:true,
			url:'/platform/banner/selectCms',
			method:'post',
			toolbar: '#innerTb',
			queryParams:{
				type:$("#cmsType").combotree('getValue')
			},
			columns:[[
			    {field:'ck',checkbox:true, width:'10%'},
				{field:'title',title:'标题',width:'45%'},
				{field:'createTime',title:'创建时间',width:'45%'},
			]],
			onLoadSuccess:function(data){

			}
		});
		
		//内部链接栏目选择
		$("#cmsType").combotree({
			onChange:function(newVal, oldVal){
				$("#innerDg").datagrid("load",{
					type:newVal
				});
				$("#type").val(newVal);
			}
		});
		
		//图片回显
		$("#file").filebox({
			buttonText:'选择图片',
			accept : 'image/jpg, image/bmp, image/jpeg, image/gif, image/png ', 
		    onChange: function (event) {

		        // 获取所选文件的File对象
		        var file = $(this).filebox("getFile");

		        if (file != null) {
		        	if(file.size > 3 * 1024 * 1024){
		        		center("不能超过3M");
		        		$(this).filebox("clear");
		        		return;
		        	}
		            // 创建FileReader对象
		            var reader = new window.FileReader();
		            // 定义reader的onload事件
		            // 当读完文件信息后触发onload事件
		            reader.onload = function (e) {
		                // reader.result保存着产生的虚拟URL
		                $("#img").attr("src", this.result);
		            }
		            // 读取指定文件并形成URL
		            reader.readAsDataURL(file);
		        }else{
		        	initImg();
		        	validateFile(true);
		        }   
		    }
		});    
	}
	
	//清空
	function clearForm(){
		
		$("#sortTip").hide();
		
		if($("#bid").val()==""){
			//新增时清空数据
			$('#ff').form('clear');
			$("#urlType").combobox("setValue","outer");
			//初始化图片
			initImg();
		}else{
			//更新时重置数据
			var row = $("#dg").datagrid("getSelected");
			//清空表单
			$('#ff').form('clear');
			//初始化图片
			initImg();
			
			$("#add").dialog("open");
			
			if(row.urlType=="outer"){
				//修改前为外部链接
				$("table .inner").hide();
				$("table .outer").show();
				
				
			}else{
				//修改前为内部链接
				$("#cmsType").combotree("setValue", row.type);
				var selectId = row.url.split("=")[1];
				$("table .inner").show();
				$("#innerDg").datagrid({
					onLoadSuccess:function(data){
						$.each(data.rows, function(index, item){
							if(item.id == selectId){
								$('#innerDg').datagrid('selectRow',index);
							}
						});
					}
				});
			}
			$("#img").attr("src", row.img);
			//存在图片时不验证file框，否则验证file框
			if(row.img != ""){
				validateFile(false);
			}else{
				validateFile(true);
			}
			
			$('#ff').form('load', row);	
		}
		
	}
	
	//验证是否存在相同位置相同排序的数据
	function validateSort(loc, sort){
		//id
		var id = $("#bid").val();
		$.ajax({
			url:'/platform/banner/validateSort',
			method:'post',
			dataType:'json',
			data:{
				id:id,
				location:loc,
				sort:sort
			},
			success:function(data){
				if(data.type=="success"){
					$("#sortTip").hide();
				}else{
					$("#sortTip").show();
				}
			}
		});
	}
	
	
	//弹出层
	function center(msg){
			$.messager.show({
				title:'提示',
				msg:msg,
				showType:'fade',
				style:{
					right:'',
					bottom:''
				}
			});
		}
	//验证文件
	function validateFile(bool){
		$("#file").filebox({
			required : bool
		});
	}
	//初始化图片
	function initImg(){
		$("#img").attr("src","/statics/images/addimg.png");
	}
	
	//新增修改弹出框高度
	function changeHeight(height){
		$("#add").dialog({
			height:height
		});
	}