package kingim;

import kingim.utils.GoogleAuthenticator;
import org.junit.Test;

/**
 *
 *
 * 身份认证测试
 *
 * @author yangbo
 *
 * @version 创建时间：2017年8月14日 上午11:09:23
 *
 *
 */
public class AuthTest {

    //当测试authTest时候，把genSecretTest生成的secret值赋值给它
    private static String secret="ZPP4P3M5G3RKYQ4V";

//    @Test
//    public void genSecretTest() {// 生成密钥
//        String secret = GoogleAuthenticator.generateSecretKey();
//        // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
//        String qrcode = GoogleAuthenticator.getQRBarcode("meeting", secret);
//        String qrBarcodeURL = GoogleAuthenticator.getQRBarcodeURL("meeting", "www.80049.vip", secret);
//        System.out.println("二维码地址："+qrBarcodeURL);
//        System.out.println("qrcode:" + qrcode + ",key:" + secret);
//    }
    
    /**
     * 对app的随机生成的code,输入并验证
     */
//    @Test
//    public void verifyTest() {
//        long code = 775828;
//        long t = System.currentTimeMillis();
//        GoogleAuthenticator ga = new GoogleAuthenticator();
//        ga.setWindowSize(1);
//        boolean r = ga.check_code(secret, code, t);
//        System.out.println("检查code是否正确？" + r);
//        String key = "287671";
//        boolean authcode = GoogleAuthenticator.authcode(key, secret);//验证
//        System.out.println("检查code是否正确？" + authcode);
//    }

}
