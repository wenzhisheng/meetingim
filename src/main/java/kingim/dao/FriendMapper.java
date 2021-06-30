package kingim.dao;

import kingim.model.Friend;
import kingim.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FriendMapper extends Mapper<Friend> {

    int updateOneToOtherType(@Param("userId") int userId,@Param("friendId") int friendId, @Param("toTypeId") int toTypeId);

    int delFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    int updateAllToOtherType(@Param("userId") int userId,@Param("fromTypeId") int fromTypeId, @Param("toTypeId") int toTypeId);

    List<User> searchFriends(String searchStr);

    /**
     * 是否已是好友关系
     * @param userId
     *            用户id
     * @param friendId
     *            好友id
     * @return true:已是好友 false不是好友
     */
    boolean isFriend(@Param("userId") int userId, @Param("friendId") int friendId);
}