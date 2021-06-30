package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.FriendApplyMapper;
import kingim.dao.FriendMapper;
import kingim.model.Friend;
import kingim.model.FriendApply;
import kingim.service.FriendApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Service
public class FriendApplyServiceImpl extends BaseServiceImpl<FriendApply>
        implements FriendApplyService {

    @Autowired
    private FriendApplyMapper friendApplyMapper;
    @Autowired
    private FriendMapper friendMapper;

    /**
     * 申请状态(好友申请、群组申请、活动申请)-所有
     */
    public static final int APPLY_ALL = -1;
    /**
     * 申请状态(好友申请、群组申请、活动申请)-待处理
     */
    public static final int APPLY_UNCHECK = 0;
    /**
     * 申请状态(好友申请、群组申请、活动申请)-通过
     */
    public static final int APPLY_PASS = 1;
    /**
     * 申请状态(好友申请、群组申请、活动申请)-拒绝
     */
    public static final int APPLY_REFUSE = 2;

    @Override
    public Mapper<FriendApply> getMapper() {
        return friendApplyMapper;
    }

    /**
     * 查询我发出的好友申请
     * @param fromUserId
     *            申请者id
     * @param status
     *            -1:所有，0：待处理，1：同意，2：拒绝
     * @param pageNum
     *            当前页
     * @param pageSize
     *            每页记录数
     * @return 好友申请PageInfo
     */
    @Override
    public PageInfo<FriendApply> getByFromUserId(int fromUserId, int status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<FriendApply>(friendApplyMapper.getByFromUserId(fromUserId, status));
    }

    /**
     * 查询我接收到的好友申请
     * @param toUserId
     *            申请者id
     * @param status
     *            -1:所有，0：待处理，1：同意，2：拒绝
     * @param pageNum
     *            当前页
     * @param pageSize
     *            每页记录数
     * @return 好友申请PageInfo
     */
    public PageInfo<FriendApply> getByToUserId(int toUserId, int status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<FriendApply>(friendApplyMapper.getByToUserId(toUserId, status));
    }

    /**
     * 处理好友申请<br>
     * 如果受理人同意，则建立好友关系
     * @param id          好友申请id
     * @param status      0：待处理，1：同意，2：拒绝
     * @param fromTypeId  申请人分组id
     * @param toTypeId    受理人分组id
     * @return 受影响行数
     */
    public int updateStatus(int id, int status, int fromTypeId,int toTypeId) {

        // 处理申请
        int successRow = friendApplyMapper.updateStatus(id, status);

        // 如果是同意，则处理好友关系
        if (status == APPLY_PASS) {

            FriendApply friendApply = friendApplyMapper.selectByPrimaryKey(id);

            // 验证是否好友
            boolean isFriend = friendMapper.isFriend(friendApply.getFromUserId(), friendApply.getToUserId());

            // 如果不是好友，建立好友关系
            if (!isFriend) {
                // 申请人-> 受理人
                successRow += friendMapper.insertSelective(new Friend(friendApply.getFromUserId(), friendApply.getToUserId(),toTypeId));
                // 受理人-->申请人
                successRow += friendMapper.insertSelective(new Friend(friendApply.getToUserId(), friendApply.getFromUserId(),fromTypeId));
            }
        }
        return successRow;
    }

    public List<FriendApply> getByUserIdAndToId(int userId, int toId, int status) {
        FriendApply record = new FriendApply();
        record.setFromUserId(userId);
        record.setToUserId(toId);
        record.setStatus(status);
        return friendApplyMapper.select(record);
    }
}
