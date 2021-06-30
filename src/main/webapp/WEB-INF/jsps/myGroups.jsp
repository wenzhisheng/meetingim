<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>聊酷宝</title>
    <link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.min.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/myFriends.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/myGroups.css"/>
    <link rel="stylesheet" type="text/css" href="/layim/css/layui.css" media="all"/>


</head>
<body>
<input type="hidden" value="" id="uuid">
<input type="hidden" id="userId" value="${user.id}"/>

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
                <li><a href="/index/myFriends" >好友管理<span class="sr-only">(current)</span></a></li>
                <li class="active"><a href="/index/myGroups" >群管理</a></li>
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
        <div class="col-md-12 col-sm-12 myFriends_md12">
            <div class="col-md-3 col-sm-12 myFriends_left">

                <div class="list-group listGroup">
                    <a href="#" class="list-group-item active">
                        <span>聊酷宝官方群1</span>
                        <span class="badge">66</span>
                        <br>
                        <div class="group_info" title="啊啊啊啊"><span>简介：</span>聊酷宝官方群1</div>
                    </a>
                </div>
                <!-- list-group1结束 -->

                <%--btnGroup开始--%>
                <div class="btnGroup">
                    <div class="btnMargin"></div>
                    <div class="btn-group btn-group-justified">
                        <a class="btn btn-default" type="button" data-toggle="modal" href="#addChatGroup_modal">新建群</a>
                    </div>
                    <%--addChatGroup_modal开始--%>
                    <div class="modal  fade" id="addChatGroup_modal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabelAdd">新建群</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="addChatGroup_body">
                                        <div class="addChatGroup_content">
                                            <span>群名称：</span>
                                            <input type="text" class="form-control" id="creatGroup_Name" placeholder="请输入群名称">
                                        </div>
                                        <div class="addChatGroup_content">
                                            <span>群简介：</span>
                                            <textarea name="群简介：" id="addGroup_sign" class="form-control addChatGroup_content_area" rows="3" style="resize:none" placeholder="请输入群简介，如讨论话题、人员构成等"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success" id="creatGroup_sure">确定</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div>
                    </div><%--addChatGroup_modal结束--%>

                </div><!-- btnGroup结束 -->

            </div><!-- myFriends_left结束 -->


            <div class="modal fade in" id="renameGroup_modal" tabindex="-1">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" >修改群信息</h4>
                        </div>
                        <div class="modal-body">
                            <div class="renameGroup_body">
                                <div class="renameGroup_content">
                                    <span>群名称：</span><input type="text" class="form-control" id="reNameGroupInput" placeholder="请输入新的群名">
                                </div>
                                <div class="renameGroup_content">
                                    <span>群简介：</span><textarea name="群简介：" class="form-control renameGroup_content_area" id="reSignGroupInput" style="resize:none" rows="3"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary " id="reName-type-btn">确定</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal  fade in" id="delGroup_modal" tabindex="-1">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <%--<div class="modal-header">--%>
                        <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                        <%--<h4 class="modal-title" id="myModalLabel">删除分组</h4>--%>
                        <%--</div>--%>
                        <div class="modal-body">
                            <p>
                                确定要解散该群吗？
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger btn-xs">确定</button>
                            <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade in" id="addMember_modal" tabindex="-1">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">添加群成员</h4>
                        </div>
                        <div class="modal-body">
                            <ul class="addMember_ul">

                                    <%--<a href="#" class="addMember_li">--%>
                                        <%--<div class="media addMember_choose">--%>
                                            <%--<div class="media-left">--%>
                                                <%--<img class="media-object" src="../../images/boy-09.png" alt="图片加载失败">--%>
                                            <%--</div>--%>
                                            <%--<div class="media-body">--%>
                                                <%--<h4 class="media-heading">跳跳猴</h4>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>

                            </ul>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary btn-md addMember_btn">添加</button>
                        </div>
                    </div>
                </div>
            </div>


            <!-- myFriends_right开始 -->
            <div class="container container-fluid col-md-9 col-sm-12 myFriends_right">
                <div class="row qunMemberList">

                    <div  class="col-md-12  col-sm-12 myGroups_mem">
                        <div class="col-md-8 col-sm-8 btn-group myGroups_btns">
                            <button class="btn btn-primary renameGroup_btn" data-toggle="modal" data-target="#renameGroup_modal" type="button">修改群信息</button>
                            <button class="btn btn-success addGroupMem_btn" data-toggle="modal" data-target="#addMember_modal" type="button">添加群员</button>
                            <button class="btn btn-danger delGroupMem_btn" data-toggle="modal" data-target="#delGroup_modal" type="button">解散群</button>
                        </div>
                    </div>

                    <div class="container container-fluid col-md-12 col-sm-12">

                        <div class="col-md-4 col-sm-6">
                            <div class="myFriends_each">
                                <div class="myGroups_content">
                                    <img class="img-circle img-responsive pull-left" src="../../images/boy-09.png">
                                    <div class="myFriends_all ">
                                        <span class="myFriends_block">假行僧</span>
                                        <p>管理员</p>
                                        <button class="btn btn-primary btn-xs myFriends_block" type="button" data-toggle="modal" data-target="#outGroups_modal">移出群</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </div><%--myFriends_right结束--%>

        </div><!-- myFriends_md12结束 -->
    </div><%--row结束--%>
</div><%--myFriends_colume结束--%>


<script src="../../js/jquery.min.js"></script>
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/im.js"></script>
<script src="/js/myGroups.js"></script>
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

        $('.delFriends').click(function(e){
            var _this=$(this);
            _this.parents('.col-md-4').hide();
        });

        var userId = $('#userId').val();
        if (userId == "" || undefined || null){
            window.location.href = 'http://www.80049.vip';
        }

        // setTimeout(function(){
        //     $("#layui-layer1").attr("style","display:none;");
        // },500);

    })

</script>
</body>
</html>

