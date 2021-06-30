package kingim.ws;

import com.github.pagehelper.PageInfo;
import kingim.model.FriendMessage;
import kingim.model.GroupMessage;
import kingim.model.User;
import kingim.service.FriendMessageService;
import kingim.service.GroupMessageService;
import kingim.utils.RedisUtils;
import kingim.vo.SearchMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/friend")
public class FriendWS {

	@Autowired
	private FriendMessageService friendMessageService;
	@Autowired
	private GroupMessageService groupMessageService;

	// 清空历史记录
	@ResponseBody
	@RequestMapping(value = "/clearMsgHistory",produces="text/html;charset=UTF-8")
	public String clearHistoryMessage(Integer userId, Integer toId, String type) {
		String result = "0";
		if("friend".equals(type)){
			FriendMessage msg = new FriendMessage();
			msg.setFromUserId(userId);
			msg.setToUserId(toId);
			result = friendMessageService.clearHistoryMessage(msg)+"";
		}else if("group".equals(type)){

		}
		return result;
	}

	// 消息撤回
	@ResponseBody
	@RequestMapping(value = "/delMsgFromMongoByCid")
	public int delMsgFromMongoByCid(String cid, String type){
		int result = 0;
		if("friend".equals(type)){
			FriendMessage msg = new FriendMessage();
			msg.setIsBack(1);
			msg.setCid(cid);
			result = friendMessageService.updateByCid(msg);
		}else if("group".equals(type)){
			GroupMessage msg = new GroupMessage();
			msg.setIsBack(1);
			msg.setCid(cid);
			result = groupMessageService.updateByCid(msg);
		}
		return result;
	}

	//记录发送的消息
	@ResponseBody
	@RequestMapping(value = "/saveMessage", produces = "text/plain; charset=utf-8")
	public String saveMessage(String message) {
		String result = "0";
		if (message != null){
			JSONObject jsonObject = JSONObject.fromObject(message);
			JSONObject mine = (JSONObject)jsonObject.get("mine");
			JSONObject to = (JSONObject)jsonObject.get("to");
			FriendMessage msg = new FriendMessage();
			msg.setContent((String)mine.get("content"));
			msg.setFromUserId((Integer) mine.get("id"));
			msg.setToUserId((Integer) to.get("id"));
			msg.setCid((String)mine.get("cid"));
			msg.setIsRead(0);
			msg.setIsDel(0);
			msg.setIsBack(0);
			result = friendMessageService.save(msg)+"";
		}
		return result;
	}

	//查询历史消息页面
	@RequestMapping(value = "getHistoryMessagePage")
	public String getHistoryMessagePage(HttpSession session,Integer id,String type,Integer pageNum,Integer pageSize, Model model) {
		User user = (User) session.getAttribute("user");
		if(pageNum==null){
			pageNum=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		int fromUserId = user.getId();
		String str = getHistoryMsg(type,id,fromUserId,pageNum,pageSize);
		model.addAttribute("jsonStr","["+str+"]");
		model.addAttribute("toId",id);
		model.addAttribute("type",type);
		return "chatLog";
	}

	//关键词查询聊天记录
	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/searchHistoryMessage",produces="text/html;charset=UTF-8")
	public String searchHistoryMessage(HttpSession session, SearchMessage searchMessage, Model model) {
		User user = (User) session.getAttribute("user");
		if(searchMessage.getPageNum()==null){
			searchMessage.setPageNum(1);
		}
		if(searchMessage.getPageSize()==null){
			searchMessage.setPageSize(10);
		}
		int fromUserId = user.getId();
		String str = searchHistoryMsg(searchMessage.getSearchStr() ,searchMessage.getType(),searchMessage.getId(),fromUserId,searchMessage.getPageNum(),searchMessage.getPageSize());
		return "["+str+"]";
	}

	private String searchHistoryMsg(String searchStr, String type, Integer id, Integer fromUserId, Integer pageNum, Integer pageSize){
		if(type.equals("friend")){
			PageInfo<FriendMessage> list = friendMessageService.searchHistoryMessage(searchStr, fromUserId,id,pageNum,pageSize);
			JSONArray jsonArray = JSONArray.fromObject(list);
			return jsonArray.get(0).toString();
		}else if(type.equals("group")){
			PageInfo<GroupMessage> list = groupMessageService.searchHistoryMessage(searchStr, id,pageNum,pageSize);
			JSONArray jsonArray= JSONArray.fromObject(list);
			return jsonArray.get(0).toString();
		}
		return "";
	}

	//查询历史消息接口
	@ResponseBody
	@RequestMapping(value = "getHistoryMessage",produces="text/html;charset=UTF-8")
	public String getHistoryMessage(HttpSession session,Integer id,String type,Integer pageNum,Integer pageSize) {
		User user = (User) session.getAttribute("user");
		if(pageNum==null){
			pageNum=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		int fromUserId = user.getId();
		String str = getHistoryMsg(type,id,fromUserId,pageNum,pageSize);
		return "["+str+"]";
	}

	private String getHistoryMsg(String type, Integer id, Integer fromUserId, Integer pageNum, Integer pageSize){
		if(type.equals("friend")){
			PageInfo<FriendMessage> list = friendMessageService.getHistoryMessage(fromUserId,id,pageNum,pageSize);
			JSONArray jsonArray = JSONArray.fromObject(list);
			return jsonArray.get(0).toString();
		}else if(type.equals("group")){
			PageInfo<GroupMessage> list = groupMessageService.getHistoryMessage(id,pageNum,pageSize);
			JSONArray jsonArray= JSONArray.fromObject(list);
			return jsonArray.get(0).toString();
		}
		return "";
	}

	@RequestMapping(value = "updateOnLineStatus", produces = "text/plain; charset=utf-8")
	public @ResponseBody String updateOnLineStatus(int userId,String status) {
        try{
            RedisUtils.set(userId + "_status", status);
            return "1";
        }catch(Exception e){
            e.printStackTrace();
        }
    	return "0";
	}


	@RequestMapping(value = "msgBoxPage", produces = "text/plain; charset=utf-8")
	public String msgBoxPage(HttpSession session,Model model) {
		User user = (User) session.getAttribute("user");
		int userId = user.getId();
		model.addAttribute("userId",userId);
		return "msgbox";
	}
	
}
