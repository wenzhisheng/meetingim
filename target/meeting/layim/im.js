var userId=$("#userId").val();
var lockReconnect = false;	//避免ws重复连接
var ws = null;  // 判断当前浏览器是否支持WebSocket
var wsUrl = "ws://127.0.0.1:8080/LLWS/"+userId;
// var wsUrl = "ws://220.128.125.76:8080/LLWS/"+userId;
createWebSocket(wsUrl);   //连接ws

function createWebSocket(url) {
    try{
        if('WebSocket' in window){
            ws = new WebSocket(url);
        }else{
            layui.use(['layer'],function(){
                var layer = layui.layer;
                layer.alert("您的浏览器不支持websocket协议,建议使用新版谷歌、火狐等浏览器，请勿使用IE10以下浏览器，360浏览器请使用极速模式，不要使用兼容模式！");
            });
        }
        initEventHandle();
    }catch(e){
        reconnect(url);
        console.log(e);
    }
}

// 初始化连接
function initEventHandle() {
    ws.onclose = function () {
        reconnect(wsUrl);
        console.log("ws连接关闭!"+new Date().toUTCString());
    };
    ws.onerror = function () {
        reconnect(wsUrl);
        console.log("ws连接错误!");
    };
    ws.onopen = function () {
        heartCheck.reset().start();      //心跳检测重置
        console.log("ws连接成功!"+new Date().toUTCString());
    };
    ws.onmessage = function (event) {    //如果获取到消息，心跳检测重置
        heartCheck.reset().start();      //拿到任何消息都说明当前连接是正常的
        console.log("ws收到消息啦:" +event.data);
        if(event.data!='pong'){
            var obj=eval("("+event.data+")");
            layui.use(['layim'], function(layim){
                if(obj.type=="onlineStatus"){
                    layim.setFriendStatus(obj.id, obj.content);
                }else if(obj.type=="friend" || obj.type=="group"){
                    layim.getMessage(obj);
                }else if(obj.type=="delMsg"){
                    var cid =obj.cid;
                    var type=obj.toType;
                    var toId=obj.toId;
                    var uId=obj.userId;
                    //console.log(cid);
                    $(".layim-chat-main li[data-cid='"+cid+"']").remove();
                    var cache =  layui.layim.cache();
                    var local = layui.data('layim')[cache.mine.id]; 	//获取当前用户本地数据
                    console.log(JSON.stringify(local));
                    var localIndex="";
                    if(type=='friend'){
                        localIndex=type+uId;
                    }else{
                        localIndex=type+toId;
                    }
                    //console.log(localIndex);
                    var s=local.chatlog[localIndex];
                    //console.log(JSON.stringify(s));
                    var array=[];
                    $.each(s,function(k,v){
                        if(v.cid!=cid){
                            array.push(v);
                        }
                    });
                    local.chatlog[localIndex]=array;
                    //向localStorage同步数据
                    layui.data('layim', {
                        key: cache.mine.id
                        ,value: local
                    });
                }
            });
        }
    };
}

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    ws.close();
}

// 保持长连接
function reconnect(url) {
    if(lockReconnect) return;
    lockReconnect = true;
    setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
        createWebSocket(url);
        lockReconnect = false;
    }, 2000);
}

//心跳检测
var heartCheck = {
    timeout: 540000,		//9分钟发一次心跳
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        return this;
    },
    start: function(){
        var self = this;
        this.timeoutObj = setTimeout(function(){
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            ws.send("ping");
            console.log("ping!")
            self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
                ws.close();		//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
            }, self.timeout)
        }, this.timeout)
    }
}

layui.use(['layim','layer','jquery'], function(layim){

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

    layim.config({
        init: data
        ,brief: false
        //查看群员接口
        ,members: {
            url: '/api/qun/getByGroupId'
        }
        ,uploadImage: {
            url: '/sns/uploadFile?userId='+userId
            ,type: '' //默认post
        }
        ,uploadFile: {
            url: '/sns/uploadFile?userId='+userId
            ,type: '' //默认post
        }
        ,min:false
        ,find:''  // 查找好友
        ,title: '聊酷宝'        //主面板最小化后显示的名称
        ,chatLog: '/api/friend/getHistoryMessagePage'  //聊天记录地址
        ,copyright: false          //是否授权
        ,right: '10px'
        ,notice:true      //开启桌面消息提醒
        ,msgbox:'/api/friend/msgBoxPage' //消息盒子页面地址，若不开启，剔除该项即可
    });

    // 监听发送消息
    layim.on('sendMessage', function(data){
        var uuid = $("#uuid").val();
        var obj={
            "mine":{
                "avatar":data.mine.avatar,
                "content":data.mine.content,
                "cid":uuid,   //标记消息，用于撤回等操作
                "id":data.mine.id,
                "mine":true,
                "username":data.mine.username,
                "difftime":data.mine.difftime
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
        var msg = JSON.stringify(obj);
        console.log(msg);
        if(data.to.type=="friend" || data.to.type=="fankui"){
            ws.send(msg);    	//发送消息到websocket服务
            $.post("/api/friend/saveMessage",{"message":msg},function(res){
                if(res=="1"){
                    console.log("friend消息已存数据库")
                }
            });
        }else if(data.to.type=="group"){
            $.post("/api/qun/getSimpleMemberByGroupId?groupId="+data.to.id,function(res){
                console.log(res)
                if(res!=null){
                    var obj1={
                        "mine":{
                            "avatar":data.mine.avatar,
                            "content":data.mine.content,
                            "cid":uuid,
                            "id":data.mine.id,
                            "mine":true,
                            "username":data.mine.username,
                            "difftime":data.mine.difftime
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
                    ws.send(JSON.stringify(obj1));  	//发送消息倒webSocket服务
                }
            })
            $.post("/api/qun/saveMessage",{"message":msg},function(res){
                if(res == 1){
                    console.log("group消息已存数据库")
                }
            });
       }
    });

    //监听在线状态的切换事件
    layim.on('online', function(data){
        console.log(data);
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
             console.log(res);
        })
        ws.send(JSON.stringify(obj));
    });

    //layim建立就绪
    $(function () {
        // 浏览器兼容性问题
        // layim.on('ready',function(){
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

    // 截图粘贴发送
    var imgReader = function( item ){
        var blob = item.getAsFile(),
            reader = new FileReader();
        reader.onload = function( e ){
            imgSrc = e.target.result;
            // layer.confirm('是否要发送该截图?', {icon: 3, title:'发送截图'}, function(index){
                $.post('/sns/uploadPaste?userId='+userId,{imageBase64Content:imgSrc},function(result){
                    if(result.code == 0){
                        //这里注意要使用layim提供的内置标签哦
                        $('.layim-chat-textarea textarea').val($('.layim-chat-textarea textarea').val()+result.data.src);
                        layer.close(index);
                    }else{
                        alert(result.msg);
                    }
                },'json');
            //     layer.close(index);
            // });
        };
        reader.readAsDataURL( blob );
    };
    try{
        $('body').unbind('paste',".layim-chat-textarea textarea").bind('paste',".layim-chat-textarea textarea",function(e){
            var clipboardData = event.clipboardData || window.clipboardData || event.originalEvent.clipboardData;
            var   i = 0, items, item, types;
            if( clipboardData ){
                items = clipboardData.items;
                if( !items ){
                    return;
                }
                item = items[0];
                types = clipboardData.types || [];
                for(var i = 0; i < types.length; i++ ){
                    if( types[i] === 'Files' ){
                        item = items[i];
                        break;
                    }
                }
                if( item && item.kind === 'file' && item.type.match(/^image\//i) ){
                    imgReader( item );
                }
            }
        });
    }catch (e){console.log(e)}

    // 两分钟内消息撤回
    $("body").on("mousedown",".layim-chat-mine>.layim-chat-text",function(e){
        if(e.which == 3){
            var cid =$(e.target).parent().data("cid")==undefined?$(e.target).parent().parent().data("cid"):$(e.target).parent().data("cid");
            var msgDom=$(".layim-chat-mine[data-cid='"+cid+"']");
            var time= msgDom.find("i").html();  //消息发布时间字符串
            var now=new Date();   //当前时间
            var msgTime = new Date(Date.parse(time.replace(/-/g,"/")));   	 //消息发布时间转换成Date格式
            console.log(now+"---"+msgTime);
            if((now - msgTime)/60000 < 2){    //只能撤回2分钟内的消息
                layer.confirm('确定要撤回消息吗?', {icon: 3, title:'提示'}, function(index){
                    //console.log(cid);
                    // var windowName=msgDom.find(".layim-chat-other>img").attr("class");
                    var windowName =$(e.target).parent().parent().parent().find(".layim-chat-other>img").attr("class")==undefined?$(e.target).parent().parent().parent().parent().parent().find(".layim-chat-other>img").attr("class"):$(e.target).parent().parent().parent().find(".layim-chat-other>img").attr("class");
                    console.log(msgDom.parent().parent().parent().find(".layim-chat-other>img").attr("class"))
                    var arr=windowName.split(" ");
                    var localIndex = arr[0].substring(6,windowName.length);
                    //console.log(localIndex);
                    var cache =  layui.layim.cache();
                    var local = layui.data('layim')[cache.mine.id]; 	//获取当前用户本地数据
                    //console.log(JSON.stringify(local));
                    var s=local.chatlog[localIndex];
                    //console.log(JSON.stringify(s));
                    var type=s[0].type;
                    var toId=s[0].id;
                    $.post("/api/friend/delMsgFromMongoByCid",{"cid":cid,"type":type},function(res){
                        if(res == 1){
                            console.log("消息撤回成功");
                        }
                    });
                    console.log(type);
                    var array=[];
                    $.each(s,function(k,v){
                        if(v.cid!=cid){
                            array.push(v);
                        }
                    });
                    local.chatlog[localIndex]=array;
                    //向localStorage同步数据
                    layui.data('layim', {
                        key: cache.mine.id
                        ,value: local
                    });
                    //console.log(local.chatlog[localIndex])
                    if(type=='friend'){
                        var obj={
                            "mine":{
                                "id":userId
                            },
                            "to":{
                                "type":"delMsg",
                                "cid":cid,
                                "id":toId,
                                "toType":type
                            }
                        };
                        ws.send(JSON.stringify(obj));
                    }else{
                        $.post("/api/qun/getSimpleMemberByGroupId?groupId="+toId,function(res){
                            console.log(res);
                            if(res!=null){
                                var obj1={
                                    "mine":{
                                        "id":userId
                                    },
                                    "to":{
                                        "type":"delMsg",
                                        "cid":cid,
                                        "id":toId,
                                        "toType":type,
                                        "memberList":res
                                    }
                                }
                                ws.send(JSON.stringify(obj1));  	//发送消息倒Socket服务
                            }
                        })
                    }
                    msgDom.remove();
                    layer.close(index);
                });
            }else{
                layer.msg("超过2分钟的消息无法撤回！",{time:1000});
            }
        }
    });

});

document.oncontextmenu=function(ev){
    return false;    //屏蔽右键菜单
};
