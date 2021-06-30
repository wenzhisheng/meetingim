package kingim.controller;

import com.github.pagehelper.PageInfo;
import kingim.exception.DescribeException;
import kingim.model.PageVO;
import kingim.model.WhiteIP;
import kingim.service.WhiteIPService;
import kingim.utils.CommonUtil;
import kingim.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/whiteIP")
public class WhiteIPController {

    @Autowired
    private WhiteIPService whiteIPService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:30
     * @description: 分页条件查询IP白名单
     * @param: [pageVO, loginLogVO]
     * @return: com.github.pagehelper.PageInfo<kingim.model.LoginLogVO>
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<WhiteIP> page(PageVO pageVO, WhiteIP whiteIP){
        return whiteIPService.page(pageVO, whiteIP);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:41
     * @description: 批量删除
     * @param: [whiteIP ]
     * @return: int
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public int deleteWhiteIP(WhiteIP whiteIP){
        return whiteIPService.deleteWhiteIP(whiteIP);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:44
     * @description: 更新IP白名单
     * @param: [whiteIP]
     * @return: int
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(@RequestBody WhiteIP whiteIP){
        CommonUtil.paramEmptyVerify(String.valueOf(whiteIP.getId()),"ID不能为空");
        String ip = whiteIP.getIp();
        if (ip != null){
            if (!CommonUtil.ipCheck(whiteIP.getIp())){
                throw new DescribeException("IP不合法", -1);
            }
        }
        whiteIP.setGmtModified(new Date());
        return whiteIPService.update(whiteIP);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:29
     * @description: 添加IP白名单
     * @param: [whiteIP]
     * @return: int
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(@RequestBody WhiteIP whiteIP){
        if (!CommonUtil.ipCheck(whiteIP.getIp())){
            throw new DescribeException("IP不合法", -1);
        }
        whiteIP.setGmtCreate(new Date());
        whiteIP.setGmtModified(new Date());
        return whiteIPService.save(whiteIP);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:29
     * @description: IP过滤
     * @param: [request]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/illegal", method = RequestMethod.POST)
    public Object isWhiteIP(HttpServletRequest request){
        String remortIP = CommonUtil.getIpAddr(request);
        WhiteIP whiteIP = new WhiteIP();
        whiteIP.setIp(remortIP);
        whiteIP.setStatus(1);
        ResultData resultData = new ResultData();
        // 0表示不存在，1存在
        resultData.setStatus(1);
        if (!whiteIPService.isWhiteIP(whiteIP)){
            resultData.setStatus(0);
            resultData.setLocation("http://www.80049.vip/illegal.jsp");
        }
        return resultData;
    }

}
