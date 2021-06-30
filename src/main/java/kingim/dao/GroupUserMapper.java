package kingim.dao;

import kingim.model.Group;
import kingim.model.GroupUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface GroupUserMapper extends Mapper<GroupUser> {

    // 获取群组成员用户名列表
    List<String> getSimpleMemberByGroupId(int groupId);

    /**
     * 获取群成员列表<br>
     * 群组信息、用户登录信息和用户基本信息
     * @param groupId  群组id
     * @return 群成员列表
     */
    List<GroupUser> getByGroupId(@Param("groupId") int groupId);

    // 根据用户id查询已加入的群组
    List<Group> getByUserId(@Param("userId") int userId);

    /**
     * 清空群成员
     * @param groupId  群组id
     * @return 受影响行数
     */
    int deleteByGroupId(@Param("groupId") Integer groupId);

    /**
     * 获取可加入讨论组的好友列表<br>
     * @param groupId,userId 群组id,管理员id
     * @return 成员列表
     */
    List<GroupUser> getNewMemberByGroupId(@Param("groupId") int groupId,@Param("userId") int userId);

    /**
     * 批量添加群成员<br>
     * 会忽略已是群组成员的用户
     * @param guserList 群成员list(必选值userId,groupId)
     * @return 受影响行数
     */
    int batchSave(@Param("guserList") List<GroupUser> guserList);
}