<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>聊酷宝</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="/layim/css/layui.css" media="all"/>
</head>
<body>
<input type="hidden" id="userId" value="${user.id}"/>
<input type="hidden" value="" id="uuid">
<a class="layui-btn" href="/index/logout">退出登录</a>

<script src="/js/jquery.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/im.js"></script>

</body>
</html>

