package kingim.service.impl;

import java.util.ArrayList;
import java.util.List;
import kingim.dao.GroupUserMapper;
import kingim.model.Group;
import kingim.model.GroupUser;
import kingim.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 群成员
 * @author 1434262447@qq.com
 */
@Service("groupUserService")
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUser> implements GroupUserService {

	@Autowired
	GroupUserMapper groupUserMapper;

	@Override
	public Mapper<GroupUser> getMapper() {
		return groupUserMapper;
	}

	@Override
	public List<String> getSimpleMemberByGroupId(int groupId) {
		return groupUserMapper.getSimpleMemberByGroupId(groupId);
	}

	@Override
	public List<Group> getByUserId(int userId) {
		return groupUserMapper.getByUserId(userId);
	}

	@Override
	public List<GroupUser> getByGroupId(Integer groupId) {
		List<GroupUser> list;
		if (groupId != null){
			list = groupUserMapper.getByGroupId(groupId);
		}else{
			list = new ArrayList<GroupUser>();
		}
		return list;
	}

	@Override
	public int deleteByGroupId(int groupId) {
		return groupUserMapper.deleteByGroupId(groupId);
	}

	@Override
	public List<GroupUser> getNewMemberByGroupId(int groupId, int userId) {
		return groupUserMapper.getNewMemberByGroupId(groupId, userId);
	}

	/**
	 * 批量添加群成员<br>
	 * 会忽略已是群组成员的用户
	 * @param guserList 	群成员list(必选值userId,groupId)
	 * @return 受影响行数
	 */
	@Override
	public int batchSave(List<GroupUser> guserList) {
		return groupUserMapper.batchSave(guserList);
	}

}
