package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.FriendMessage;
import kingim.model.PageVO;

public interface FriendMessageService extends BaseService<FriendMessage> {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 12:58
     * @description: 定时清空聊天记录
     * @param: []
     * @return: int
     */
    int scheduledFriendMessage();

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 12:58
     * @description: 分页查询条件查询聊天记录
     * @param: [pageVO, friendMessage]
     * @return: com.github.pagehelper.PageInfo<kingim.model.FriendMessage>
     */
    PageInfo<FriendMessage> pageFriendMessage(PageVO pageVO, FriendMessage friendMessage);

    // 清空历史记录
    int clearHistoryMessage(FriendMessage msg);

    // 更新撤回
    int updateByCid(FriendMessage msg);

    // 搜索聊天记录
    PageInfo<FriendMessage> searchHistoryMessage(String searchStr, Integer fromUserId, Integer toUserId, Integer pageNum, Integer pageSize);

    //获取用户消息记录
    PageInfo<FriendMessage> getHistoryMessage(Integer fromUserId, Integer toUserId, Integer pageNum, Integer pageSize);

}