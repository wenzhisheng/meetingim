package kingim.service;

import com.github.pagehelper.PageInfo;
import kingim.model.PageVO;
import kingim.model.User;

public interface UserService extends BaseService<User>{

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-09 21:08
	 * @description: 批量禁用
	 * @param: [user]
	 * @return: int
	 */
	int updateEnable(User user);

	int updateIsEnable(User user);

	int updateEncrypted(User user);

	PageInfo<User> pageUser(PageVO pageVO, User user);

	int updateUser(User user);

	int updateByPassword(User user);

	User getUserById(int userId);

	int getUserByUserName(User user);

	User matchUser(String userName,String password);

}
