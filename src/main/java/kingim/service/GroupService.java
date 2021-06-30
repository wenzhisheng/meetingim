package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.Group;
import kingim.model.PageVO;
import kingim.model.User;

/**
 * 群组Service
 */
public interface GroupService extends BaseService<Group> {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-11 15:41
	 * @description: 更新
	 * @param: [group]
	 * @return: int
	 */
	int updateGroup(Group group);
	
	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-11 15:00
	 * @description: 分页条件查询全部群组
	 * @param: [pageVO, group]
	 * @return: com.github.pagehelper.PageInfo<kingim.model.Group>
	 */
	PageInfo<Group>  pageGroup(PageVO pageVO, Group group);

	User getGroupMaster(int groupId);
}
