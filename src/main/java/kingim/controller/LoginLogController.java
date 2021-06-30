package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.model.LoginLogVO;
import kingim.model.PageVO;
import kingim.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:29
     * @description: 分页条件查询登录日志
     * @param: [pageVO, loginLogVO]
     * @return: com.github.pagehelper.PageInfo<kingim.model.LoginLogVO>
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<LoginLogVO> page(PageVO pageVO, LoginLogVO loginLogVO){
        return loginLogService.page(pageVO, loginLogVO);
    }

}
