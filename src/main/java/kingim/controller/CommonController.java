package kingim.controller;

import kingim.utils.GoogleAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author dameizi
 * @description 公共接口
 * @dateTime 2019-05-10 22:26
 * @className kingim.controller.CommonController
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    //当测试authTest时候，把genSecretTest生成的secret值赋值给它
    private static final String secret = "ZPP4P3M5G3RKYQ4V";

//    public static void main(String[] args) {
//        String secret = GoogleAuthenticator.generateSecretKey();
         //把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
//        String qrcode = GoogleAuthenticator.getQRBarcode("meeting", secret);
//        String qrBarcodeURL = GoogleAuthenticator.getQRBarcodeURL("meeting", "www.80049.vip", secret);
//        System.out.println("二维码地址："+ qrBarcodeURL);
//        System.out.println("qrcode:" + qrcode + ",key:" + secret);
//        String key = "555502";
//        boolean authcode = GoogleAuthenticator.authcode(key, secret);//验证
//        System.out.println("检查code是否正确？" + authcode);
//    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-10 22:43
     * @description: 谷歌动态口令
     * @param: [securityCode]
     * @return: boolean
     */
    @ResponseBody
    @RequestMapping(value = "/authenticator", method = RequestMethod.GET)
    public boolean deleteUser(String securityCode){
        if (!securityCode.matches("[0-9]{6}")){
            return false;
        }
        return GoogleAuthenticator.authcode(securityCode, secret);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-15 12:49
     * @description: 删除上传
     * @param: []
     * @return: void
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUpload", method = RequestMethod.GET)
    public void deleteUpload(HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload"+File.separator+"sns");
        File file = new File(path);
        delete(file);
        logger.info("执行定时删除上传文件");
    }

    public static void delete(File file) {
        if(!file.exists()) {
            return;
        }
        if(file.isFile() || file.list()==null) {
            file.delete();
            System.out.println("删除了"+file.getName());
        }else {
            File[] files = file.listFiles();
            for(File a:files) {
                delete(a);
            }
            file.delete();
            System.out.println("删除了"+file.getName());
        }
    }

}
