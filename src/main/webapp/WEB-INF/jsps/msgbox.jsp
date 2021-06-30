<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>好友申请消息盒子</title>
<link rel="stylesheet" href="/layim/css/layui.css">
<style>
.layim-msgbox{margin: 15px;}
.layim-msgbox li{position: relative; margin-bottom: 10px; padding: 0 130px 10px 60px; padding-bottom: 10px; line-height: 22px; border-bottom: 1px dotted #e2e2e2;}
.layim-msgbox .layim-msgbox-tips{margin: 0; padding: 10px 0; border: none; text-align: center; color: #999;}
.layim-msgbox .layim-msgbox-system{padding: 0 10px 10px 10px;}
.layim-msgbox li p span{padding-left: 5px; color: #999;}
.layim-msgbox li p em{font-style: normal; color: #FF5722;}

.layim-msgbox-avatar{position: absolute; left: 0; top: 0; width: 50px; height: 50px;}
.layim-msgbox-user{padding-top: 5px;}
.layim-msgbox-content{margin-top: 3px;}
.layim-msgbox .layui-btn-small{padding: 0 15px; margin-left: 5px;}
.layim-msgbox-btn{position: absolute; right: 0; top: 12px; color: #999;}
</style>
</head>
<body>
<input id="userId" type="hidden" value='${userId}'>
<input type="hidden" value="" id="uuid">
<ul class="layim-msgbox" id="LAY_view"></ul>

<script src="/layim/layui.js"></script>
<script>
layui.use(['layim', 'flow'], function(){
  var layim = layui.layim
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,$ = layui.jquery
  ,flow = layui.flow;
  var cache = {};   //用于临时记录请求到的数据
  var userId=$("#userId").val();

  flow.load({
	    elem: '#LAY_view' //指定列表容器
	    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
	      var lis = [];
	      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
	      //申请添加你为好友
	        $.ajax({
	      		url:"/friendApplyController/getByToUserId",
	      		type: "POST",
	      		async : true,
	      		data: {"toUserId":userId,"status":0,"pageNum":page,"pageSize":5},
	      		dataType: "json",
	      		success: function(res){	
	      			//console.log(res);
	    	     	layui.each(res.list, function(index, item){
	    	     		cache[item.fromUserId] = item.fromUser;
	    	     		var htm='<li data-id="'+item.id+'" data-uid="'+item.fromUserId+'" data-tid="'+item.friendTypeId+'">'
	    	     		+'  <a href="javascript:;">'
	    	     		+'    <img src="'+item.fromUser.avatar+'" class="layui-circle layim-msgbox-avatar">'
	    	     		+'  </a>'
	    	     		+'  <p class="layim-msgbox-user">'
	    	     		+'    <a href="javascript:;">'+item.fromUser.nickName+'</a>'
	    	     		+'    <span>'+item.applyTime.substring(0,16)+'</span>'
	    	     		+'  </p>'
	    	     		+'  <p class="layim-msgbox-content">'
	    	     		+' 申请添加你为好友  '
	    	     		+'    <span>附言: '+item.remark+'</span>'
	    	     		+'  </p>'
	    	     		+'  <p class="layim-msgbox-btn">'
	    	     		+'    <button class="layui-btn layui-btn-small" data-type="agree">同意</button>'
	    	     		+'    <button class="layui-btn layui-btn-small layui-btn-danger" data-type="refuse">拒绝</button>'
	    	     		+'  </p>'
	    	     		+'</li>'
	    		    	lis.push(htm);
	      			});
			        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
			        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
			        next(lis.join(''), page < res.pages);
	      		}
	        });
	      }
	 });
 
  
  //操作
  var active = {
    agree: function(othis){
      var li = othis.parents('li'),id = li.data('id'),uid = li.data('uid'),typeId=li.data('tid'),user = cache[uid];

      console.log("totypeId="+typeId)
      //选择分组
      parent.layui.layim.setFriendGroup({
        type: 'friend'
        ,username: user.nickName
        ,avatar: user.avatar
        ,group: parent.layui.layim.cache().friend //获取好友分组数据
        ,submit: function(group, index){
    	   $.ajax({
    			url:"/friendApplyController/updateStatus",
    			type: "POST",
    			async : true,
    			data: {"id":id,"status":1,"fromTypeId":group,"toTypeId":typeId},
    			dataType: "json",
    			success: function(result){			
    				 if(result > 0){
    					 //将好友追加到主面板
    					parent.layui.layim.addList({
   						  type: 'friend'       //列表类型，只支持friend和group两种
   	 					 ,avatar: user.avatar   //好友头像
   	 					 ,username: user.nickName//好友昵称
   	 					 ,groupid: group //所在的分组id
   	 					 ,id: uid 		//好友id
   	 					 ,sign: user.sign //好友签名
   	 				 });  
    			          parent.layer.close(index);
    			          othis.parent().html('已同意');
    				 }else if(result == -1){
    					 layer.alert("你们已经是好友了,请勿重复操作！");
    				 }else{
    					 layer.msg("操作失败！");
    				 }
    			}
    	   })   
          
        }
      });
      
    }
  
    ,refuse: function(othis){
      var li = othis.parents('li'),id = li.data('id'),typeId=li.data('tid');
      layer.confirm('您确定拒绝吗？', function(index){
     	   $.ajax({
     			url:"/friendApplyController/updateStatus",
     			type: "POST",
     			async : true,
     			data: {"id":id,"status":2,"fromTypeId":0,"toTypeId":typeId},
     			dataType: "json",
     			success: function(result){			
     				 if(result>0){
     					  layer.close(index);
     			          othis.parent().html('<em>已拒绝</em>');
     				 }else{
     					 layer.msg("操作失败！");
     				 }
     			}
     	   })
      });
    }
  };

  $('body').on('click', '.layui-btn', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });
  
});
</script>
</body>
</html>
