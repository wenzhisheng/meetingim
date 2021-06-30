package kingim.dao;

import kingim.model.FriendApply;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FriendApplyMapper extends Mapper<FriendApply> {

    /**
     * 查询我发出的好友申请
     * @param fromUserId 申请者id
     * @param status   -1:所有，0：待处理，1：同意，2：拒绝
     * @return 好友申请List
     */
    List<FriendApply> getByFromUserId(@Param("fromUserId") int fromUserId,
                                      @Param("status") int status);

    /**
     * 查询我接收到的好友申请
     * @param toUserId
     *            申请者id
     * @param status
     *            -1:所有，0：待处理，1：同意，2：拒绝
     * @return 好友申请List
     */
    List<FriendApply> getByToUserId(@Param("toUserId") int toUserId,
                                    @Param("status") int status);

    /**
     * 更新申请状态
     * @param id
     *            好友申请id
     * @param status
     *            0：待处理，1：同意，2：拒绝
     * @return 受影响行数
     */
    int updateStatus(@Param("id") int id, @Param("status") int status);

}