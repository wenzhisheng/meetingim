package kingim.service;

import kingim.model.Friend;
import kingim.model.User;

import java.util.List;

public interface FriendService extends BaseService<Friend> {

    int delFriend(int userId, int friendId);

    List<User> searchFriends(String searchStr);

    boolean isFriend(int userId, int friendId);

}
