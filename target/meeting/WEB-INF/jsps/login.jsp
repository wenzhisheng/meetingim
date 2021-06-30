<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>聊酷宝</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/style.min.css?v=4.1.0" rel="stylesheet">
    <script src="/js/jquery.min.js"></script>
</head>
<script type="text/javascript">
    //异步请求接口
    // $.ajax({
    //     url: 'http://www.80049.vip/whiteIP/illegal',
    //     type: 'POST',
    //     data:{id:1},
    //     success:function(response) {
    //         if (response.status == 0){
    //             window.location.href=response.location;
    //         }
    //     }
    // });
</script>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
            <h3 class="logo-name">聊酷宝</h3>
            </div>
            <h4></h4>
			       <form action="/index/loginCheck" method="post">
			              <div class="form-group">
			                  <input type="username" class="form-control" placeholder="用户名" name="userName" required/>
			              </div>
			              <div class="form-group">
			                  <input type="password" class="form-control" placeholder="密码" name="password" required/>
			              </div>
			              <button type="submit" id="sbBtn" class="btn btn-primary block full-width m-b">登录</button>
			               <p class="text-muted text-center" style="font-size:14px;color:red"> ${msg} </p>
					 	   <p class="text-muted text-center">
                               <a href="/index/password">忘记密码</a> |
                               <a href="/index/register">注册账号</a>
                           </p>
			       </form>
        </div>
    </div>

    <div style="width: 100%;position: fixed;bottom: 5px;text-align: center;">
        <h3>© CopyRight 2019-2020</h3>
    </div>
</body>
</html>

