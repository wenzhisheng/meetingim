package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.model.Group;
import kingim.model.GroupUser;
import kingim.model.PageVO;
import kingim.service.GroupService;
import kingim.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/groupController")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupUserService groupUserService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 15:40
     * @description: 更新群组
     * @param: [user]
     * @return: int
     */
    @ResponseBody
    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public int updateGroup(@RequestBody Group group){
        return groupService.updateGroup(group);
    }
    
    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 14:58
     * @description: 分页条件查询全部群组
     * @param: [pageVO, group]
     * @return: com.github.pagehelper.PageInfo<kingim.model.FriendMessage>
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<Group> pageGroup(PageVO pageVO, Group group){
        return groupService.pageGroup(pageVO, group);
    }

    /**
     * 创建群组
     * @param group 群组信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public Integer add(Group group) {
        // 创建群后，将创建者加为群成员
        UUID uuid = UUID.randomUUID();
        group.setAvatar("/images/animal-01.png");
        group.setGroupNum(uuid.toString());
        if (groupService.save(group)> 0) {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupId(group.getId());
            groupUser.setUserId(group.getUserId());
            groupUserService.save(groupUser);
            return group.getId();
        }
        return 0;
    }
    /**
     * 修改群组信息
     * @param group（id必须）
     * @return 成功执行的条数
     */
    @RequestMapping("/update")
    @ResponseBody
    public Integer update(Group group){
        group.setGmtModified(new Date());
        return groupService.update(group);
    }

    /**
     * 解散群组
     * @return 成功执行的条数
     */
    @RequestMapping("delete")
    @ResponseBody
    public Integer delete(int groupId){
        Group group=new Group();
        group.setId(groupId);
        Group g=groupService.selectOne(group);
        if(g!=null){
            groupUserService.deleteByGroupId(g.getId());
            return groupService.deleteById(g.getId());
        }
        return 0;
    }
    /**
     * 退出群组
     * @param （groupId、userId必须）
     * @return 成功执行的条数
     */
    @RequestMapping("quit")
    @ResponseBody
    public Integer quit(int groupId,int userId){
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        GroupUser gu=groupUserService.selectOne(groupUser);
        if(gu!=null){
            return groupUserService.deleteById(gu.getId());
        }
        return 0;
    }

}
