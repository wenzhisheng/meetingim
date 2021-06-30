package kingim.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kingim.exception.DescribeException;
import kingim.model.AdminVO;
import kingim.model.LoginLogVO;
import kingim.service.AdminService;
import kingim.service.LoginLogService;
import kingim.utils.CommonUtil;
import kingim.utils.IpAccessCityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private LoginLogService loginLogService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
    public Object login(HttpServletRequest request, @RequestBody AdminVO adminVO){
        AdminVO login = adminService.login(adminVO);
        JSONObject jsonObject = new JSONObject();
        if (login == null){
            jsonObject.put("status", "error");
            jsonObject.put("type", "account");
            jsonObject.put("currentAuthority", "guest");
            return jsonObject;
        }
        login.setType("account");
        login.setCurrentAuthority("admin");
        login.setStatus("success");
        // 保存登录日志
        insertLoginLog(request, login);
        return JSON.toJSONString(login);
    }

    private void insertLoginLog(HttpServletRequest request, AdminVO login) {
        LoginLogVO loginLogVO = new LoginLogVO();
        loginLogVO.setAccount(login.getAccount());
        loginLogVO.setLoginTime(new Date());
        loginLogVO.setLoginIp(CommonUtil.getIpAddr(request));
        //ip转换物理地址
        IpAccessCityUtil ipAccessCity = new IpAccessCityUtil();
        try {
            loginLogVO.setRealAddress(ipAccessCity.getAddresses("ip="+CommonUtil.getIpAddr(request), "utf-8"));
        } catch (Exception e) {
            throw new DescribeException("获取IP物理地址失败", -1);
        }
        loginLogService.save(loginLogVO);
    }

}
