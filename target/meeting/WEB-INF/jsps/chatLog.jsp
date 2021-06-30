<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>聊天记录</title>
	<link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.min.css" media="all"/>
	<link rel="stylesheet" type="text/css" href="/layim/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="/layim/css/modules/layim/layim.css" />
	<link rel="stylesheet" type="text/css" href="/layim/css/modules/layim/chatting.css" />
	<link rel="stylesheet" type="text/css" href="../../css/common.css"/>
	<link rel="stylesheet" type="text/css" href="../../css/searchFriends.css"/>
  </head>
  <body>

  	  <p class="showMore"><a href="javascript:;" id="clearLog">清空历史记录</a></p>
	  <br/>
  	  <p class="showMore"><a href="javascript:;" id="moreLog">查看更多消息</a></p>
  	  
	  <div class="layim-chat-main">
		  <ul id="messageList">
		  
		<!--  <li>
				  <div class="layim-chat-user">
					  <img src="http://tva1.sinaimg.cn/crop.7.0.736.736.50/bd986d61jw8f5x8bqtp00j20ku0kgabx.jpg">
					  <cite>Hi<i>2016-8-19 10:15:26</i></cite>
				  </div>
			  	  <div class="layim-chat-text">嗨，你好！该模块尚在开发中</div>
			  </li>
			  
			  <li class="layim-chat-mine">
				  <div class="layim-chat-user">
					  <img src="http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg">
					  <cite><i>2016-8-19 10:47:42</i>纸飞机</cite>
				  </div>
				  <div class="layim-chat-text">您好</div>
			  </li>   -->
			  
		  </ul>
	  </div>
	  
	 <input id="message" type="hidden" value='${jsonStr}'>
	 <input id="toId" type="hidden" value='${toId}'>
	 <input id="type" type="hidden" value='${type}'>
	 <input id="userId" type="hidden" value='${sessionScope.user.id}'>

	  <div class="col-md-12 col-sm-12 col-xs-12 searchFriends_header"
		   style="position: fixed;top: 0;padding-top: 5px;padding-bottom: 5px;">
		  <div class="col-md-9 col-sm-9 col-xs-9 searchFriends_inp">
			  <input class="form-control" id="searchStr" type="text" placeholder="输入搜索匹配内容">
		  </div>
		  <div class="col-md-3  col-sm-3 col-xs-3 pull-left">
			  <a class="btn btn-success searchFriends_btn" id="searchMessage" type="button">搜索</a>
		  </div>
	  </div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/layim/chatLog.js"></script>
<script type="text/javascript" src="/layim/layui.js"></script>
</body>
</html>
