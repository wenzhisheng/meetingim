package kingim.dao;

import kingim.model.FriendMessage;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FriendMessageMapper extends Mapper<FriendMessage> {

    int scheduledFriendMessage();

    List<FriendMessage> listFriendMessage(@Param("vo") FriendMessage friendMessage);

    int clearHistoryMessage(@Param("fromUserId")Integer fromUserId, @Param("toUserId")Integer toUserId);

    int updateByCid(@Param("cid") String cid);

    List<FriendMessage> searchHistoryMessage(@Param("searchStr")String searchStr, @Param("fromUserId")Integer fromUserId, @Param("toUserId")Integer toUserId);

    List<FriendMessage> getHistoryMessage(@Param("fromUserId")Integer fromUserId, @Param("toUserId")Integer toUserId);

    int updateAllToRead(@Param("fromUserId")Integer fromUserId,@Param("toUserId")Integer toUserId);

}
