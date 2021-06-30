package kingim.service.impl;

import kingim.dao.FriendMapper;
import kingim.model.Friend;
import kingim.model.User;
import kingim.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 好友Service
 * @author jkxqj
 */
@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend> implements FriendService {

	@Autowired
	private FriendMapper friendMapper;

	@Override
	public Mapper<Friend> getMapper() {
		return friendMapper;
	}

	@Override
	public int delFriend(int userId, int friendId) {
		return friendMapper.delFriend(userId, friendId);
	}

	@Override
	public List<User> searchFriends(String searchStr) {
		return friendMapper.searchFriends(searchStr);
	}

	public boolean isFriend(int userId, int friendId) {
		return friendMapper.isFriend(userId, friendId);
	}

}
