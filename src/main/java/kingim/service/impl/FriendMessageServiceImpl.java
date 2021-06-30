package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.FriendMessageMapper;
import kingim.model.FriendMessage;
import kingim.model.PageVO;
import kingim.model.User;
import kingim.service.FriendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class FriendMessageServiceImpl extends BaseServiceImpl<FriendMessage> implements FriendMessageService {

    @Autowired
    private FriendMessageMapper friendMessageMapper;

    @Override
    public Mapper<FriendMessage> getMapper() {
        return friendMessageMapper;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 12:58
     * @description: 定时清空聊天记录
     * @param: []
     * @return: int
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int scheduledFriendMessage() {
        return friendMessageMapper.scheduledFriendMessage();
    }

    @Override
    public PageInfo<FriendMessage> pageFriendMessage(PageVO pageVO, FriendMessage friendMessage) {
        PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
        return new PageInfo<FriendMessage>(friendMessageMapper.listFriendMessage(friendMessage));
    }

    @Override
    public int clearHistoryMessage(FriendMessage msg) {
        friendMessageMapper.clearHistoryMessage(msg.getToUserId(), msg.getFromUserId());
        return friendMessageMapper.clearHistoryMessage(msg.getFromUserId(), msg.getToUserId());
    }

    @Override
    public int updateByCid(FriendMessage msg) {
        return friendMessageMapper.updateByCid(msg.getCid());
    }

    @Override
    public PageInfo<FriendMessage> searchHistoryMessage(String searchStr, Integer fromUserId, Integer toUserId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FriendMessage> friendMsgList = friendMessageMapper.searchHistoryMessage(searchStr, fromUserId, toUserId);
        PageInfo<FriendMessage> page = new PageInfo<>(friendMsgList);
        return page;
    }

    @Override
    public PageInfo<FriendMessage> getHistoryMessage(Integer fromUserId, Integer toUserId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FriendMessage> friendMsgList = friendMessageMapper.getHistoryMessage(fromUserId, toUserId);
        PageInfo<FriendMessage> page = new PageInfo<>(friendMsgList);
        return page;
    }

}
