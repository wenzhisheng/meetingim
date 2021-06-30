package kingim.service.impl;

import java.util.List;

import kingim.dao.FriendMapper;
import kingim.dao.FriendTypeMapper;
import kingim.model.FriendType;
import kingim.model.User;
import kingim.service.FriendTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友分组
 */
@Service
public class FriendTypeServiceImpl extends BaseServiceImpl<FriendType> implements FriendTypeService {

	@Autowired
	private FriendTypeMapper friendTypeMapper;
	@Autowired
	private FriendMapper friendMapper;

	@Override
	public Mapper<FriendType> getMapper() {
		return friendTypeMapper;
	}

	@Override
	public List<FriendType> getFriendTypeByUserId(int userId) {
		return friendTypeMapper.getFriendTypeByUserId(userId);
	}

	@Override
	public int getByUserId(Integer userId) {
		FriendType friendType = new FriendType();
		friendType.setUserId(userId);
		friendType.setIsDefault(1);
		List<FriendType> friendTypeList = friendTypeMapper.select(friendType);
		if (friendTypeList.size() == 0) {
			friendType.setTypeName("我的好友");
			friendType.setIsDefault(1);
			friendTypeMapper.insert(friendType);
			friendTypeList.add(friendType);
			return friendType.getId();
		}
		return friendTypeList.get(0).getId();
	}

	@Override
	public int updateToOtherType(int userId, int friendId, int toTypeId) {
		return friendMapper.updateOneToOtherType(userId, friendId, toTypeId);
	}

	// 根据分组id查询该分组下好友详情列表
	@Override
	public List<User> getFriendsByTypeId(int typeId) {
		return friendTypeMapper.getFriendsByTypeId(typeId);
	}

	/**
	 * 删除好友分组<br>
	 * 该组下的好友移动到"好友"这个分组中
	 * @param id 分组id
	 * @return 受影响行数
	 */
	@Override
	public int delById(Object id) {
		int successRow = 0;
		// 获取当前分组
		FriendType delType = this.getById(id);
		// 获取"好友"分组
		FriendType goodFriendType = getByUserIdTypeName(delType.getUserId(),"好友");
		// 将删除的分组下的好友移动至"好友"分组
		successRow += friendMapper.updateAllToOtherType(delType.getUserId(), delType.getId(), goodFriendType.getId());
		// 删除分组
		successRow += super.deleteById(id);
		return successRow;
	}


	private FriendType getByUserIdTypeName(int userId, String typeName) {
		return friendTypeMapper.selectOne(new FriendType(userId, typeName));
	}

}
