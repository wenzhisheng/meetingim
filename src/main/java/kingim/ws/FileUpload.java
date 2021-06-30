package kingim.ws;

import com.alibaba.fastjson.JSON;
import kingim.utils.CommonUtil;
import kingim.vo.UFile;
import kingim.vo.UploadFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sns")
public class FileUpload {

	private static final String ADDRESS = "/home/share/meeting/";

	private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:41
	 * @description: 获取头像资源
	 * @param: [request]
	 * @return: java.util.List<java.lang.String>
	 */
	@ResponseBody
	@RequestMapping(value = "/getImage")
	public List<String> uploadPaste(HttpServletRequest request){
		List<String> list = new ArrayList<String>();
		String separator = File.separator;
		String realPath = request.getSession().getServletContext().getRealPath("images")+separator;
		File baseFile = new File(realPath);
		if (baseFile.isFile() || !baseFile.exists()) {
			return list;
		}
		File[] files = baseFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				break;
			} else {
				String absolutePath = file.getAbsolutePath();
				list.add(separator+absolutePath.substring(absolutePath.indexOf("images")));
			}
		}
		return list;
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:10
	 * @description: 上传截图
	 * @param: [request, userId, imageBase64Content]
	 * @return: java.lang.String
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadPaste", produces = "text/plain; charset=utf-8")
	public String uploadPaste(HttpServletRequest request, int userId, String imageBase64Content){
        MultipartFile file = CommonUtil.base64ToMultipart(imageBase64Content);
        UploadFile upFile = new UploadFile();
        String path = request.getSession().getServletContext().getRealPath("upload/sns/"+userId+"/");
        String fileName = CommonUtil.StringFilter(file.getOriginalFilename());
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		if (fileUploadSize(file, upFile)) {
			return JSON.toJSONString(upFile);
		}
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/sns/"+userId;
        String name=UUID.randomUUID().toString()+"."+ext;
        String downLoadPath=basePath+"/"+name;
        File pathFile = new File(path);
        if (!pathFile.exists()){
			pathFile.mkdirs();
		}
        File targetFile = new File(path,name);
        try {
            file.transferTo(targetFile);
            upFile.setCode(0);
            UFile uf=new UFile();
            uf.setName(fileName);
            uf.setSrc("img["+downLoadPath+"]");
            upFile.setData(uf);
			// 上传至共享目录
			//meetingshare(file, userId);
        } catch (Exception e) {
			upFile.setMsg("图片上传异常");
			logger.error("图片上传异常，{}", e);
        }
        return JSON.toJSONString(upFile);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:10
	 * @description: 上传图片文件
	 * @param: [file, userId, request]
	 * @return: java.lang.String
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8")
	public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,int userId,HttpServletRequest request) {
		UploadFile upFile = new UploadFile();
		String path = request.getSession().getServletContext().getRealPath("upload/sns/"+userId+"/");
		String fileName = CommonUtil.StringFilter(file.getOriginalFilename());
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		if (fileUploadSize(file, upFile)) return JSON.toJSONString(upFile);
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/sns/"+userId;
		String name=UUID.randomUUID().toString()+"."+ext;
		String downLoadPath = basePath+"/"+name;
		File targetFile = new File(path,name);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
			upFile.setCode(0);
			UFile uf=new UFile();
			uf.setName(fileName);
			//uf.setSrc(ROOT+"/sns/downLoadFile?downLoadPath="+downLoadPath+"&fileName="+fileName);
			uf.setSrc(downLoadPath);
			upFile.setData(uf);
			// 上传至共享目录
			// meetingshare(file, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(upFile);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:11
	 * @description: 下载图片文件
	 * @param: [downLoadPath, fileName, response]
	 * @return: void
	 */
	@RequestMapping("/downLoadFile")
	public void downLoadFile(String downLoadPath, String fileName,HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setCharacterEncoding("UTF-8");
			long fileLength = new File(downLoadPath).length();
			response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));
			response.setContentType("application/x-download;");
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
		} catch (Exception e) {
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 问价能上传大小限制
	private boolean fileUploadSize(@RequestParam(value = "file", required = false) MultipartFile file, UploadFile upFile) {
		if (file.getSize() > 1024 * 1024 * 5) {
			upFile.setCode(1);
			upFile.setMsg("单个文件/图片大小不能超过5M!");
			return true;
		}
		return false;
	}

	// 上传至共享目录
	public void meetingshare(MultipartFile file, int userId){
		String path = ADDRESS+userId;
		File pathFile = new File(path);
		try {
			Runtime.getRuntime().exec("chmod 777 "+ADDRESS.substring(0, ADDRESS.indexOf("/share")));
			Runtime.getRuntime().exec("chmod 777 "+ADDRESS.substring(0, ADDRESS.indexOf("/meeting")));
			Runtime.getRuntime().exec("chmod 777 "+ADDRESS.substring(0, ADDRESS.lastIndexOf("/")));
			if (!pathFile.exists()){
				Runtime.getRuntime().exec("chmod 777 "+path);
			}
			// 得到输入流
			InputStream inputStream = file.getInputStream();
			// 获取文件名
			String filename = CommonUtil.StringFilter(file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(inputStream, new File(path, filename));
			Runtime.getRuntime().exec("chmod 777 " + path +  "/"+ filename);
		} catch (Exception e) {
			logger.error("图片上传异常，{}", e);
		}
	}

}