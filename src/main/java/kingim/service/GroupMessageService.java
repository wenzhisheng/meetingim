package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.GroupMessage;
import kingim.model.PageVO;

public interface GroupMessageService extends BaseService<GroupMessage> {

    int scheduledGroupMessage();

    PageInfo<GroupMessage> pageGroupMessage(PageVO pageVO, GroupMessage groupMessage);

    // 更新撤回
    int updateByCid(GroupMessage msg);

    //获取群消息记录
    PageInfo<GroupMessage> searchHistoryMessage(String searchStr, Integer groupId, Integer pageNum, Integer pageSize);

    // 获取群消息记录
    PageInfo<GroupMessage> getHistoryMessage(Integer groupId, Integer pageNum, Integer pageSize);
}