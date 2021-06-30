package kingim.service;

import java.util.List;
import kingim.model.FriendType;
import kingim.model.User;

public interface FriendTypeService extends BaseService<FriendType> {

	// 获取好友分组列表
	List<FriendType> getFriendTypeByUserId(int userId);

	// 查询用户的默认好友分组id，如果不存在好友分组则创建默认分组"好友"并返回该id
	int getByUserId(Integer userId);

	//修改好友分组
	int updateToOtherType(int userId, int friendId, int toTypeId);

	//根据分组id获取好友信息
	List<User> getFriendsByTypeId(int typeId);

	/**
	 * 删除好友分组<br>
	 * 该组下的好友移动到"好友"这个分组中
	 *
	 * @param id
	 *            分组id
	 * @return 受影响行数
	 */
	int delById(Object id);

}
