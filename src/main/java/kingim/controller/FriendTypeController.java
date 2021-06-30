package kingim.controller;

import com.alibaba.fastjson.JSON;
import kingim.model.FriendType;
import kingim.model.User;
import kingim.service.FriendTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("friendTypeController")
public class FriendTypeController {

    @Autowired
    private FriendTypeService friendTypeService;

    // 获取好友分组列表
    @RequestMapping(value = "getFriendType", produces = "text/plain; charset=utf-8")
    public @ResponseBody String getFriendType(Integer userId) {
        List<FriendType> list = friendTypeService.getFriendTypeByUserId(userId);
        return JSON.toJSONString(list);
    }

    // 获取分组好友
    @ResponseBody
    @RequestMapping(value = "/getFriendsByTypeId", produces = "text/plain; charset=utf-8")
    public String getFriendsByTypeId(int typeId) {
        List<User> list = friendTypeService.getFriendsByTypeId(typeId);
        return JSON.toJSONString(list);
    }

    // 新增好友分组
    @RequestMapping(value = "add", produces = "text/plain; charset=utf-8")
    public @ResponseBody String add(String typeName, int userId) {
        int f = 0;
        try {
            FriendType t = new FriendType();
            t.setTypeName(typeName);
            t.setUserId(userId);
            t.setIsDefault(0);
            f = friendTypeService.save(t);
        } catch (Exception e) {
            f = 0;
            e.printStackTrace();
        }
        return f + "";
    }

    // 将用户移动至另一个分组 ,成功返回1，失败返回0.
    @RequestMapping(value = "updateToOtherType", produces = "text/plain; charset=utf-8")
    public @ResponseBody String updateBatchToOtherType(int userId, int friendId, int toTypeId) {
        int f = 0;
        try {
            f = friendTypeService.updateToOtherType(userId, friendId, toTypeId);
        } catch (Exception e) {
            f = 0;
            e.printStackTrace();
        }
        return f + "";
    }

    // 删除分组，好友移到默认"好友"分组
    @RequestMapping(value = "deleteById", produces = "text/plain; charset=utf-8")
    public @ResponseBody String deleteById(int id) {
        int f = 0;
        try {
            f = friendTypeService.delById(id);
        } catch (Exception e) {
            f = 0;
            e.printStackTrace();
        }
        return f + "";
    }

    // 更改分组名称
    @RequestMapping(value = "updateById", produces = "text/plain; charset=utf-8")
    public @ResponseBody String updateById(int id, String newName) {
        int f = 0;
        try {
            FriendType t = new FriendType();
            t.setId(id);
            t.setTypeName(newName);
            f = friendTypeService.update(t);
        } catch (Exception e) {
            f = 0;
            e.printStackTrace();
        }
        return f + "";
    }

}
