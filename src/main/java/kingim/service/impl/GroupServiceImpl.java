package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.GroupMapper;
import kingim.model.Group;
import kingim.model.PageVO;
import kingim.model.User;
import kingim.service.GroupService;
import kingim.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 群组Service
 * @author 1434262447@qq.com
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService{

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Mapper<Group> getMapper() {
        return groupMapper;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 15:43
     * @description: 更新群组
     * @param: [group]
     * @return: int
     */
    @Override
    public int updateGroup(Group group) {
        CommonUtil.paramEmptyVerify(String.valueOf(group.getId()),"ID不能为空");
        return groupMapper.updateGroup(group);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 15:00
     * @description: 分页条件查询全部群组
     * @param: [pageVO, group]
     * @return: com.github.pagehelper.PageInfo<kingim.model.Group>
     */
    @Override
    public PageInfo<Group> pageGroup(PageVO pageVO, Group group) {
        PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
        return new PageInfo<Group>(groupMapper.listGroup(group));
    }

    @Override
    public User getGroupMaster(int groupId) {
        return groupMapper.getGroupMaster(groupId);
    }

}
