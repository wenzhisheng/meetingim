package kingim.interceptor;

import kingim.model.User;
import kingim.utils.SessionSave;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dameizi
 * @description 登录拦截器
 * @dateTime 2019-05-16 19:27
 * @className kingim.interceptor.LoginFilter
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //获取session
        HttpSession session = servletRequest.getSession();
        //获得用户请求的URI
        String path = servletRequest.getRequestURI();
        //获取session的用户信息
        User user = (User) session.getAttribute("user");
        //如果是登录界面直接放行
        if (path.indexOf("/index/loginCheck") > -1 || path.indexOf("/index/updateEnable") > -1 ||
                path.indexOf("/index/updateIsEnable") > -1 || path.indexOf("/index/updateUser") > -1 ||
                path.indexOf("/index/page") > -1 || path.indexOf("/index/insert") > -1 ||
                path.indexOf("/index/password") > -1 || path.indexOf("/index/register") > -1 ||
                path.indexOf("/index/updateByPassword") > -1 || path.indexOf("/index/login") > -1 ||
                path.indexOf("/index/main") > -1 || path.indexOf("/index/wap") > -1) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        //如果用户信息为空，表明session已经过期或者已经被清空，则跳转到登陆页面
        if (user == null) {
            servletResponse.sendRedirect("http://www.80049.vip");
            //servletResponse.sendRedirect(servletRequest.getContextPath() + "/");
        } else {
            //获取全局类SessionSave保存账户的静态sessionId
            String sessionId = SessionSave.getSessionIdSave().get(user.getUserName());
            //获取当前的sessionId
            String currentSessionId = session.getId();
            //如果两个sessionId不等，则当前账户强制下线，需要重新登录
            if (!currentSessionId.equals(sessionId)) {
                servletResponse.sendRedirect("http://www.80049.vip");
                //servletResponse.sendRedirect(servletRequest.getContextPath() + "/");
            }else {
                // 如果是同一账户session则放行请求
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
