<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>聊酷宝</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="/layim/css/layui.mobile.css" media="all"/>
</head>
<body>
<input type="hidden" id="userId" value="${user.id}"/>
<input type="hidden" id="roleCode" value="${user.roleCode}"/>
<script src="/js/jquery.min.js"></script>
<script src="/layim/layui.js"></script>
<script src="/layim/wap.js"></script>
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
</body>
</html>

