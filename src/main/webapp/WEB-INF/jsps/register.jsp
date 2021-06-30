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
    <script src="/js/jquery.min.js"></script>
</head>

<body class="gray-bg">
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>
            <h2 class="logo-name" style="margin:40px auto;">聊酷宝</h2>
        </div>
        <form action="/index/insert" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">昵称：</div>
                <input style="width: 75%" type="username" class="form-control" placeholder="账号" name="nickName" required/>
            </div>
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
                <div style="float: left;width: 25%;line-height: 35px;">个性签名：</div>
                <input style="width: 75%" type="text" class="form-control" placeholder="个性签名" name="sign" required/>
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">选择性别：</div>
                保密&nbsp;<input type="radio" name="gender" value="0" title="保密">
                &nbsp;&nbsp;&nbsp;男&nbsp;<input type="radio" name="gender" value="1" title="男" checked>
                &nbsp;&nbsp;&nbsp;女&nbsp;<input type="radio" name="gender" value="2" title="女">
            </div>
            <div class="form-group">
                <div style="float: left;width: 25%;line-height: 35px;">选择头像：</div>
                <div id="getImage"></div>
                <input style="width: 100%;" type="hidden" id="inputID" class="form-control" placeholder="头像" name="avatar" required/>
                <%--<input style="width: 75%" type="file" onclick="this" class="form-control" placeholder="上传头像" name="file" required/>--%>
            </div>
            <button type="submit" id="sbBtn" class="btn btn-primary block full-width m-b">注册账号</button>
            <p class="text-muted text-center" style="font-size:14px;color:red"> ${msg} </p>
            <p class="text-muted text-center">已有账号&nbsp;<a href="/index/login">登录</a></p>
        </form>
    </div>
</div>
<div style="width: 100%;position: fixed;bottom: 5px;text-align: center;">
    <h3>© CopyRight 2019-2020</h3>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $.post("/sns/getImage",function (result) {
            let htm = '';
            $.each(result, (k, v) => {
                if (k === 0) {
                    htm += '<img id="img' + k + '" src="' + v + '" onclick="imgclick(' + k + ')"  class="img-circle pull-left check"/>';
                }else{
                    htm += '<img id="img' + k + '" src="' + v + '" onclick="imgclick(' + k + ')"  class="img-circle pull-left"/>';
                }
            });
            $("#getImage").html(htm);
            $('#inputID').val(result[0]);
            $('#img0').addClass('check');
        });
    });
    let x = []
    let y = 'check'
    function imgclick(k) {
        $('#img0').removeClass('check')
        x.push(k)
        if (x.length > 1) {
            $('#img' + x[0]).removeClass('check')
            x.splice(0, 1)
        }
        $('#img' + x[0]).addClass('check')
        $('#inputID').val($('#img' + x[0]).attr('src'))
    }
</script>
<style type="text/css">
    #getImage{
        width:222px;
        height:144px;
        overflow:scroll;
    }
    #getImage img{
        width:54px;
        height:54px;
        padding:9px 9px;
    }
    .check{
        background: #dedede !important;
    }
</style>
</body>
</html>

