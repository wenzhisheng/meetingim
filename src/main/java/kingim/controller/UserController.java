package kingim.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import kingim.model.*;
import kingim.service.FriendTypeService;
import kingim.service.GroupUserService;
import kingim.service.UserService;
import kingim.utils.CommonUtil;
import kingim.utils.RedisUtils;
import kingim.utils.SessionSave;
import kingim.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/index")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private FriendTypeService friendTypeService;
	@Autowired
	private GroupUserService groupUserService;
	
	@RequestMapping("/password")
	public String password() {
		return "password";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/main")
	public String main() {
		return "main";
	}

	@RequestMapping("/wap")
	public String wap() {
		return "wap";
	}

	@RequestMapping("/myInfo")
	public String myInfo() {
		return "myInfo";
	}

	@RequestMapping("/myFriends")
	public String myFriends() {
		return "myFriends";
	}

	@RequestMapping("/myGroups")
	public String myGroups() {
		return "myGroups";
	}

	@RequestMapping("/searchFriends")
	public String searchFriends() {
		return "searchFriends";
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-09 21:07
	 * @description: 批量禁用
	 * @param: [user]
	 * @return: int
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEnable", method = RequestMethod.GET)
	public int updateEnable(User user){
		return userService.updateEnable(user);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-09 21:07
	 * @description: 批量启用
	 * @param: [user]
	 * @return: int
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsEnable", method = RequestMethod.GET)
	public int updateIsEnable(User user){
		return userService.updateIsEnable(user);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-09 20:58
	 * @description: 更新状态
	 * @param: [user]
	 * @return: int
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public int updateUser(@RequestBody User user){
		return userService.updateUser(user);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-08 21:01
	 * @description: 修改密保
	 * @param: [session]
	 * @return: java.lang.Object
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEncrypted")
	public int updateEncrypted(HttpSession session, User user) {
		int result = 0;
		User user1 = (User) session.getAttribute("user");
		User user2 = userService.getById(user1.getId());
		if (!user2.getPassword().equals(user.getPassword())){
			return result;
		}
		user.setId(user1.getId());
		return userService.updateEncrypted(user);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-08 17:57
	 * @description: 获取个人信息
	 * @param: [session]
	 * @return: java.lang.Object
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", produces = "text/plain; charset=utf-8")
	public Object getUserInfo(HttpSession session) {
		User user1 = (User) session.getAttribute("user");
		return JSON.toJSONString(userService.getById(user1.getId()));
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-06 21:41
	 * @description: 分页查询
	 * @param: [pageVO, user]
	 * @return: com.github.pagehelper.PageInfo<kingim.model.User>
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public PageInfo<User> pageUser(PageVO pageVO, User user){
		return userService.pageUser(pageVO, user);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-06 21:42
	 * @description: 注册账号
	 * @param: [file, request, user]
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@RequestParam(value = "file", required = false) MultipartFile file,
						 HttpServletRequest request, Model model, User user){
		if (insertParamVerify(model, user)) {
			return "/register";
		}
		// 账号不能重复
		if (!CommonUtil.userNameVertify(user.getUserName())){
			model.addAttribute("msg", "账号只能字母与数字组合，且5-16位");
			return "/register";
		}
		int byUserName = userService.getUserByUserName(user);
		if (byUserName > 0){
			model.addAttribute("msg", "账号已被使用，请修改");
			return "/register";
		}
		//user.setAvatar(CommonUtil.uploadFile(file, request));
		user.setIsEnable(0);
		user.setGmtCreate(new Date());
		user.setGmtLogin(new Date());
		user.setAvatar(user.getAvatar().replace("\\","/"));
		this.userService.save(user);
		// 初始化好友分组
		initFriendType(user);
		return "/login";
	}

	private boolean insertParamVerify(Model model, User user) {
		if(StringUtils.isEmpty(user.getNickName())) {
			model.addAttribute("msg", "昵称不能为空");
			return true;
		}
		if(StringUtils.isEmpty(user.getUserName())) {
			model.addAttribute("msg", "账号不能为空");
			return true;
		}
		if(StringUtils.isEmpty(user.getPassword())) {
			model.addAttribute("msg", "密码不能为空");
			return true;
		}
		if(StringUtils.isEmpty(user.getConfirmPassword())) {
			model.addAttribute("msg", "确认密码不能为空");
			return true;
		}
		if (!user.getPassword().equals(user.getConfirmPassword())){
			model.addAttribute("msg", "两次密码不一致");
			return true;
		}
		return false;
	}

	private void initFriendType(User user) {
		FriendType friendType = new FriendType();
		friendType.setTypeName("我的好友");
		friendType.setUserId(user.getId());
		friendType.setIsDefault(1);
		friendTypeService.save(friendType);
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-02 20:56
	 * @description: 修改密码
	 * @param: [user]
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/updateByPassword", method = RequestMethod.POST)
	public String updateByPassword(User user){
		userService.updateByPassword(user);
		return "/login";
	}

	@ResponseBody
	@RequestMapping("/updateUserInfo")
	public Map<String, Object> updateUserInfo(HttpSession session, User user) {
		User user1 = (User) session.getAttribute("user");
		String code = "0";
		User u = new User();
		u.setId(user1.getId());
		if(user.getSign()!=null){
			u.setSign(user.getSign());
		}
		if(user.getNickName()!=null){
			u.setNickName(user.getNickName());
		}
		if(user.getEmail()!=null){
			u.setEmail(user.getEmail());
		}
		if(user.getGender()!=null){
			u.setGender(user.getGender());
		}
		if(user.getPhone()!=null){
			u.setPhone(user.getPhone());
		}
		Map<String, Object> map = new HashMap<>();
		try {
			code = userService.update(u)+"";
		} catch (Exception e) {
			logger.error("更新用户信息失败：{}",e);
		}
		map.put("code", code);
		return map;
	}
	
	/**
	 * 用户登录
	 * @param model
	 * @param userName   用户名
	 * @param password   密码
	 */
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(HttpSession session,HttpServletRequest request,Model model, String userName, String password) {
		// 是否存在用户
		User user = userService.matchUser(userName,password);
		if (user != null) {
			// 同一用户登录过滤
			String sessionID = request.getRequestedSessionId();
			if (!SessionSave.getSessionIdSave().containsKey(userName)) {
				SessionSave.getSessionIdSave().put(userName, sessionID);
			}else if(SessionSave.getSessionIdSave().containsKey(userName) &&
					!sessionID.equals(SessionSave.getSessionIdSave().get(userName))){
				SessionSave.getSessionIdSave().remove(userName);
				SessionSave.getSessionIdSave().put(userName, sessionID);
			}
			session.setAttribute("user",user);
			boolean isMobile = isMobile(request);
			user.setGmtLogin(new Date());
			userService.update(user);
			logger.info("用户："+user.getNickName()+" : "+user.getUserName()+"  登录成功!" +" isMobile="+isMobile);
			if(isMobile){
				return "wap";
			}
			return "searchFriends";
		}
		model.addAttribute("msg", "用户名或密码错误，请重新输入!");
		logger.info("userName:"+userName+" password:"+password+"  登录失败!");
		return "login";
	}
	
	/**
	 * 退出登录
	 * @author 1434262447@qq.com
	 */
	@RequestMapping("/logout")
	public String logout(SessionStatus sessionStatus, HttpSession session, HttpServletRequest request) {
		sessionStatus.setComplete();
		session.invalidate();
		String basePath = request.getHeader("Referer");
		return "redirect:"+basePath.substring(0,basePath.indexOf("/index"));
	}

	/**
	 * 给layim提供初始化数据服务，包括个人信息、好友列表信息、群组列表信息
	 * @param userId
	 * @author 1434262447@qq.com
	 */
	@ResponseBody
	@RequestMapping(value = "/getInitList", produces = "text/plain; charset=utf-8")
	public String getInitList(int userId) {
		User u=userService.getUserById(userId);
		SNSUser mine = new SNSUser();
		mine.setId(u.getId());
		if(u.getAvatar()==null || u.getAvatar().equals("")){
			mine.setAvatar("images/avatar/default.png");
		}else{
			mine.setAvatar(u.getAvatar());
		}
		mine.setSign(u.getSign());
		mine.setUsername(u.getNickName());
		mine.setDifftime(CommonUtil.difftime(new Date(), u.getGmtLogin()));
		//获取redis中的用户在线状态
		String redisKey=userId+"_status";
		RedisUtils.set(redisKey, "online");
		mine.setStatus("online");
		SNSInit snsinit =new SNSInit();
		SNSdata data=new SNSdata();
		data.setMine(mine);
		snsinit.setData(data);
		List<SNSFriend> snsFriendList = new ArrayList<>();
		List<FriendType> list = friendTypeService.getFriendTypeByUserId(userId);
		if(list==null){
			int id=friendTypeService.getByUserId(userId);  //如果没有默认分组，则初始化分组
			SNSFriend snsFriend = new SNSFriend();
			snsFriend.setGroupname("我的好友");
			snsFriend.setOnline(0);
			snsFriend.setId(id);
			snsFriendList.add(snsFriend);
		}else{
			try{
				for(int i=0;i<list.size();i++){
					SNSFriend snsFriend = new SNSFriend();
					snsFriend.setGroupname(list.get(i).getTypeName());
					snsFriend.setId(list.get(i).getId());
					List<Friend> friendList=list.get(i).getFriends();
					List<SNSUser> snsUserList = new ArrayList<>();
					int onlineNum=0;
					for(int j=0;j<friendList.size();j++){
						SNSUser snsUser = new SNSUser();
						snsUser.setAvatar(friendList.get(j).getFriendInfo().getAvatar());
						snsUser.setSign(friendList.get(j).getFriendInfo().getSign());
						snsUser.setUsername(friendList.get(j).getFriendInfo().getNickName());
						snsUser.setId(friendList.get(j).getFriendId());
						snsUser.setDifftime(CommonUtil.difftime(new Date(), friendList.get(j).getFriendInfo().getGmtLogin()));
						onlineNum++;
						//获取redis中的用户在线状态
						redisKey=friendList.get(j).getFriendId()+"_status";
						if(RedisUtils.exists(redisKey)){
							snsUser.setStatus(RedisUtils.get(redisKey).toString());
						}else{
							snsUser.setStatus("offline");
						}
						snsUserList.add(snsUser);
					}
					snsFriend.setOnline(onlineNum);
					snsFriend.setList(snsUserList);
					snsFriendList.add(snsFriend);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		data.setFriend(snsFriendList);
		//获取我加入的群的列表
		List<Group> groupList = groupUserService.getByUserId(userId);
		List<SNSGroup> glist = new ArrayList<>();
		if(groupList!=null){
			for(int k=0;k<groupList.size();k++){
				SNSGroup sgroup = new SNSGroup();
				sgroup.setGroupname(groupList.get(k).getGroupName());
				sgroup.setId(groupList.get(k).getId());
				sgroup.setAvatar(groupList.get(k).getAvatar());
				glist.add(sgroup);
			}
			data.setGroup(glist);
		}
		snsinit.setData(data);

		return JSON.toJSONString(snsinit);
	}

	//从redis中获取离线消息
	@ResponseBody
	@RequestMapping(value = "/getOfflineMsgFromRedis")
	public JSONArray getOfflineMsgFromRedis(int userId) {
		JSONArray jsonArray = new JSONArray();
		if (RedisUtils.exists(userId + "_msg"))
		{
			Long len = RedisUtils.llen(userId + "_msg");
			while (len > 0)
			{
				jsonArray.add(RedisUtils.rpop(userId + "_msg"));
				len--;
			}
		}
		return jsonArray;
	}

	public static boolean isMobile(HttpServletRequest request) {
		List<String> mobileAgents = Arrays.asList("ipad", "iphone os", "rv:1.2.3.4", "ucweb", "android", "windows ce", "windows mobile","micromessenger");
		String ua = request.getHeader("User-Agent").toLowerCase();
		for (String sua : mobileAgents) {
			if (ua.indexOf(sua) > -1) {
				return true;//手机端
			}
		}
		return false;//PC端
	}

}
