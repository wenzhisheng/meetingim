package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.model.FriendApply;
import kingim.service.FriendApplyService;
import kingim.service.FriendService;
import kingim.service.FriendTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("friendApplyController")
public class FriendApplyController {

    @Autowired
    private FriendTypeService friendTypeService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendApplyService friendApplyService;

    // 返回1代表发送成功！0代表失败，-1代表你们已经是好友，-2代表不能添加自己为好友,-3代表好友申请已经发给对方了，不要重复发送。
    @RequestMapping(value = "sentAddFriendInfo", produces = "text/plain; charset=utf-8")
    public @ResponseBody String sentAddFriendInfo(int userId, int friendId, String remark) {
        int f = 0;
        List<FriendApply> list = friendApplyService.getByUserIdAndToId(userId,friendId,0);
        if(list.size()>0){
            return "-3";
        }
        if(userId==friendId){
            return "-2";
        }
        if (friendService.isFriend(userId, friendId)){
            return "-1";
        }
        try {
            int friendTypeId = friendTypeService.getByUserId(userId);
            FriendApply friendApply = new FriendApply();
            friendApply.setFriendTypeId(friendTypeId);
            friendApply.setFromUserId(userId);
            friendApply.setToUserId(friendId);
            friendApply.setRemark(remark);
            friendApply.setStatus(0);
            f = friendApplyService.save(friendApply);
        } catch (Exception e) {
            e.printStackTrace();
            f = 0;
        }
        return f + "";
    }

    // 查询我发出的好友申请
    @ResponseBody
    @RequestMapping(value = "getByFromUserId")
    public PageInfo<FriendApply> getByFromUserId(int fromUserId, int status, int pageNum, int pageSize) {
        return friendApplyService.getByFromUserId(fromUserId, status, pageNum, pageSize);
    }

    // 查询我接收到的好友申请
    @CrossOrigin // 解决跨域
    @ResponseBody
    @RequestMapping(value = "getByToUserId")
    public PageInfo<FriendApply> getByToUserId(int toUserId, int status, int pageNum, int pageSize) {
        PageInfo<FriendApply> fa = friendApplyService.getByToUserId(toUserId, status, pageNum, pageSize);
        return fa;
    }

    // 查询 好友申请
    @RequestMapping(value = "getById")
    public @ResponseBody FriendApply getById(int id) {
        FriendApply fa = friendApplyService.getById(id);
        return fa;
    }

    // 处理好友申请 status 0：待处理，1：同意，2：拒绝
    // 返回1代表发送成功,0代表失败。
    @RequestMapping(value = "updateStatus", produces = "text/html;charset=utf-8")
    public @ResponseBody String updateStatus(int id, int status,int fromTypeId, int toTypeId) {
        int f = 0;
        try {
            f = friendApplyService.updateStatus(id,status,fromTypeId,toTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            f = 0;
        }
        return f + "";
    }

}
