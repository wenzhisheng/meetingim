package kingim.utils;

import kingim.exception.DescribeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-04-28 19:16
 * @className kingim.utils.CommonUtil
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    //用户和Session绑定关系
    public static final Map<String, HttpSession> USER_SESSION=new HashMap<String, HttpSession>();
    //seeionId和用户的绑定关系
    public static final Map<String, String> SESSIONID_USER=new HashMap<String, String>();

    /**
     * @author: dameizi
     * @dateTime: 2019-05-15 16:52
     * @description: 用户名校验
     * @param: [username]
     * @return: boolean
     */
    public static boolean userNameVertify(String username){
        if (username.matches("[0-9A-Za-z]{5,16}")){
            return true;
        }
        return false;
    }

    //用户登录处理
    public static void userLoginHandle(HttpServletRequest request){
        //当前登录的用户
        String userName = request.getParameter("userName");
        //当前sessionId
        String sessionId=request.getSession().getId();
        //删除当前sessionId绑定的用户，用户--HttpSession
        USER_SESSION.remove(SESSIONID_USER.remove(sessionId));
        //删除当前登录用户绑定的HttpSession
        HttpSession session=USER_SESSION.remove(userName);
        if(session!=null){
            SESSIONID_USER.remove(session.getId());
            session.removeAttribute("user");
            session.setAttribute("msg", "您的账号已经在另一处登录了,你被迫下线!");
        }
    }

    // base64转为文件
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b;
            b = decoder.decodeBuffer(baseStrs[1]);
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将文件转base64字符串
    public String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    // BASE64解码成File文件
    public static File base64ToFile(String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath="D:\\image";
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    // 时间差
    public static String difftime(Date nowTime, Date oldTime){
        long l = nowTime.getTime() - oldTime.getTime();
        //long day = l/(24*60*60*1000);
        //long hour = (l/(60*60*1000)-day*24);
        long hour = (l/(60*60*1000));
        long min = ((l/(60*1000))-hour*60);
        //long min = ((l/(60*1000))-day*24*60-hour*60);
        //long s = (l/1000-day*24*60*60-hour*60*60-min*60);
        return hour+"小时"+min+"分钟";
    }

    // 获取真实IP
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:04
     * @description: 参数非空校验
     * @param: [param, error]
     * @return: void
     */
    public static void paramEmptyVerify(String param, String error) {
        if(StringUtils.isEmpty(param)) {
            throw new DescribeException(error, 0);
        }
    }

    //上传文件
    public static String uploadFile(MultipartFile file, HttpServletRequest request ) {
        String path = request.getSession().getServletContext().getRealPath("upload/picture/");
        String fileName = StringFilter(file.getOriginalFilename());
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        if(file.getSize()>1024*1024*5){
            throw new DescribeException("单个文件/图片大小不能超过5M!", -1);
        }
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/sns/";
        String name = UUID.randomUUID().toString()+"."+ext;
        String downLoadPath = basePath+"/"+name;
        File targetFile = new File(path,name);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("上传文件失败，{}", e);
        }
        return downLoadPath;
    }

    // 过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符和空格
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
