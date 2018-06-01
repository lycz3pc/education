
$.ajax({
		type: "get",
		url: "/sys/menu/list",
		async: true,
		dataType:"json",
		success: function(data) {
			/*console.log(data);*/
			var _menus = data.menus;
			var html=[];
			for ( var i = 0; i < _menus.length; i++) {
				var root="'"+_menus[i].text+"'";
				html.push('<li onclick="menu('+_menus[i].id+','+root+')" class="pf-nav-item home ' + (i == 0 ? 'current' : '') + '" data-menu="sys-manage">'+
					'<a href="javascript:;">'+
				'<span class="iconfont">&#xe63f;</span>'+
				'<span class="pf-nav-title">'+_menus[i].text+'</span></a></li>');
			}
			$("#rootMenu").html(html.join(''));
			if(typeof(_menus[0])!='undefined'){
				menu(_menus[0].id,_menus[0].text);
			}
			/*if(_menus.length==0){*/
				//退出系统
				$(document).on('click', '.pf-logout', function() {
					/*confirm('您确定要退出吗？', {
						icon: 4,
						title: '确定退出' //按钮
					}, function() {
						location.href = 'login.html';
					});*/
					
					
					$("#tip").dialog({
						title:'退出确认',
						buttons:[{
							text:'确认',
							iconCls:'icon-ok',
							handler:function(){
								location.href="/logout"
							}
						},{
							text:'取消',
							handler:function(){
								$("#tip").dialog("close");
							}
						}]
					});
				});
			/*}*/
	}	
});

function menu(parentid,root){
	$.ajax({
		type: "get",
		url: "/sys/menu/bypid?parentid="+parentid,
		async: true,
		dataType:"json",
		success: function(data) {
			var _menus = data.menus;
			var mainPlatform = {
				init: function() {
					this.bindEvent();
					this.render(_menus);
				},

				bindEvent: function() {
					var self = this;
					// 顶部大菜单单击事件
					$(document).on('click', '.pf-nav-item', function() {
						$('.pf-nav-item').removeClass('current');
						$(this).addClass('current');

						// 渲染对应侧边菜单
						var m = $(this).index();
						self.render(_menus[m]);
						
						//为左侧大菜单加icon
						var iicon = $(this).find(".iconfont").html();
						$("#pf-bd #pf-sider .pf-model-name .pf-sider-icon").html(iicon);
					});

					//左侧菜单二级菜单点击事件
					$(document).on('click', '.sider-nav li', function() {
						$('.sider-nav li').removeClass('current');
						$(this).addClass('current');
					});

					//左侧菜单三级菜单点击事件-----------------------------------------------------------
					$(document).on('click', '.sider-nav-s li', function() {
						$('.sider-nav li').removeClass('active');
						$(this).addClass('active');
					});
					
					//退出系统
					/*$(document).on('click', '.pf-logout', function() {*/
						/*confirm('您确定要退出吗？', {
							icon: 4,
							title: '确定退出' //按钮
						}, function() {
							location.href = 'login.html';
						});*/
						
						
					/*	$("#tip").dialog({
							title:'退出确认',
							buttons:[{
								text:'确认',
								iconCls:'icon-ok',
								handler:function(){
									location.href="/logout"
								}
							},{
								text:'取消',
								handler:function(){
									$("#tip").dialog("close");
								}
							}]
						});
					});*/
					//左侧菜单收起
					$(document).on('click', '.toggle-icon', function() {
						$(this).closest("#pf-bd").toggleClass("toggle");
						setTimeout(function() {
							$(window).resize();
						}, 300)
					});
				},

				//绑定左侧菜单数据
				render: function(menu) {
					$('#pf-sider').empty();
					var html = ['<h2 class="pf-model-name"><span class="iconfont pf-sider-icon">&#xe63f;</span><span class="pf-name">' +root + '</span><span class="toggle-icon"></span></h2>'];
					html.push('<ul class="sider-nav">');
	             
					for (var i = 0, len =menu.length; i < len; i++) {
						var childmenu = menu[i].children;
						html.push('<li > <a href="javascript:;"><span class="iconfont sider-nav-icon">&#xe620;</span><span class="sider-nav-title">' + menu[i].text + '</span><i class="iconfont">&#xe642;</i></a><ul class="sider-nav-s">');
						for (var j = 0; j < childmenu.length; j++) {
							html.push('<li data-src="' + childmenu[j].url + '" ><a href="javascript:;" class="easyui-linkbutton" ' + "onclick=" + '"' + "addTab('" + childmenu[j].text + "'," + "'" + childmenu[j].url + "'" + ')">' + childmenu[j].text + '</a></li>')
						}
						html.push('</ul></li>');
					}
					html.push('</ul>');
					$('#pf-sider').html(html.join(''));
				}

			};

			mainPlatform.init();
		}
	});

}
//添加easyui-tabs（防止重复）   
function addTab(title, url) {
	if ($('#pf-page').tabs('exists', title)) {
		$('#pf-page').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" border="no" frameborder="0" title="' + title + '" src="' + url + '" class="page-iframe" style="width:100%;height:100%;"></iframe>';
		$('#pf-page').tabs('add', {
			title: title,
			content: content,
			closable: true
		});
	}
}
/** 
 * json格式转树状结构 
 * @param   {json}      json数据 
 * @param   {String}    id的字符串 
 * @param   {String}    父id的字符串 
 * @param   {String}    children的字符串 
 * @return  {Array}     数组 
 */  
function transData(a, idStr, pidStr, chindrenStr){  
    var r = [], hash = {}, id = idStr, pid = pidStr, children = chindrenStr, i = 0, j = 0, len = a.length;  
    for(; i < len; i++){  
        hash[a[i][id]] = a[i];  
    }  
    for(; j < len; j++){  
        var aVal = a[j], hashVP = hash[aVal[pid]];  
        if(hashVP){  
            !hashVP[children] && (hashVP[children] = []);  
            hashVP[children].push(aVal);  
        }else{  
            r.push(aVal);  
        }  
    }  
    return r;  
}

//0906  S_tabs标签关闭功能
window.onload = function(){
	
	var main_tabs=document.querySelector(".tabs-wrap");
	var main_tabs_header=document.querySelector(".tabs-header");
	var main_tabs_delete=document.getElementById("tabs_deletes"); 
	//给选中标签添加右击事件
	main_tabs.oncontextmenu = function(e){
	    var e = e || window.event;
	    main_tabs_delete.style.display = "block";
	    main_tabs_delete.style.left = e.clientX-15+'px';
	    main_tabs_delete.style.top = 18+'px';
	   return false;//取消右键点击的默认事件
	};
 
	  //生成tab标签
	  $('#pf-page').tabs({
		   border : true,
		  });
	   //为每个菜单绑定点击事件
	   //关闭选中的标签
	   $(".tabs_deletes").click(function(){
	    //获取选中的标签索引
	    var tab = $('#pf-page').tabs('getSelected');
	    var index = $('#pf-page').tabs('getTabIndex',tab);
	    if(index!=0){
	    	$("#pf-page").tabs("close",index);
	    }
	   });
 	
	   
	   //关闭所有标签
	   $(".tabs_deletes_all").click(function(){
	    var tabs = $("#pf-page").tabs("tabs");
	    var length = tabs.length;
		    for(var i=0;i<length;i++){
		    //如果我是第一个标签
		    	if(i!=0){
		    		$("#pf-page").tabs("close",1);
		    	}
		    }
	   });
	   
	   //关闭选中标签之外的标签
	   $(".tabs_deletes_else").click(function(){
	    //获取所有标签
	    var tabs = $("#pf-page").tabs("tabs");
	    var length = tabs.length;
	    //获取选中标签的索引
	    var tab = $('#pf-page').tabs('getSelected');
	    var index = $('#pf-page').tabs('getTabIndex',tab);
		    //关闭选中标签之前的标签
		    for(var i=0;i<index-1;i++){	
		    		$("#pf-page").tabs("close",1);
		    }
		    //关闭选中标签之后的标签
		    for(var i=0;i<length-index+1;i++){
		    //如果我是选中的标签
		    	if(i!=0){
		    	 $("#pf-page").tabs("close",2);  
		    }

	    }
	   });
	   
	   //鼠标移出，隐藏
	   $(document).on('mouseover', '.tabs-panels,#pf-sider,.pf-hd', function() {
		   $("#tabs_deletes").hide();
	   })
	   //鼠标点击关闭，隐藏
	   $(document).on('click', '#tabs_deletes ul li', function() {
		   $("#tabs_deletes").hide();
	   })
	   
 	}



