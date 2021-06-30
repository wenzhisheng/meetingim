<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>聊酷宝</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/style.min.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>
            <h2 class="logo-name" style="margin:40px auto;">聊酷宝</h2>
        </div>
        <br/>
        <form action="/index/updateByPassword" method="post">
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">账号：</div>
                <input style="width: 75%" type="username" class="form-control" placeholder="账号" name="userName" required/>
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">密码：</div>
                <input style="width: 75%" type="password" class="form-control" placeholder="密码" name="password" required/>
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">确认密码：</div>
                <input style="width: 75%" type="password" class="form-control" placeholder="确认密码" name="confirmPassword" required/>
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">密保问题：</div>
                <input style="width: 75%" type="username" class="form-control" placeholder="密保问题" name="encryptedQuestion" required/>
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">密保答案：</div>
                <input style="width: 75%" type="username" class="form-control" placeholder="密保答案" name="encryptedAnswer" required/>
            </div>
            <button type="submit" id="sbBtn" class="btn btn-primary block full-width m-b">修改密码</button>
            <p class="text-muted text-center" style="font-size:14px;color:red"> ${msg} </p>
            <p class="text-muted text-center">
                <a href="/index/login">返回登录</a> |
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

