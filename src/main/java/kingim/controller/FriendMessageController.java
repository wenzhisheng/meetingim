package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.model.FriendMessage;
import kingim.model.PageVO;
import kingim.service.FriendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/friendMessage")
public class FriendMessageController {

    @Autowired
    private FriendMessageService friendMessageService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-05 15:13
     * @description: 单聊信息
     * @param: [pageVO, friendMessage]
     * @return: com.github.pagehelper.PageInfo<kingim.model.FriendMessage>
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<FriendMessage> pageUser(PageVO pageVO, FriendMessage friendMessage){
        return friendMessageService.pageFriendMessage(pageVO, friendMessage);
    }

}
