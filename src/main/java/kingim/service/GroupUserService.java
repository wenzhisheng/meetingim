package kingim.service;

import java.util.List;
import kingim.model.Group;
import kingim.model.GroupUser;

public interface GroupUserService extends BaseService<GroupUser> {

	List<String> getSimpleMemberByGroupId(int groupId);

	// 根据用户id查询用户已加入的群组
	List<Group> getByUserId(int userId);

	/**
	 * 获取群成员列表<br>
	 * 群组信息、用户登录信息和用户基本信息
	 * @param groupId   群组id
	 * @return 群成员列表
	 */
	List<GroupUser> getByGroupId(Integer groupId);

	/**
	 * 清空群成员
	 * @param groupId  群组id
	 * @return 受影响行数
	 */
	int deleteByGroupId(int groupId);

	List<GroupUser> getNewMemberByGroupId(int groupId, int userId);

	int batchSave(List<GroupUser> guserList);

}
