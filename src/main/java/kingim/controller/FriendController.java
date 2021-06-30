package kingim.controller;

import com.alibaba.fastjson.JSON;
import kingim.model.User;
import kingim.service.FriendService;
import kingim.service.FriendTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("friendController")
public class FriendController {

    @Autowired
    private FriendService friendService;

    //删除好友，成功返回1，失败返回0
    @ResponseBody
    @RequestMapping(value = "delFriend", produces = "text/plain; charset=utf-8")
    public String delFriend(int userId, int friendId) {
        int f=0;
        try {
            f=friendService.delFriend(userId, friendId);
        } catch (Exception e) {
            f=0;
            e.printStackTrace();
        }
        return f+"";
    }

    @ResponseBody
    @RequestMapping(value = "searchFriends", produces = "text/plain; charset=utf-8")
    public String searchFriend(String searchStr) {
        List<User> list = friendService.searchFriends(searchStr);
        return JSON.toJSONString(list);
    }

}
