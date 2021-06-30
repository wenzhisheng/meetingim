package kingim.controller;

import com.alibaba.fastjson.JSON;
import kingim.model.GroupUser;
import kingim.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groupUserController")
public class GroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    //获取群组成员列表
    @ResponseBody
    @RequestMapping(value = "/getMemberByGroupId", produces = "text/plain; charset=utf-8")
    public String getMemberByGroupId(Integer groupId){
        List<GroupUser> list = groupUserService.getByGroupId(groupId);
        return JSON.toJSONString(list);
    }

    //获取不在某群组中的好友列表
    @ResponseBody
    @RequestMapping(value = "getNewMemberByGroupId", produces = "text/plain; charset=utf-8")
    public String getNewMemberByGroupId(int groupId,int userId){
        List<GroupUser> list = groupUserService.getNewMemberByGroupId(groupId, userId);
        return JSON.toJSONString(list);
    }

    /**
     * 批量加好友进讨论组,多个userId用逗号隔开
     * @param userIds
     * @return 成功执行的条数
     */
    @ResponseBody
    @RequestMapping("batchAdd")
    public Integer batchAdd(String userIds,int groupId) {
        String[] userIdArray = userIds.split(",");
        List<GroupUser> list = new ArrayList<>();
        for(int i=0;i<userIdArray.length;i++){
            GroupUser guser = new GroupUser();
            guser.setUserId(Integer.parseInt(userIdArray[i]));
            guser.setGroupId(groupId);
            list.add(guser);
        }
        return groupUserService.batchSave(list);
    }

}
