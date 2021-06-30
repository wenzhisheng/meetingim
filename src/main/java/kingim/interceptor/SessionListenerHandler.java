package kingim.interceptor;

import kingim.utils.CommonUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 登录处理
 */
@Component
public class SessionListenerHandler implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId=se.getSession().getId();
        //当前session销毁时删除当前session绑定的用户信息
        //同时删除当前session绑定用户的HttpSession
        CommonUtil.USER_SESSION.remove(CommonUtil.SESSIONID_USER.remove(sessionId));
    }
}