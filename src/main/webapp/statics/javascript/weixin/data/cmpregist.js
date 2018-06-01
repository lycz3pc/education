
$(document).ready(function(){
	
	//校区列表
	 campusList=[];
	//注册量列表
	registList=[];
	//初始化查询提示
	$("#msg").show();
	 $.ajax({
	        method: 'POST',
	        url: '/regist/npWxRegistRecordT/countCampusRegistList',
	        contentType: "application/json",
	        dataType: "json",
	         data: JSON.stringify({
	        	start:$("#start").datebox("getValue"),
				end:$("#end").datebox("getValue")
	        }),
	        success: function(data) {
	        	for(var i =0;i<data.list.length;i++){
	        		campusList[i]=data.list[i].campus;
	        		registList[i]=data.list[i].count;
	        	}
	        	
	        	tjpic();
	        }
		});
	

    });



//根据开始时间和结束时间查询
function qryLog(){
	
	var start=$("#start").datebox("getValue");
	var end=$("#end").datebox("getValue");
	if(toTimestamp(end) - toTimestamp(start) >365*86400){
		$.messager.alert('提示','最大查询时段为一年!','info');
		return ;
	}
	if(toTimestamp(end) < toTimestamp(start)){
		$.messager.alert('提示','结束时间必须大于开始时间!','info');
		return ;
	}
	if(isNaN(toTimestamp(start)) || isNaN(toTimestamp(end))){
		$.messager.alert('提示','开始时间或者结束时间为空!','info');
		return ;
	}
	//校区列表
	 campusList=[];
	//注册量列表
	registList=[];
	
		
	 $.ajax({
	        method: 'POST',
	        url: '/regist/npWxRegistRecordT/countCampusRegistList',
	        contentType: "application/json",
	        dataType: "json",
	         data: JSON.stringify({
	        	start:toTimestamp(start),
				end:toTimestamp(end)+86400
	        }),
	        success: function(data) {
	        	//根据时间段查询隐藏提示信息
	        	$("#msg").hide();
	        	document.getElementById('chart3').style.height='420px';
	        	if(!data.list[0]){
	        		//当查询时间段注册量不存在是查询校区列表
	        		queryCampusList();
	        	}else{
	        	for(var i =0;i<data.list.length;i++){
	        		campusList[i]=data.list[i].campus;
	        		registList[i]=data.list[i].count;
	        	}
	        	
		        	tjpic();
	        }
	        }
		});
}


function queryCampusList(){
	 $.ajax({
	        method: 'POST',
	        url: '/regist/npWxRegistRecordT/queryCampusList',
	        dataType: "json",
	        success: function(data) {
	        	for(var i =0;i<data.list.length;i++){
	        		campusList[i]=data.list[i].campusName;
	        		registList[i]=0;
	        	}
	        	
	        	tjpic();
	        }
		});
	
}

//date类型转时间戳
function toTimestamp(date){
	var timestamp = Date.parse(date);
	timestamp = timestamp / 1000;
	return timestamp;
} 


//生产条形统计图
function tjpic(){
	var option3 = {
			
			grid:{

				x:'20%',//控制统计图左上角的x坐标位置，可为数值单位是px，可用百分比

				y:'15%',//控制统计图左上角y坐标的位置，可为数值单位是px，可用百分比

				width:'60%',//控制统计图的宽度

				height:'70%',//控制统计图的高度

				//borderWidth:0,//控制边框的线条粗细，为0则不显示

				borderColor:'#ccc'//修改边框的线条颜色，如果borderWidth为0则不起作用

				},
			backgroundColor: '#f8f8f8',
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['注册量'],
                show:false
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: false},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    splitLine:{  
                        show:false  
                    },
                    data : campusList
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'注册量',
                    type:'bar',
                    itemStyle : {
                	    normal: {
                	       color:'#519af2',
                		   label : {
                			   show: true,
                			   textStyle:{color:'red'}
                			   }
                                }
                             },
                    data:registList,
                    markPoint : {
                    	itemStyle : {
                    	    normal: {
                    	    	color:'red'
                                    }
                                 },
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                    	itemStyle : {
                    	    normal: {
                    	    	color:'red'
                                    }
                                 },
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        }; 

        var myChart3 = echarts.init(document.getElementById('chart3'));
        myChart3.setOption(option3);         
          
        //我的待办点击事件
        $(document).on('click','.work-item.green',function(event){
            var width = (2 * $(this).width()) + 10;
            $(".todo-panel").width(width -2).css({top:$(this).offset().top,left:$(this).offset().left}).show();
            event.stopPropagation();
        });  
        $(".todo-panel").click(function(){
             event.stopPropagation();
        });    
        $(document).click(function(){
            $(".todo-panel").hide();
        });      
}
