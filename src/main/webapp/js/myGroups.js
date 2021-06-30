var userId=$("#userId").val();

//获取群列表信息
$.post("/api/qun/getGroupByUserId",{"userId":userId},function (res) {
    let data = eval("("+res+")");
    console.log(data);
    let htm='',groupId='',groupMaster='';
    $.each(data,function (k,v) {
        if(k==0){
            htm+='<a href="javascript:;" class="list-group-item active" data-gid="'+v.id+'" data-gmid="'+v.userId+'">'
            groupId=v.id;
            groupMaster=v.userId;
        }else{
            htm+='<a href="javascript:;" class="list-group-item" data-gid="'+v.id+'" data-gmid="'+v.userId+'">'
        }
        htm+='    <span>'+v.groupName+'</span>'
        +'    <span class="badge">'+v.memberCount+'</span>'
        +'    <br>'
        +'    <div class="group_info" title="'+v.description+'"><span>简介：</span>'+v.description+'</div>'
        +'</a>'
    })
    $(".listGroup").html(htm);

    //获取第一个群的成员信息
    getMemberByGroupId(groupId,groupMaster);
})

//点击不同的群
$("body").on('click','.list-group-item',function(){
    let _this = $(this);
    let groupId = _this.attr("data-gid");
    let groupMaster = _this.attr("data-gmid");
    _this.addClass('active').siblings('.list-group-item').removeClass('active');
    getMemberByGroupId(groupId,groupMaster);
})

function getMemberByGroupId(groupId,groupMaster) {
    $.post("/groupUserController/getMemberByGroupId",{"groupId":groupId},function(res) {
        let data = eval("("+res+")");
        //console.log(data);
        let htm='';
        if(userId == groupMaster){
            htm+='<div  class="col-md-12  myGroups_mem">'
                +'    <div class="col-md-8 btn-group myGroups_btns">'
                +'        <button class="btn btn-primary renameGroup_btn" data-toggle="modal" data-groupId="'+groupId+'" data-target="#renameGroup_modal" type="button">修改群信息</button>'
                +'        <button class="btn btn-success addGroupMem_btn" data-toggle="modal" data-groupId="'+groupId+'" id="addMemberBtn" type="button">添加群员</button>'
                +'        <button class="btn btn-danger delGroupMem_btn" data-toggle="modal" data-groupId="'+groupId+'" data-target="#delGroup_modal" type="button">解散群</button>'
                +'    </div>'
                +'</div>'
        }
        $.each(data,function (k,v) {
            htm+='<div class="col-sm-6">'
                +'    <div class="myFriends_each">'
                +'        <div class="myGroups_content">'
                +'            <img class="img-circle img-responsive pull-left" src="'+v.user.avatar+'">'
                +'            <div class="myFriends_all ">'
                +'                <span class="myFriends_block">'+v.user.nickName+'</span>'
                +'                <p title="'+v.user.sign+'">'+v.user.sign.substring(0,15)+'</p>'
           // console.log(v.userId+"---"+userId);
                if(v.userId!=userId && userId==groupMaster){
                    htm+='   <button class="btn btn-primary btn-xs myFriends_block removeGroup"  data-groupId="'+groupId+'" data-mid="'+v.userId+'" type="button">移出群</button>'
                }else if (v.userId==userId && userId!=groupMaster){
                    htm+='   <button class="btn btn-primary btn-xs myFriends_block quitGroup" data-groupId="'+groupId+'" type="button">退群</button>'
                }
                htm+= '            </div>'
                +'        </div>'
                +'    </div>'
                +'</div>'
        })
        $(".qunMemberList").html(htm);
    })
}

//确认添加分组按钮
$("body").on("click","#creatGroup_sure",function(){

    let groupName=$("#creatGroup_Name").val().trim();
    let groupSign=$("#addGroup_sign").val().trim();
    let patrn=/[\"\'<>``!@#$%^&*+-\/\/\/\\//?,.]/;
    if (patrn.exec(groupName)){
        layer.msg("不允许输入英文状态下的特殊字符！");
        return false;
    }
    if(groupName.length>20){
        layer.msg("群名长度不能超过20！");
        return false;
    }
    if(addGroup_sign.length>30){
        layer.msg("讨论组描述长度不能超过30！");
        return false;
    }
    if(groupName=="" || groupName==null){
        layer.msg("群名不能为空！");
        return false;
    }
    if(addGroup_sign=="" || addGroup_sign==null){
        layer.msg('群描述不能为空！');
        return false;
    }else{
        $.post('/groupController/add',{"groupName":groupName,"description":groupSign,"userId":userId},function(result){
            if(result>0){
                layer.msg("添加成功!");
                $("#addChatGroup_modal").modal('hide');
                window.location.reload()
            }else{
                layer.msg('操作失败!');
            }
        })
    }
})


//重命名分组的--确定按钮
$("#reName-type-btn").click(function(){
    let groupChangeId=$(".renameGroup_btn").attr("data-groupId");
    let newName=$("#reNameGroupInput").val();
    let newSign=$("#reSignGroupInput").val();
    /*alert(newName);
    alert(userId);*/
    let patrn=/[\"\'<>``!@#$%^&*+-\/\/\/\\//?,.]/;
    if (patrn.exec(newName))
    {
        layer.msg("不允许输入英文状态下的特殊字符！");
        return;
    }
    if(newName=="" || newName==null){
        layer.msg('讨论组名不能为空！');
        return;
    }else if(newName.trim().length>20){
        layer.msg('群名长度不能超过20！');
        return;
    }else if(newSign=="" || newSign==null){
        layer.msg('讨论组描述不能为空！');
        return;
    }else{
       // alert("gid"+groupChangeId+"  gname="+newName+"  ddd"+newSign)
        $.post("/groupController/update",{"id":groupChangeId,"groupName":newName,"description":newSign},function(result) {
            //console.log("======"+groupChangeId)
            if(result>0){
                $('#renameGroup_modal').modal('hide');
                location.reload();
            }else{
                layer.msg('操作失败!');
            }
        })
    };

})

//删除群组（里面成员被踢出群）
$("body").on('click','.delGroupMem_btn',function(){
    let groupId=$(this).attr('data-groupId');
    // console.log("typeId=="+typeId)
    //alert(groupId);
    if(confirm("确定要删除该群吗？")){
        $.post("/groupController/delete",{"groupId":groupId},function(result) {
            //alert(result);
            if(result>0){
                window.location.reload();
            }else{
                layer.msg('删除失败!');
            }
        })
    }else{
        return;
    }
})

// 移出群成员
$("body").on("click",".removeGroup",function(){
    let mid=$(this).attr("data-mId");
    let gid=$(this).attr("data-groupId");
    if(confirm("确定要移出该好友吗？")){
        $.post('/groupController/quit',{"groupId":gid,"userId":mid},function(result){
            if(result>0){
                window.location.reload();
            }else{
                layer.msg('删除失败!');
            }
        });
    }else{
        return;
    }
})

//退出群
$("body").on("click",".quitGroup",function(){
    let gid=$(this).attr("data-groupId");
    if(confirm("确定要退出群吗？")){
        $.post('/groupController/quit',{"groupId":gid,"userId":userId},function(result){
            if(result>0){
                window.location.reload();
            }else{
                layer.msg('退出失败!');
            }
        });
    }else{
        return;
    }
})

//"添加组员"按钮
$("body").on('click','#addMemberBtn',function(){
    let groupId=$(this).attr("data-groupId");
    $('#addMember_modal').modal('show');
    getAllFriendToAdd(groupId);
})

function getAllFriendToAdd(groupId){
    $.post("/groupUserController/getNewMemberByGroupId",{"groupId":groupId,"userId":userId}, function(result) {
        let data = eval("(" + result + ")");
        let htm="";
        if(data.length) {
            $.each(data, function (k, v) {
                htm += '    <a href="javascript:;" class="addMember_li" data-userId="' + v.id + '">'
                    + '        <div class="media addMember_choose">'
                    + '            <div class="media-left">'
                    + '                <img class="media-object" src="' + v.avatar + '" alt="图片加载失败">'
                    + '            </div>'
                    + '            <div class="media-body">'
                    + '                <h4 class="media-heading">' + v.userName + '</h4>'
                    + '                <h4 class="media-heading">' + v.nickName + '</h4>'
                    + '            </div>'
                    + '        </div>'
                    + '    </a>'
            })
            $(".addMember_ul").html(htm)
        }else{
            $('#addMember_modal').modal('hide');
            layer.msg("没有可邀请的好友哦！");
        }
    })
}

// 添加群成员的"确定"按钮
$("body").on('click','.addMember_btn',function(){
    let groupId=$("#addMemberBtn").attr("data-groupId");
    let userIds="";
    $(".addMember_ul a").each(function(){
        if($(this).attr("class").indexOf("active")>-1){
            let a = $(this).attr('data-userId')+",";
            userIds+=a;
        }
    })
    if(userIds.length==0){
        layer.msg("请选择组员！");
        return;
    }
    $.post("/groupUserController/batchAdd",{"userIds":userIds,"groupId":groupId},function(result) {
        if(result){
            $('#addMember_modal').modal('hide');
            location.reload();
        }else{
            layer.msg("操作失败！")
        }
    })
})

$("body").on('click','a.addMember_li',function(){
    let _this=$(this);
    let act=_this.attr('class').indexOf('active');
    if (act == -1){
        _this.addClass('active');
    }else{
        _this.removeClass('active');
    }
})