package kingim.dao;

import kingim.model.Group;
import kingim.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GroupMapper extends Mapper<Group> {

    int updateGroup(@Param("vo") Group group);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 15:03
     * @description: 列表获取群组
     * @param: [groupId]
     * @return: kingim.model.User
     */
    List<Group> listGroup(@Param("vo") Group group);

    User getGroupMaster(int groupId);

}