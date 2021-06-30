var userId=$("#userId").val();
var socket = null;  // 判断当前浏览器是否支持WebSocket

if ('WebSocket' in window) {
    socket = new WebSocket("ws://127.0.0.1:8080/LLWS/"+userId);
    // socket = new WebSocket("ws://220.128.125.76:8080/LLWS/"+userId);
} else {
    alert('该浏览器不支持本系统即时通讯功能，推荐使用谷歌或火狐浏览器！');
}

layui.use(['mobile','layer','jquery'], function(){

    var mobile = layui.mobile,layim = mobile.layim, layer = mobile.layer;
    layui.layim = layim;
    layui.layer = layer;

    var data;

    $.ajax({
        type: "post",
        url: "/index/getInitList?userId="+userId,
        cache:false,
        async:false,
        success: function(res){
            data = JSON.parse(res).data;
        }
    })


    //基础配置
    layim.config({

        init:data

        //上传图片接口
        , uploadImage: {
            url: '/sns/uploadFile?userId='+userId //接口地址
            , type: 'post' //默认post
        }

        //上传文件接口
        , uploadFile: {
            url: '/sns/uploadFile?userId='+userId //接口地址
            , type: 'post' //默认post
        }

        //扩展“更多”的自定义列表，下文会做进一步介绍（如果无需扩展，剔除该项即可）
        , moreList: [{
            alias: 'find'
            , title: '更多'
            , iconUnicode: '&#xe628;' //图标字体的unicode，可不填
            , iconClass: '' //图标字体的class类名
        }]

        //扩展工具栏，下文会做进一步介绍（如果无需扩展，剔除该项即可）
        // , tool: [{
        //     alias: 'code' //工具别名
        //     , title: '代码' //工具名称
        //     , iconUnicode: '&#xe64e;' //工具图标，参考图标文档，可不填
        //     , iconClass: '' //图标字体的class类名
        // }]
        ,isgroup:true
        ,copyright:false
        ,notice:true
        ,isNewFriend:true
        ,title:"聊酷宝"
    });

    // 连接发生错误的回调方法
    socket.onerror = function() {
        console.log("ws连接失败!");
    };
    // 连接成功建立的回调方法
    socket.onopen = function() {
        console.log("ws连接成功!");
    }

    // 接收到消息的回调方法
    socket.onmessage = function(res) {
        console.log("ws收到消息啦:" +res.data);
        res = eval("("+res.data+")");
        if(res.type == 'friend' || res.type == 'group'){
            layim.getMessage(res);
        }else{
            layim.setFriendStatus(res.id,res.content);
        }

    }

    // 连接关闭的回调方法
    socket.onclose = function() {
        console.log("ws关闭连接!");
    }
    // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        socket.close();
    }

    // 监听发送消息
    layim.on('sendMessage', function(data){
        var uuid=$("#uuid").val();
        var obj={
            "mine":{
                "avatar":data.mine.avatar,
                "content":data.mine.content,
                "cid":uuid,   //标记消息，用于撤回等操作
                "id":data.mine.id,
                "mine":true,
                "username":data.mine.username
            },
            "to":{
                "avatar":data.to.avatar,
                "id":data.to.id,
                "name":data.to.groupname,
                "sign":data.to.sign,
                "type":data.to.type,
                "username":data.to.username,
                "difftime":data.to.difftime
            }
        }
        var msg=JSON.stringify(obj);
        if(data.to.type=="friend"){
            socket.send(msg);    	//发送消息到websocket服务
            $.post("/api/friend/saveMessage",{"fromUserId":userId,"toUserId":data.to.id,"content":data.mine.content},function(res){
                console.log(res);
            })
        }else if(data.to.type=="group"){
            $.post("/api/qun/getSimpleMemberByGroupId?id="+data.to.id,function(res){
                console.log(res)
                if(res!=null){
                    var obj1={
                        "mine":{
                            "avatar":data.mine.avatar,
                            "content":data.mine.content,
                            "cid":uuid,
                            "id":data.mine.id,
                            "mine":true,
                            "username":data.mine.username
                        },
                        "to":{
                            "avatar":data.to.avatar,
                            "id":data.to.id,
                            "name":data.to.groupname,
                            "sign":data.to.sign,
                            "type":data.to.type,
                            "username":data.to.username,
                            "difftime":data.to.difftime,
                            "memberList":res
                        }
                    }
                    socket.send(JSON.stringify(obj1));  	//发送消息倒webSocket服务
                }
            })
            $.post("/api/qun/saveMessage",{"userId":userId,"groupId":data.to.id,"content":data.mine.content},function(res){
                  // console.log(res);
            })
       }
    });

    //监听在线状态的切换事件
    layim.on('online', function(data){
        if(data=='hide')
            data='offline';
        var obj={
            "mine":{
                "content":data,
                "id":userId
            },
            "to":{
                "type":"onlineStatus"
            }
        }
        $.post("/api/friend/updateOnLineStatus",{"userId":userId,"status":data},function(res){
             // console.log(res);
        })
        socket.send(JSON.stringify(obj));
    });

    //layim建立就绪
    $(function () {
        // 浏览器兼容性问题
        //layim.on('ready',function(){
            //获取离线消息
            $.post("/index/getOfflineMsgFromRedis?userId="+userId,function(res){
               // console.log(res);
                $.each(res,function(k,v){
                    var s = eval('(' + v + ')');
                    layim.getMessage(s);
                });
            });
        // });
    });

    $.post('/friendApplyController/getByToUserId',{"toUserId" : userId,"status" : 0,"pageNum" : 1,"pageSize" : 10 }, function(result) {
        var count=result.total;
       // console.log(count)
        if(count>0){
            layim.msgbox(count);
        }
        //count即为你通过websocket或者Ajax实时获取到的最新消息数量,它将在主面板的消息盒子icon上不断显隐提示，直到点击后自动消失
    });

    //监听查看群员
    layim.on('members', function(data){
      //  console.log(data);
    });

    //监听聊天窗口的切换
    layim.on('chatChange', function(data){
      //  console.log(data);
    });

    layim.on('sign', function(value){
        //console.log(value.length); //获得新的签名
        if(value.length<200){
            $.post("/index/updateUserInfo",{"id":userId,"sign":value},function(result){
                console.log(result);
            })
        }else{
            layer.msg("签名不能超过200字符！")
        }
    });

});




