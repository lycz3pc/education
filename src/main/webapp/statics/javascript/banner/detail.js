//学校详情、专家详情、教材详情
function detail(loc, row){
	switch(loc){
	case 'X1':
		//学校名称  schName
		$("#schName").textbox("setValue", row.schName);
		//学校所在区域 area
		$("#area").textbox("setValue", row.provinceText+" "+row.cityText+" "+row.areaText);
		//学校简介  schDescript
		$("#schDescript").textbox("setValue", row.schDescript);
		//学校图片 img1
		showImgs(row.resourceStr);
		//校长姓名 preName
		$("#preName").textbox("setValue", row.preName);
		//校长语录 preSaying
		$("#preSaying").textbox("setValue",row.preSaying);
		//头部背景图片 img2
		//尾部背景图片 img3
		//网站主题颜色 wrapper
		$("#wrapper").css("background",row.themeColor);
		//家校共育链接 position
		$("#position").textbox("setValue",row.schoolUrl);
		
		//弹出查看框
		$("#school").dialog({
			title:'查看',
			modal:true,
			close:true
		}).dialog("open");
		
		break;
	case 'ZJ1':
		//专家名称 expertName
		$("#expertName").textbox("setValue", row.name);
		//状态 status
		$("#status").textbox("setValue", row.status==1?"已通过":"待审核");
		//单位 unit
		$("#unit").textbox("setValue", row.unit);
		//排序 expertSort
		//$("#expertSort").textbox("setValue", row.sort);
		//简介 synopsis
		//$("#synopsis").textbox("setValue", row.synopsis);
		//成就 achievement
		//$("#achievement").textbox("setValue", row.achievement);
		//主题
		$("#topic").textbox("setValue", row.topic);
		$.ajax({
			url:"/platform/expert/queryImgs",
			type:'post',
			dataType:'json',
			async: false,
			data:{
				resourceStr:row.expertImg
			},
			success:function(data){
				$('#expertImg').attr('src',data.path);
			}
		});
		
		//弹出查看框
		$("#expert").dialog({
			title:'查看',
			modal:true,
			close:true
		}).dialog("open");
		
		break;
	case 'J1':
		
		//教材名称 booknames
		$("#booknames").textbox("setValue", row.name);
		//封面 coverimgs
		$("#coverimgs").attr("src", row.imgpath);
		//京东链接 bookUrl
		$("#bookUrl").textbox("setValue", row.url);
		//主题 booktype
		$("#booktype").textbox("setValue", row.typeText);
		//学段 booksection
		$("#booksection").textbox("setValue", row.section);
		//年级 bookgrade
		$("#bookgrade").textbox("setValue", row.grade);
		//作者 bookauthor
		$("#bookauthor").textbox("setValue", row.author);
		
		//弹出查看框
		$("#bookText").dialog({
			title:'查看',
			modal:true,
			close:true
		}).dialog("open");
		
		break;
	default:
		break;
	}
	
}

//联盟学校获取图片
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
			$.each(imgList,function(index,item){
				$('#img' + (index+1)).attr('src',item.path);
			})
		}
	});
}

//根据id获取图片
function getPathById(id){
	var path = "";
	$.ajax({
		url:'/resources/selectById',
		type:'post',
		async:false,
		contentType:"application/json",
		dataType:'json',
		data:JSON.stringify({id:id}),
		success:function(data){
			console.log(data.resource.path);
			path = data.resource.path;
		}
	});
	
	return path;
}

