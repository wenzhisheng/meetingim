var userId=$("#userId").val();

//初始页面。获取分组和全部好友信息
$.post("/index/getUserInfo",{"userId":userId},function (res) {
   // console.log(res);
    let data = eval("("+res+")");
  //  console.log(data);
    let myFriends = '';
    let typesData = new Map();
    let htm = '<a href="javascript:;" data-typeId="all" class="list-group-item FriendType active" style="text-align: center;background-color: white;color: black;">'
            + '个人信息</a>'
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
    myFriends += '<div class="right_info">'
        + '<div class="right_info_img"><img class="img-circle img-responsive pull-left" src="' + data.avatar + '"></div>'
        + '<div class="myFriends_block">用户名：'+ data.userName +'</div>'
        + '<div class="myFriends_block">密保问题：'+ data.encryptedQuestion +'</div>'
        + '<div class="myFriends_block">昵称：<input type="text" id="nickName" value="'+ data.nickName +'"></div>'
        + '<div class="myFriends_block">性别：<input type="text" id="gender" value="'+ data.gender +'"></div>'
        + '<div class="myFriends_block">手机：<input type="text" id="phone" value="'+ data.phone +'"></div>'
        + '<div class="myFriends_block">邮箱：<input type="text" id="email" value="'+ data.email +'"></div>'
        + '<div class="myFriends_block">个性签名：<input type="text" id="sign" value="'+data.sign+'"></div>'
        + '<div class="myFriends_block"><input type="submit" id="submitInfo" value="修改资料"></div>'
    $(".myFriends_right").html(myFriends);
});

//修改资料
$("body").on("click","#submitInfo",function(){
    let nickName =$("#nickName").val().trim();
    let gender =$("#gender").val().trim();
    let phone =$("#phone").val().trim();
    let email =$("#email").val().trim();
    let sign =$("#sign").val().trim();
    $.post('/index/updateUserInfo',{"nickName":nickName,"gender":gender,"phone":phone,
        "email":email,"sign":sign,"id":userId},function(result){
        if(parseInt(result.code) > 0){
            layer.msg("添加成功!");
            window.location.reload();
        }else{
            layer.msg('操作失败!');
        }
    });
});

//设置密保
$("body").on("click","#add-type-btn",function(){
    let encryptedQuestion =$("#encryptedQuestion").val().trim();
    let encryptedAnswer =$("#encryptedAnswer").val().trim();
    let password =$("#password").val().trim();
    $.post('/index/updateEncrypted',{"encryptedQuestion":encryptedQuestion,"encryptedAnswer":encryptedAnswer,"password":password,"id":userId},function(result){
        if(parseInt(result) > 0){
            layer.msg("添加成功!");
            $("#encryptedQuestion").val("");
            $("#encryptedAnswer").val("");
            $("#password").val("");
            window.location.reload();
        }else{
            layer.msg('操作失败!');
        }
    });
});

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