package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.model.GroupMessage;
import kingim.model.PageVO;
import kingim.model.User;
import kingim.model.WhiteIP;
import kingim.service.GroupMessageService;
import kingim.service.WhiteIPService;
import kingim.utils.CommonUtil;
import kingim.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/groupMessage")
public class GroupMessageController {

    @Autowired
    private GroupMessageService groupMessageService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-10 19:50
     * @description: 分页条件查询群聊消息
     * @param: [pageVO, groupMessage]
     * @return: com.github.pagehelper.PageInfo<kingim.model.GroupMessage>
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<GroupMessage> pageUser(PageVO pageVO, GroupMessage groupMessage){
        return groupMessageService.pageGroupMessage(pageVO, groupMessage);
    }

}
