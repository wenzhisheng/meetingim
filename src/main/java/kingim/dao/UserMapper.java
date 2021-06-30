package kingim.dao;

import kingim.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    int updateEnable(@Param("vo") User user);

    int updateIsEnable(@Param("vo") User user);

    int updateEncrypted(@Param("vo") User user);

    List<User> listUser(@Param("vo") User user);

    int updateUser(@Param("vo") User user);

    int getUserByUserName(@Param("vo") User user);

    int updateByPassword(@Param("vo") User user);

    User getEncrypted(@Param("vo") User user);

}