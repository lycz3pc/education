
$(document).ready(function(){
	
	//时间列表
	 datatime=[];
	 datatime =getTimeList();
	//点击量列表
	dataclick=[];
	 
	 $.ajax({
	        method: 'POST',
	        url: '/jdclick/npWxClickRecordT/sumjdclickList',
	        contentType: "application/json",
	        dataType: "json",
	         data: JSON.stringify({
	        	start:$("#start").datebox("getValue"),
				end:$("#end").datebox("getValue")
	        }),
	        success: function(data) {
	        	
	        	for(var j=0;j<datatime.length;j++){
	        	  var sum =check(datatime[j],data.list);
	        	  if(sum){
	        		  dataclick[j]=sum; 
	        	  }else{
	        		  dataclick[j]=0;
	        	  }
	        		
	        	}
	        	tjpic();
	        }
		});
	

    });

//根据开始时间和结束时间查询
function qryLog(){
	
	var start=$("#start").datebox("getValue");
	var end=$("#end").datebox("getValue");
	if(toTimestamp(end) - toTimestamp(start) >2592000){
		$.messager.alert('提示','最大查询时段为一个月天!','info');
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
	//时间列表
	datatime=[];
	datatime=getSearchTimeList(start,end);
	//点击量列表
	dataclick=[];
	
		
	 $.ajax({
	        method: 'POST',
	        url: '/jdclick/npWxClickRecordT/sumjdclickList',
	        contentType: "application/json",
	        dataType: "json",
	         data: JSON.stringify({
	        	start:toTimestamp(start),
				end:toTimestamp(end)+86400
	        }),
	        success: function(data) {
	        	for(var j=0;j<datatime.length;j++){
		        	  var sum =check(datatime[j],data.list);
		        	  if(sum){
		        		  dataclick[j]=sum; 
		        	  }else{
		        		  dataclick[j]=0;
		        	  }
		        		
		        	}
		        	tjpic();
	        }
		});
}

//判断查询的输入的日期是否查询出数据,有返回该日期的总点击量，没有返回null
function check(time,datalist){
	for(var i=0;i<datalist.length;i++){
		if(time == datalist[i].days){
			return datalist[i].sum;
		}
	}
	return null;
}
//生成搜索时间段的时间列表
function getSearchTimeList(start,end){
	var startstamp = Date.parse(start);
	var endstamp = Date.parse(end);
	searchTimeList=[];
	var n=(endstamp-startstamp)/86400000;
	for(var i=0;i<=n;i++){
		if(i == 0){
			searchTimeList[i]=toDate(startstamp);
		}else{
			startstamp+=86400000;
			searchTimeList[i]=toDate(startstamp);
		}
	}
	return searchTimeList;
	
}


//生成最近一个月的时间列表
function getTimeList(){
	var timestamp = Date.parse(new Date())-29*86400000;//86400000一天的毫秒数
	 timeList=[];
	for(var i=0;i<30;i++){
		if(i == 0){
			timeList[i]=toDate(timestamp);
		}else{
			timestamp+=86400000;
			timeList[i]=toDate(timestamp);
		}
		
	}
     return timeList;

}
	
 
//时间戳转date
function toDate(timestamp){
	var date = new Date(timestamp);
	Y = date.getFullYear() + '';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '';
	D = date.getDate() <10 ? '0'+date.getDate() : date.getDate();
	return (Y+M+D)*1;
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
			backgroundColor: '#f8f8f8',
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['点击量'],
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
                    data : datatime
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'点击量',
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
                    data:dataclick,
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
