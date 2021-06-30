package kingim.quartz;

import kingim.service.FriendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-09 22:04
 * @className kingim.quartz.FriendMessage
 */
@Component
public class FriendMessage {

    private static final Logger logger = LoggerFactory.getLogger(FriendMessage.class);

    @Autowired
    private FriendMessageService friendMessageService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-09 23:10
     * @description: 定时删除数据
     * @param: [] 每月二号凌晨删除聊天记录
     * @return: void
     */
    @Scheduled(cron = "0 0 0 2 * ?")
    public void testTask(){
        friendMessageService.scheduledFriendMessage();
        logger.info("执行定时删除单聊记录");
    }

}
