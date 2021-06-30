package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.FriendApply;

import java.util.List;

public interface FriendApplyService extends BaseService<FriendApply> {

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
    PageInfo<FriendApply> getByFromUserId(int fromUserId, int status, int pageNum, int pageSize);

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
    PageInfo<FriendApply> getByToUserId(int toUserId, int status, int pageNum, int pageSize);

    /**
     * 处理好友申请
     * @param id
     *            好友申请id
     * @param status
     *            0：待处理，1：同意，2：拒绝
     * @param fromTypeId  申请人分组id
     * @param toTypeId    受理人分组id
     * @return 受影响行数
     */
    int updateStatus(int id, int status,int fromTypeId,int toTypeId);

    /**
     * 查询我发给对方的好友请求
     * @param userId
     *            申请者id
     * @param toId
     *            被申请人id
     * @param status
     *            -1:所有，0：待处理，1：同意，2：拒绝
     */
    List<FriendApply> getByUserIdAndToId(int userId, int toId, int status);

}
