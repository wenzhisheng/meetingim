package kingim.dao;

import java.util.List;
import kingim.model.FriendType;
import kingim.model.User;
import tk.mybatis.mapper.common.Mapper;

public interface FriendTypeMapper extends Mapper<FriendType> {
	// 获取好友分组列表
	List<FriendType> getFriendTypeByUserId(int userId);

	// 根据分组id查询该分组下好友详情
	List<User> getFriendsByTypeId(int typeId);
}