var toId=$("#toId").val();
var userId=$("#userId").val();
var type=$("#type").val();
var htm="";
var pageNum=2;
var pageSize=10;
var pageNum2 = 1;
var pageSize2 = 100;
$(document).ready(function(){
  var jsonStr=$("#message").val();
  //console.log(jsonStr)
  var dataObj=eval("("+jsonStr+")");
  //console.log(dataObj);
  getDetail(dataObj);
  $("#moreLog").click(function(){
     $.post('/api/friend/getHistoryMessage',{'id':toId,'type':type,'pageNum':pageNum,'pageSize':pageSize},function(result){
    	 var data=eval("("+result+")");
    	// console.log(data[0].hasNextPage);
    	 if(data[0].hasNextPage == false){
    		 alert("没有更多消息");
    		 return;
    	 }
    	 getDetail(data);
    	 pageNum++;
     });
  });

  $("#searchMessage").click(function(){
	let searchStr = $("#searchStr").val();
	$.post('/api/friend/searchHistoryMessage',{'searchStr':searchStr,'id':toId,'type':type,'pageNum':pageNum2,'pageSize':pageSize2},function(result){
		// console.log(data[0].result);
		var data=eval("("+result+")");
		// console.log(data[0].hasNextPage);
		if(data[0].list == 0){
			alert("没有找到匹配消息");
			return;
		}
		$(".layim-chat-main").remove();//清除本地缓存
		var cache =  layui.layim.cache();
		var local = layui.data('layim')[cache.mine.id]; 	//获取当前用户本地数据
		pageNum++;
	});
  });

	// 清空历史记录
	$("#clearLog").click(function(){
		layui.use(['layim'], function(){
			layer.confirm('确定要清空历史记录吗?', {icon: 3, title:'提示'}, function(index){
				var msgDom = $(".layim-chat-main");
				$.post('/api/friend/clearMsgHistory',{'userId':userId,'toId':toId,'type':type},function(result){
					var res = parseInt(result);
					if(res > 0){
						console.log("清空历史记录成功");
					}
				});
				msgDom.remove();
				layer.close(index);
			});
		});
	});

});

function getDetail(dataObj){
	htm="";
	//console.log(dataObj[0].list);
	var data = dataObj[0].list;
	console.log(data)
	if(type=="friend"){
	   	for(var i=data.length-1;i>=0;i--){
	   		if(data[i].fromUserId == userId){
		     htm+='<li class="layim-chat-mine">'
				 +'  <div class="layim-chat-user">'
				 +'	  <img src="'+data[i].fromAvatar+'">'
				 +'	  <cite><i>'+data[i].sendTime.substring(0,16)+'</i>我</cite>'
				 +'  </div>'
				 +'  <div class="layim-chat-text">'+ parent.layui.layim.content(data[i].content)+'</div>'
				 +'</li>'
			 }else{
			     htm+='<li>'
					 +'  <div class="layim-chat-user">'
					 +'	  <img src="'+data[i].fromAvatar+'">'
					 +'	  <cite>'+data[i].fromName+'<i>'+data[i].sendTime.substring(0,16)+'</i></cite>'
					 +'  </div>'
					 +'  <div class="layim-chat-text">'+parent.layui.layim.content(data[i].content)+'</div>'
					 +'</li>'
			 }
		}
	}else if(type=="group"){
        for(var i=data.length-1;i>=0;i--){
	   		if(data[i].userId == userId){
		     htm+='<li class="layim-chat-mine">'
				 +'  <div class="layim-chat-user">'
				 +'	  <img src="'+data[i].avatar+'">'
				 +'	  <cite><i>'+data[i].sendTime.substring(0,16)+'</i>我</cite>'
				 +'  </div>'
				 +'  <div class="layim-chat-text">'+ parent.layui.layim.content(data[i].content)+'</div>'
				 +'</li>'
			 }else{
			     htm+='<li>'
					 +'  <div class="layim-chat-user">'
					 +'	  <img src="'+data[i].avatar+'">'
					 +'	  <cite>'+data[i].nickName+'<i>'+data[i].sendTime.substring(0,16)+'</i></cite>'
					 +'  </div>'
					 +'  <div class="layim-chat-text">'+parent.layui.layim.content(data[i].content)+'</div>'
					 +'</li>'
			 }
		}
	}

    $("#messageList").prepend(htm);
}
