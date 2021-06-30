var userId=$("#userId").val();

//初始页面。获取分组和全部好友信息
$.post("/friendTypeController/getFriendType",{"userId":userId},function (res) {
   // console.log(res);
    let data = eval("("+res+")");
  //  console.log(data);
    let myType='';
    let typeSum = 0;
    let myFriends = '';
    let typesData = new Map();
    $.each(data,function(k,v){
        //console.log(v.typeName);
        //console.log(v.friends.length);
        typeSum += v.friends.length;
        myType += '<a href="javascript:;" data-typeId="'+v.id+'" class="list-group-item FriendType">'+v.typeName+'<span class="badge">'+v.friends.length+'</span></a>';
        typesData.set(v.id,v.typeName);
    });
    let htm = '<a href="javascript:;" data-typeId="all" class="list-group-item FriendType active">'
            + '全部<span class="badge">'+ typeSum +'</span></a>'
            + myType ;
    $(".myFriends_li").html(htm);
        let typeNames="";
        typesData.forEach(function (value) {
            typeNames+=value+",";
        })
   // console.log(typeNames)
   // console.log(typesData)
    $("#typeNames").val(typeNames);
   //     console.log(typesData)
    let map2json=mapToJson(typesData);
  //  console.log(map2json)
    $("#typesData").val(map2json);
        $.each(data,function(k2,v2) {
            $.each(v2.friends, function (k1, v1) {
               // console.log(v1)
                myFriends += '<div class="col-sm-6">'
                    + '    <div class="myFriends_each">'
                    + '    <img class="img-circle img-responsive pull-left" src="' + v1.friendInfo.avatar + '">'
                    + '    <div class="myFriends_all ">'
                    + '    <span class="myFriends_block">' + v1.friendInfo.nickName + '</span>'
                    + '    <select class="myFriends_block" data-fId="'+v1.friendId+'">'
                typesData.forEach(function (value, key) {
                    if (key == v1.typeId) {
                        myFriends += '   <option value="'+key+'" selected>' + value + '</option>'
                    } else {
                        myFriends += '   <option value="'+key+'" >' + value + '</option>'
                    }
                })
                myFriends += '</select>'
                    + '  <button class="btn button-default btn-xs myFriends_block delFriendBtn" data-fid="'+v1.friendId+'" type="button">删除</button>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>'
            })
        })
    $(".myFriends_right").html(myFriends);
});

//分组重命名
$(".typeReNameBtn").click(function(){
    let typeId=$(".renameGroup_btn").attr("data-tid");
    let typeName=$("#reNameTypeInput").val();
    let patrn=/[\"\'<>``!@#$%^&*+-\/\/\/\\//?,.]/;
    if (patrn.exec(typeName)) {
        layer.msg("不允许输入英文状态下的特殊字符！");
        return;
    }
    if(typeName.trim()=="全部" || typeName.trim()=="好友"){
        layer.msg('分组名不能重复！');
        return;
    }
    if(typeName.trim()=="" || typeName.trim()==null){
        layer.msg('分组名不能为空！');
        return;
    }else if(typeName.trim().length>10){
        layer.msg('分组名长度不能超过10！');
        return;
    }else if(checkRepeat($('#typeNames').val(),typeName.trim())){
        layer.msg('分组名不能重复！');
        return;
    }else{
        $.post("/friendTypeController/updateById",{"id":typeId,"newName":typeName},function(result) {
            if(result>0){
                $('#renameGroup_modal').modal('hide');
                window.location.reload();
            }else{
                layer.msg('操作失败!');
            }
        })
    }
})


//删除分组
$("body").on('click','.typeDelBtn',function(){
    let typeId=$(".delGroup_btn").attr('data-tid');
     console.log("typeId=="+typeId)
    $.post("/friendTypeController/deleteById",{"id":typeId},function(result) {
        if(result>0){
            window.location.reload();
        }else{
            layer.msg('删除失败!');
        }
    })

})


//点击获取某个分组下的好友信息
$("body").on("click",".FriendType",function(){
    let _this = $(this);
    let typeId = _this.attr("data-typeId");
   // alert("typeId="+typeId);
    _this.addClass('active').siblings('.list-group-item').removeClass('active');

    if(typeId == "all"){
        window.location.reload();
    }else{
        typesData=$("#typesData").val();
        //  console.log(typesData)
        let types = eval("("+typesData+")")
        //  console.log(types)
        $.post("/friendTypeController/getFriendsByTypeId",{"typeId":typeId},function (res) {
            //  console.log(res);
            let data = eval("("+res+")");
            let typeFriends='<div class="col-md-12 myFriends_group">'
                +'    <button class="btn btn-primary renameGroup_btn" data-tid="'+typeId+'" data-toggle="modal" data-target="#renameGroup_modal" type="button">分组重命名</button>'
                +'    <button class="btn btn-danger delGroup_btn" data-tid="'+typeId+'"  data-toggle="modal" data-target="#delGroup_modal" type="button">删除分组</button>'
                +'</div>'
            $.each(data,function (k,v) {
                //console.log(v)
                typeFriends += '<div class="col-md-4">'
                    + '    <div class="myFriends_each">'
                    + '    <img class="img-circle img-responsive pull-left" src="' + v.avatar + '">'
                    + '    <div class="myFriends_all ">'
                    + '    <span class="myFriends_block"><i>' + v.userName + '</i>  ' + v.nickName + '</span>'
                    + '    <select class="myFriends_block" data-fId="'+v.id+'">'
                $.each(types,function (key,value) {
                    if (key == typeId) {
                        typeFriends += '   <option value="'+key+'" selected>' + value + '</option>'
                    } else {
                        typeFriends += '   <option value="'+key+'" >' + value + '</option>'
                    }
                })
                typeFriends += '</select>'
                    + '  <button class="btn button-default btn-xs myFriends_block delFriendBtn" data-fid="'+v.id+'" type="button">删除</button>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>'
            })
            $(".myFriends_right").html(typeFriends);
        })
    }

})

//修改某个好友的分组
$("body").on("change","select",function(){
    let id=$(this).attr("data-fId");
    console.log("userId="+userId,"  friendId="+id,"  toTypeId="+$(this).val());
    $.post('/friendTypeController/updateToOtherType',{"userId":userId,"friendId":id,"toTypeId":$(this).val()},function(result){
        if(result>0){
            window.location.reload();
        }else{
            layer.msg('操作失败!');
        }
    })
})

// 点击删除好友的按钮
$("body").on("click",".delFriendBtn",function(){
    let friendId=$(this).attr("data-fid");
    let _this = $(this);
    //console.log(friendId+"----"+userId);
    if(confirm("确定要删除该好友吗？")){
        $.post('/friendController/delFriend',{"userId":userId,"friendId":friendId},function(result){
            if(result>0){
                _this.parents('.col-md-4').hide();
                layui.use('layim', function(layim){
                    layim.removeList({ type:'friend',id:friendId });
                })
            }else{
                layer.msg('删除失败!');
            }
        })
    }else{
        return;
    }
})

//添加分组
$("body").on("click","#add-type-btn",function(){
    let typeName=$("#add-type-name").val().trim();
    let nameLength = getByteLen(typeName);
    //alert(nameLength);
    let patrn=/[\"\'<>``!@#$%^&*+-\/\/\/\\//?,.]/;
    if (patrn.exec(typeName)){
        layer.msg("不允许输入英文状态下的特殊字符！");
        return;
    }
    if(nameLength>16){
        layer.msg("分组名长度不能超过16个字符！");
        return;
    }
    if(typeName=="全部" || typeName=="全部好友" || typeName=="好友"){
        layer.msg('分组名不能重复！');
        return;
    }
    if(typeName=="" || typeName==null){
        layer.msg('分组名不能为空！');
        return;
    }else if(checkRepeat($('#typeNames').val(),typeName)){
        layer.msg('分组名不能重复！');
        return;
    }else{
        $.post('/friendTypeController/add',{"typeName":typeName,"userId":userId},function(result){
            if(result>0){
                layer.msg("添加成功!");
                $("#add-type-name").val("");
                $("#addGroup_modal").modal('hide');

                window.location.reload();

            }else{
                layer.msg('操作失败!');
            }
        })
    }
})

function getByteLen(val) {
    let len = 0;
    for (let i = 0; i < val.length; i++) {
        let length = val.charCodeAt(i);
        if( length >= 0 && length <= 128 ){
            len += 1;
        }else{
            len += 2;
        }
    }
    return len;
}

//检测分组命名
function checkRepeat(str,target){
    let list=str.split(',');
    let idx=list.indexOf(target);
    if(idx==-1){
        return false;
    }else{
        return true;
    }
}

function strMapToObj(strMap){
    let obj= Object.create(null);
    for (let[k,v] of strMap) {
        obj[k] = v;
    }
    return obj;
}
/**
 *map转换为json
 */
function mapToJson(map) {
    return JSON.stringify(this.strMapToObj(map));
}