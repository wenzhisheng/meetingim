package kingim.ws;

import java.util.ArrayList;
import java.util.List;
import kingim.model.*;
import kingim.service.GroupMessageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import kingim.service.GroupService;
import kingim.service.GroupUserService;
import kingim.vo.SNSMData;
import kingim.vo.SNSMember;
import kingim.vo.SNSUser;

@Controller
@RequestMapping("/api/qun")
public class QunWS {
 
  @Autowired
  private GroupUserService groupUserService;
  @Autowired
  private GroupService groupService;
  @Autowired
  private GroupMessageService groupMessageService;

	//获取群成员列表(只含用户名)
	@ResponseBody
	@RequestMapping(value = "/getSimpleMemberByGroupId", produces = "text/plain; charset=utf-8")
	public String getSimpleMemberByGroupId(int groupId){
		return JSON.toJSONString(groupUserService.getSimpleMemberByGroupId(groupId));
	}
	 
	//获取我的群组列表(加入的)
	@ResponseBody
	@RequestMapping(value = "/getGroupByUserId", produces = "text/plain; charset=utf-8")
	public String getGroupByUserId(int userId){
		List<Group> list = groupUserService.getByUserId(userId);
		return JSON.toJSONString(list);
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
			GroupMessage msg = new GroupMessage();
			msg.setContent((String)mine.get("content"));
			msg.setGroupId((Integer) to.get("id"));
			msg.setUserId((Integer) mine.get("id"));
			msg.setCid((String)mine.get("cid"));
			msg.setIsRead(0);
			msg.setIsDel(0);
			msg.setIsBack(0);
			result = groupMessageService.save(msg)+"";
		}
		return result;
	}
	
	//获取群成员信息 for sns
	@ResponseBody
	@RequestMapping(value = "/getByGroupId", produces = "text/plain; charset=utf-8")
	public String getByGroupId(int id){
		List<GroupUser> list = groupUserService.getByGroupId(id);
		User groupMaster = groupService.getGroupMaster(id);
		SNSMember member = new SNSMember();
		List<SNSUser> userList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			SNSUser snsUser =new SNSUser();
			snsUser.setId(list.get(i).getId());
			snsUser.setAvatar(list.get(i).getUser().getAvatar());
			snsUser.setSign("无个性不签名");
			snsUser.setUsername(list.get(i).getUser().getNickName());
			userList.add(snsUser);
		}
		SNSUser snsMaster=new SNSUser();
		snsMaster.setAvatar(groupMaster.getAvatar());
		snsMaster.setId(groupMaster.getId());
		snsMaster.setSign(groupMaster.getSign());
		snsMaster.setUsername(groupMaster.getNickName());
		SNSMData data = new SNSMData();
		data.setList(userList);
		data.setOwner(snsMaster);
		member.setCode(0);
		member.setData(data);
		return JSON.toJSONString(member);
	}
}
