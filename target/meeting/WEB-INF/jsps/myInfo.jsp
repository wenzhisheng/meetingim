<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>聊酷宝</title>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="/css/myInfo.css"/>
    <link rel="stylesheet" type="text/css" href="/layim/css/layui.css" media="all"/>

</head>
<body >
<input type="hidden" value="" id="uuid">
<input type="hidden" id="userId" value="${user.id}"/>
<input type="hidden" id="typeNames" value="">
<input type="hidden" id="typesData" value="">

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container container-fluid">
        <!--导航条头部navbar-header开始-->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#king-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">聊酷宝</a>
        </div>
        <%--导航条头部navbar-header结束--%>
        <%--导航条内容开始--%>
        <div class="collapse navbar-collapse" id="king-navbar">
            <ul class="nav navbar-nav">
                <li><a href="/index/searchFriends" >查找好友</a></li>
                <li><a href="/index/myFriends" >好友管理<span class="sr-only">(current)</span></a></li>
                <li><a href="/index/myGroups" >群管理</a></li>
                <li class="active"><a href="/index/myInfo" >我</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/index/logout">退出登录</a></li>
            </ul>
        </div><%--导航条内容结束--%>

    </div><%--container-fluid结束--%>
</nav><%--导航条结束--%>

<div class="container container-fluid myFriends_colume" id="myFriends">
    <div class="row">
        <div class="col-md-12 myFriends_md12">
                <div class="col-md-3 myFriends_left">
                    <div class="list-group myFriends_li ">
                    </div>    <!-- list-group1结束 -->
                    <%--btnGroup开始--%>
                    <div class="btnGroup">
                        <div class="btn-group btn-group-justified">
                            <a class="btn btn-default" type="button" data-toggle="modal" href="#addGroup_modal">设置密保</a>
                        </div>
                        <%--addGroup_modal开始--%>
                        <div class="modal  fade" id="addGroup_modal" tabindex="-1">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myModalLabel">修改密保</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>
                                            密保问题：<input type="text" class="form-control" id="encryptedQuestion" placeholder="请输入密保问题">
                                        </p>
                                        <p>
                                            密保答案：<input type="text" class="form-control" id="encryptedAnswer" placeholder="请输入密保答案">
                                        </p>
                                        <p>
                                            登录密码：<input type="text" class="form-control" id="password" placeholder="请输入登录密码">
                                        </p>
                                    </div>
                                    <div class="modal-footer" style="text-align: center;">
                                        <button type="button" class="btn btn-success" id="add-type-btn">确认</button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!-- btnGroup结束 -->
                </div><!-- myFriends_left结束 -->
                <!-- myFriends_right开始 -->
                <div class="container container-fluid col-md-9 col-sm-12 myFriends_right">
                    <div class="row">
                        <div class="col-md-12 col-sm-12" id="myFriendDiv" >
                        </div>
                    </div>
                </div><%--myFriends_right结束--%>
        </div><!-- myFriends_md12结束 -->
    </div><%--row结束--%>
</div><%--myFriends_colume结束--%>


<script src="/js/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/im.js"></script>
<script src="/js/myInfo.js"></script>

<script type="text/javascript">
    $(function(){
        $('.navbar-default .navbar-nav>li').click(function(){
            var _this=$(this);
            _this.addClass('active').siblings('li').removeClass('active');
        });

        $('.list-group-item').click(function(){
            var _this=$(this);
            _this.addClass('active').siblings('.list-group-item').removeClass('active');
        });

        var userId = $('#userId').val();
        if (userId == "" || undefined || null){
            window.location.href = 'http://www.80049.vip';
        }
        // $('.delFriends').click(function(e){
        //     var _this=$(this);
        //     _this.parents('.col-md-4').hide();
        // })

        // setTimeout(function(){
        //     $("#layui-layer1").attr("style","display:none;");
        // },500);

    })

</script>
</body>
</html>

