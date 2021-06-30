package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.exception.DescribeException;
import kingim.model.FriendApply;
import kingim.model.PageVO;
import kingim.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kingim.dao.UserMapper;
import kingim.model.User;
import kingim.service.UserService;
import sun.nio.cs.US_ASCII;
import tk.mybatis.mapper.common.Mapper;

@Service
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService{

	@Autowired
    private UserMapper userMapper;

    @Override
    public Mapper<User> getMapper() {
        return userMapper;
     }

     /**
      * @author: dameizi
      * @dateTime: 2019-05-09 21:08
      * @description: 批量禁用
      * @param: [user]
      * @return: int
      */
    @Override
    public int updateEnable(User user) {
        CommonUtil.paramEmptyVerify(String.valueOf(user.getId()),"ID不能为空");
        return userMapper.updateEnable(user);
    }

    @Override
    public int updateIsEnable(User user) {
        CommonUtil.paramEmptyVerify(String.valueOf(user.getId()),"ID不能为空");
        return userMapper.updateIsEnable(user);
    }

    @Override
    public int updateEncrypted(User user) {
        return userMapper.updateEncrypted(user);
    }

    @Override
    public PageInfo<User> pageUser(PageVO pageVO, User user) {
        PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
        return new PageInfo<User>(userMapper.listUser(user));
    }

    @Override
    public int updateUser(User user) {
        CommonUtil.paramEmptyVerify(String.valueOf(user.getId()),"ID不能为空");
        CommonUtil.paramEmptyVerify(user.getNickName(), "昵称不能为空");
        CommonUtil.paramEmptyVerify(user.getPassword(), "密码不能为空");
        CommonUtil.paramEmptyVerify(String.valueOf(user.getRoleCode()), "角色不能为空");
        CommonUtil.paramEmptyVerify(String.valueOf(user.getGender()), "性别不能为空");
        return userMapper.updateUser(user);
    }

    @Override
    public int updateByPassword(User user) {
        CommonUtil.paramEmptyVerify(user.getUserName(), "账号不能为空");
        CommonUtil.paramEmptyVerify(user.getPassword(), "密码不能为空");
        CommonUtil.paramEmptyVerify(user.getConfirmPassword(), "确认密码不能为空");
        CommonUtil.paramEmptyVerify(user.getEncryptedQuestion(), "密保问题不能为空");
        CommonUtil.paramEmptyVerify(user.getEncryptedAnswer(), "密保答案不能为空");
        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new DescribeException("两次密码不一致", -1);
        }
        User user1 = userMapper.getEncrypted(user);
        if (!user.getEncryptedQuestion().equals(user1.getEncryptedQuestion())){
            throw new DescribeException("密保问题不正确", -1);
        }
        if (!user.getEncryptedAnswer().equals(user1.getEncryptedAnswer())){
            throw new DescribeException("密保答案不正确", -1);
        }
        return userMapper.updateByPassword(user);
    }

    @Override
     public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);  
     }

     /**
      * @author: dameizi
      * @dateTime: 2019-05-11 14:09
      * @description: 根据账号查询
      * @param: [user]
      * @return: kingim.model.User
      */
    @Override
    public int getUserByUserName(User user) {
        return userMapper.getUserByUserName(user);
    }

    @Override
     public User matchUser(String userName,String password) {
    	 User record = new User();
         record.setIsEnable(1);
    	 record.setUserName(userName);
    	 record.setPassword(password);
         return userMapper.selectOne(record);
     }

}
