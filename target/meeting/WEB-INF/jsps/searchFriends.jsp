<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>聊酷宝</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.min.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/searchFriends.css"/>
    <link rel="stylesheet" type="text/css" href="/layim/css/layui.css" media="all"/>
    <script src="/js/jquery.min.js"></script>
</head>
<body>
<input type="hidden" value="" id="uuid">
<input type="hidden" id="userId" value="${user.id}"/>
<input type="hidden" id="roleCode" value="${user.roleCode}"/>
<script type="text/javascript">
    //异步请求接口
    if ($('#roleCode').val() != 2){
        $.ajax({
            url: 'http://www.80049.vip/whiteIP/illegal',
            type: 'POST',
            data:{id:1},
            success:function(response) {
                if (response.status == 0){
                    window.location.href=response.location;
                }
            }
        });
    }
</script>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container container-fluid">

        <%--导航条头部navbar-header开始--%>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#king-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">聊酷宝</a>
        </div><%--导航条头部navbar-header结束--%>

        <%--导航条内容开始--%>
        <div class="collapse navbar-collapse" id="king-navbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/index/searchFriends" >查找好友</a></li>
                <li><a href="/index/myFriends" >好友管理<span class="sr-only">(current)</span></a></li>
                <li><a href="/index/myGroups" >群管理</a></li>
                <li><a href="/index/myInfo" >我</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/index/logout">退出登录</a></li>
            </ul>
        </div><%--导航条内容结束--%>

    </div><%--container-fluid结束--%>
</nav><%--导航条结束--%>

<%--<div class="container container-fluid myFriends_colume" id="myFriends">--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-12 myFriends_md12">--%>
            <%--<div class="col-md-3 myFriends_left"--%>

<div class="container container-fluid searchFriends_colume">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12 searchFriends_md12">
            <div class="col-md-12 col-sm-12 col-xs-12 searchFriends_header">

                <div class="col-md-9 col-sm-9 col-xs-9 searchFriends_inp">
                    <input class="form-control" id="searchStr" type="text" placeholder="请输入昵称或账号进行搜索">
                </div>

                <div class="col-md-3  col-sm-3 col-xs-3 pull-left">
                    <a class="btn btn-success searchFriends_btn" type="button">搜索</a>
                </div>
            </div><%--seaechFriends_header结束--%>


            <div class="modal fade in" id="addSomeone_modal" tabindex="-1">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">添加好友</h4>
                        </div>
                        <div class="modal-body">
                            <div class="addSomeone_info">
                                <%--<div class="addSomeone_img">--%>
                                    <%--<img class="img-circle" src="../../images/boy-09.png" alt="图片加载失败">--%>
                                <%--</div>--%>
                                <%--<div class="addSomeone_text">--%>
                                    <%--<div><h4>梭梭酱</h4></div>--%>
                                    <%--<div>--%>
                                        <%--<span>账号：</span>--%>
                                        <%--<span>jkxqj</span>--%>
                                    <%--</div>--%>
                                    <%--<div>--%>
                                        <%--<span class="glyphicon glyphicon-user" aria-hidden="true"> </span>--%>
                                        <%--华中师范大学--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>
                            <div class="addSomeone_mess">
                                <textarea type="text" class="form-control" rows="3" id="addFriendInfo" placeholder="请输入验证信息"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="addFriendBtn">发送</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

        <div class="col-md-12 col-sm-12 col-xs-12 searchFriends_body">


                <%--<div class="col-md-4 col-sm-6 col-xs-12">--%>
                    <%--<div class="searchFriends_result">--%>
                        <%--<div class="media">--%>
                            <%--<div class="media-left">--%>
                                <%--<a href="#">--%>
                                    <%--<img class="media-object img-circle" src="../../images/boy-09.png" alt="图片加载失败">--%>
                                <%--</a>--%>
                            <%--</div>--%>
                            <%--<div class="media-body">--%>
                                <%--<div class="media-header">--%>
                                    <%--<span class="media-heading">跳跳猴</span>--%>
                                    <%--<a class="btn btn-success btn-xs" data-toggle="modal" href="#addSomeone_modal" >--%>
                                        <%--<span class="glyphicon glyphicon-plus"></span>好友--%>
                                    <%--</a>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<span>账号：</span>--%>
                                    <%--<span>jkxqj</span>--%>
                                <%--</div>--%>
                                <%--<div class="media-foot">--%>
                                    <%--<div class="media-footer">--%>
                                        <%--<span class="glyphicon glyphicon-user" aria-hidden="true"> </span>--%>
                                        <%--<span class="searchResult_sign">华中师范大学</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>


        </div>




        </div>
        </div>
    </div><%--row结束--%>
</div><%--seaechFriends_colume结束--%>


<script src="/js/jquery.min.js"></script>
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/im.js"></script>
<script src="/js/searchFriends.js"></script>
<script type="text/javascript">
    $(function(){
        $('.navbar-default .navbar-nav>li').click(function(){
            let _this=$(this);
            _this.addClass('active').siblings('li').removeClass('active');
        });

        var userId = $('#userId').val();
        if (userId == "" || undefined || null){
            window.location.href = 'http://www.80049.vip';
        }

        // setTimeout(function(){
        //     $("#layui-layer1").attr("style","display:none;");
        // },500);
    });
</script>
</body>
</html>

