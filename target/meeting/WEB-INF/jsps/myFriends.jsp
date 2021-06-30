<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>聊酷宝</title>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="/css/myFriends.css"/>
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
        </div><%--导航条头部navbar-header结束--%>

        <%--导航条内容开始--%>
        <div class="collapse navbar-collapse" id="king-navbar">
            <ul class="nav navbar-nav">
                <li><a href="/index/searchFriends" >查找好友</a></li>
                <li class="active"><a href="/index/myFriends" >好友管理<span class="sr-only">(current)</span></a></li>
                <li><a href="/index/myGroups" >群管理</a></li>
                <li><a href="/index/myInfo" >我</a></li>
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
                        <%--<a href="#" class="list-group-item active">--%>
                            <%--全部好友<span class="badge">7</span>--%>
                        <%--</a>--%>
                        <%--<a href="#" class="list-group-item">好友<span class="badge">2</span></a>--%>
                        <%--<a href="#" class="list-group-item">2016年同事<span class="badge pull-right">3</span></a>--%>
                        <%--<a href="#" class="list-group-item">test分组<span class="badge pull-right">4</span></a>--%>
                    </div>    <!-- list-group1结束 -->

                    <%--btnGroup开始--%>
                    <div class="btnGroup">

                        <div class="btn-group btn-group-justified">
                            <a class="btn btn-default" type="button" data-toggle="modal" href="#addGroup_modal">添加分组</a>
                        </div>
                        <%--addGroup_modal开始--%>
                        <div class="modal  fade" id="addGroup_modal" tabindex="-1">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myModalLabel">添加分组</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>
                                            <input type="text" class="form-control" id="add-type-name" placeholder="请输入新的分组名">
                                        </p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-success" id="add-type-btn">添加</button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div><!-- btnGroup结束 -->

                </div><!-- myFriends_left结束 -->


            <div class="modal  fade" id="renameGroup_modal" tabindex="-1">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabelFix">分组重命名</h4>
                        </div>
                        <div class="modal-body">
                            <p>
                                <input type="text" class="form-control" id="reNameTypeInput" placeholder="请输入新的分组名">
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary typeReNameBtn">确定</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal  fade in" id="delGroup_modal" tabindex="-1">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel1">删除分组</h4>
                        </div>
                        <div class="modal-body">
                            <p>
                                删除该分组后，其下好友将移到默认[我的好友]分组，确定要删除吗？
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger btn-xs typeDelBtn">删除</button>
                            <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <%--<div class="modal fade in" id="delFriends_modal" tabindex="-1">--%>
                <%--<div class="modal-dialog modal-sm">--%>
                    <%--<div class="modal-content">--%>
                        <%--&lt;%&ndash;<div class="modal-header">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<h4 class="modal-title" id="myModalLabel">删除分组</h4>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--<div class="modal-body">--%>
                            <%--<p>--%>
                                <%--删除该好友后，您将无法查看他的任何信息，确定要删除吗？--%>
                            <%--</p>--%>
                        <%--</div>--%>
                        <%--<div class="modal-footer">--%>
                            <%--<button type="button" class="btn btn-danger btn-xs delFriends">删除</button>--%>
                            <%--<button type="button" class="btn btn-default btn-xs" data-dismiss="modal">取消</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>


                <!-- myFriends_right开始 -->
                <div class="container container-fluid col-md-9 col-sm-12 myFriends_right">
                    <div class="row">
                        <%--<div class="row"></div>--%>
                        <div class="col-md-12 col-sm-12 myFriends_group">
                            <button class="btn btn-primary renameGroup_btn" data-toggle="modal" data-target="#renameGroup_modal" type="button">分组重命名</button>
                            <button class="btn btn-danger delGroup_btn" data-toggle="modal" data-target="#delGroup_modal" type="button">删除分组</button>
                        </div>
                        <div class="col-md-12 col-sm-12" id="myFriendDiv" >
                        </div>
                        <%--<div class="col-md-4">--%>
                            <%--<div class="myFriends_each">--%>
                                <%--<img class="img-circle img-responsive pull-left" src="../../images/boy-09.png">--%>
                                <%--<div class="myFriends_all ">--%>
                                    <%--<span class="myFriends_block">梭梭酱</span>--%>
                                    <%--<select class="myFriends_block">--%>
                                        <%--<option selected>好友</option>--%>
                                        <%--<option>2016同事</option>--%>
                                        <%--<option>test分组</option>--%>
                                    <%--</select>--%>
                                    <%--<button class="btn button-default btn-xs myFriends_block" type="button" data-toggle="modal" data-target="#delFriends_modal">删除</button>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div><%--myFriends_right结束--%>

        </div><!-- myFriends_md12结束 -->
    </div><%--row结束--%>
</div><%--myFriends_colume结束--%>

<script src="/js/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/im.js"></script>
<script src="/js/myFriends.js"></script>
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

