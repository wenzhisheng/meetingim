package kingim.dao;

import kingim.model.GroupMessage;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GroupMessageMapper extends Mapper<GroupMessage> {

    int scheduledGroupMessage();

    List<GroupMessage> listGroupMessage(@Param("vo") GroupMessage groupMessage);

    int updateByCid(@Param("cid") String cid);

    // 搜索群消息
    List<GroupMessage> searchHistoryMessage(@Param("searchStr")String searchStr, @Param("groupId")Integer groupId);

    List<GroupMessage> getHistoryMessage(@Param("groupId")Integer groupId);
}
