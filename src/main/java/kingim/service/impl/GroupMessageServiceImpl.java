package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.GroupMessageMapper;
import kingim.model.GroupMessage;
import kingim.model.PageVO;
import kingim.model.User;
import kingim.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class GroupMessageServiceImpl extends BaseServiceImpl<GroupMessage> implements GroupMessageService {

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Override
    public Mapper<GroupMessage> getMapper() {
        return groupMessageMapper;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int scheduledGroupMessage() {
        return groupMessageMapper.scheduledGroupMessage();
    }

    @Override
    public PageInfo<GroupMessage> pageGroupMessage(PageVO pageVO, GroupMessage groupMessage) {
        PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
        return new PageInfo<GroupMessage>(groupMessageMapper.listGroupMessage(groupMessage));
    }

    @Override
    public int updateByCid(GroupMessage msg) {
        return groupMessageMapper.updateByCid(msg.getCid());
    }

    @Override
    public PageInfo<GroupMessage> searchHistoryMessage(String searchStr, Integer groupId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GroupMessage> list = groupMessageMapper.searchHistoryMessage(searchStr, groupId);
        PageInfo<GroupMessage> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public PageInfo<GroupMessage> getHistoryMessage(Integer groupId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GroupMessage> list = groupMessageMapper.getHistoryMessage(groupId);
        PageInfo<GroupMessage> page = new PageInfo<>(list);
        return page;
    }


}
