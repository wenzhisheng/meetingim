var userId=$("#userId").val();


$("body").on('click','.searchFriends_btn',function () {
    let searchStr = $("#searchStr").val();
    $.post("/friendController/searchFriends",{"searchStr":searchStr},function (res) {
        //console.log(res);
        let data = eval("("+res+")");
       // console.log(data);
        let htm='';
        $.each(data,function (k,v) {
            htm+='<div class="col-md-4 col-sm-6 col-xs-12">'
                +'    <div class="searchFriends_result">'
                +'        <div class="media">'
                +'            <div class="media-left">'
                +'                <a href="#">'
                +'                    <img class="media-object img-circle" src="'+v.avatar+'" alt="图片加载失败">'
                +'                </a>'
                +'            </div>'
                +'            <div class="media-body">'
                +'                <div class="media-header">'
                +'                    <span class="media-heading">'+v.nickName+'</span>'
                +'                    <a class="btn btn-success btn-xs addSomeone" data-uid="'+v.id+'" data-username="'+v.userName+'" data-nickname="'+v.nickName+'" data-avatar="'+v.avatar+'" data-sign="'+v.sign+'" >'
                +'                        <span class="glyphicon glyphicon-plus"></span>好友'
                +'                    </a>'
                +'                </div>'
                +'                <div>'
                +'                    <span>账号：</span>'
                +'                    <span>'+v.userName+'</span>'
                +'                </div>'
                +'                <div class="media-foot">'
                +'                    <div class="media-footer">'
                +'                        <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>'
                +'                        <span class="searchResult_sign">'+v.sign+'</span>'
                +'                    </div>'
                +'                </div>'
                +'            </div>'
                +'        </div>'
                +'    </div>'
                +'</div>'
        })
        $(".searchFriends_body").html(htm);
    })

})

//"+好友"按钮
$("body").on('click','.addSomeone',function(){
    let userId=$(this).attr("data-uid");
    let nickName=$(this).attr("data-nickname");
    let userName=$(this).attr("data-username");
    let avatar=$(this).attr("data-avatar");
    let sign=$(this).attr("data-sign");
    $('#addSomeone_modal').modal('show');
    console.log($('#addFriendInfo').val())
    $('#addFriendInfo').attr("value","");
    $(".addSomeone_info").html('<div class="addSomeone_img">'
        +'    <img class="img-circle" src="'+avatar+'" alt="图片加载失败">'
        +'</div>'
        +'<div class="addSomeone_text">'
        +'    <div><h4>'+nickName+'</h4></div>'
        +'    <div>'
        +'        <span>账号：</span>'
        +'        <span id="addUserId" data-uid="'+userId+'">'+userName+'</span>'
        +'    </div>'
        +'    <div>'
        +'        <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>'
        + sign
        +'    </div>'
        +'</div>')
})


//"发送"按钮
$('#addFriendBtn').on('click',function() {
    let mark = $('#addFriendInfo').val();
    let friendId=$("#addUserId").attr("data-uid");
    if(mark.length>100){
        layer.msg("输入内容不能超过100字！");
        return;
    }
    let patrn=/[\"\'<>``!@#$%^&*+-\/\/\/\\//?,.]/;
    if (patrn.exec(mark)){
        layer.msg("不允许输入英文状态下的特殊字符！");
        return;
    }
    $.post('/friendApplyController/sentAddFriendInfo', {"userId" : userId,	"friendId" : friendId,	"remark" : mark	}, function(result) {
        console.log(result)
        if(result>0){
            $("#addSomeone_modal").modal('hide');
            layer.msg("发送成功！");
            return;
        }else if(result==-1){
            layer.msg("你们已经是好友了，请勿重复添加！");
            return;
        }else if(result==-2){
            layer.msg("不能添加自己为好友！");
            return;
        }else if(result==-3){
            layer.msg("您已经发送过好友请求，请等待对方回应！");
            return;
        }else{
            $("#addSomeone_modal").modal('hide');
            layer.msg("发送失败！");
        }
    })
    $('#addFriendInfo').html();
})