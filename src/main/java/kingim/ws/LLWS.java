package kingim.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import net.sf.json.JSONArray;
import kingim.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/LLWS/{userId}")
public class LLWS {

	private static Logger logger = LoggerFactory.getLogger(LLWS.class);

    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    private static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();  
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();
 
    //连接建立成功调用的方法 
    @OnOpen  
    public void onOpen(Session session,@PathParam("userId") Integer userId) {
		String jsonString="{'content':'online','id':"+userId+",'type':'onlineStatus'}";
		for(Session s:session.getOpenSessions()){	   //循环发给所有在线的人
			s.getAsyncRemote().sendText(jsonString);   //上线通知
		}
		mapUS.put(userId+"",session);
		mapSU.put(session,userId+"");
		//更新redis中的用户在线状态
		RedisUtils.set(userId+"_status","online");
		logger.info("用户{}进入llws,当前在线人数为{}", userId, mapUS.size());
	}
  
    //连接关闭调用的方法
    @OnClose
    public void onClose(Session session) { 
    	String userId=mapSU.get(session);
    	if( userId != null && userId != "" ){
    	 	//更新redis中的用户在线状态
    		RedisUtils.set(userId+"_status","offline");
			String jsonString="{'content':'offline','id':"+userId+",'type':'onlineStatus'}";
			for(Session s:session.getOpenSessions()){	   //循环发给所有在线的人
				s.getAsyncRemote().sendText(jsonString);   //下线通知
			}
        	mapUS.remove(userId);
        	mapSU.remove(session);
			RedisUtils.set(userId+"_logoutTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			logger.info("用户{}退出llws,当前在线人数为{}", userId, mapUS.size());
    	}
    }  
  
    // 收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
		if(message.equals("ping")){
			//session.getAsyncRemote().sendText("pong");
		}else{
			JSONObject jsonObject=JSONObject.fromObject(message);
			String type = jsonObject.getJSONObject("to").getString("type");
			if(type.equals("onlineStatus")){
				for(Session s:session.getOpenSessions()){		//循环发给所有在线的人
					JSONObject toMessage=new JSONObject();
					toMessage.put("id", jsonObject.getJSONObject("mine").getString("id"));
					toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
					toMessage.put("type",type);
					s.getAsyncRemote().sendText(toMessage.toString());
				}
			}else if(type.equals("delMsg")){
				String toType=jsonObject.getJSONObject("to").getString("toType");
				String toId=jsonObject.getJSONObject("to").getString("id");
				String cid=jsonObject.getJSONObject("to").getString("cid");
				String userId=jsonObject.getJSONObject("mine").getString("id");
				JSONObject toMessage=new JSONObject();
				toMessage.put("cid",cid);
				toMessage.put("userId",userId);
				toMessage.put("toType",toType);
				toMessage.put("toId",toId);
				toMessage.put("type","delMsg");
				if(toType!=null && toId!=null && cid!=null){
					switch (toType) {
						case "friend":           							 //单聊撤回
							if(mapUS.containsKey(toId+"")){               //如果在线，及时推送撤回消息
								mapUS.get(toId+"").getAsyncRemote().sendText(toMessage.toString());               //发送消息给对方
								logger.info("撤回单聊-来自客户端的消息:{}", toMessage.toString());
							}else{
								// 如果是离线用户,删除保存到redis的数据
								List<Object> redisLogList = RedisUtils.getObjList(toId+"_msg");
								for (int i=0;i<redisLogList.size();i++){
									Object o = redisLogList.get(i);
									String s = o.toString();
									if (s.indexOf(cid) > -1){
										RedisUtils.removeOneOfList(toId+"_msg", o);
										break;
									}
								}
							}
							break;
						case "fankui":  										//家长与老师反馈消息撤回
							if(mapUS.containsKey(toId+"")){                  //如果在线，及时推送撤回消息
								mapUS.get(toId+"").getAsyncRemote().sendText(toMessage.toString());         //发送消息给对方
								logger.info("撤回反馈-来自客户端的消息:{}", toMessage.toString());
							}else{
								// 如果是离线用户,删除保存到redis的数据
								List<Object> redisLogList = RedisUtils.getObjList(toId+"_msg");
								for (Object o : redisLogList){
									String s = o.toString();
									if (s.indexOf(cid) > -1){
										RedisUtils.removeOneOfList(toId+"_msg", o);
										break;
									}
								}
							}
							break;
						case "group":
							//JSONArray memberList=JSONArray.fromObject(llClient.getGroupUser(Integer.parseInt(toId)));  //获取群成员userId列表
							JSONArray memberList=jsonObject.getJSONObject("to").getJSONArray("memberList");
							if(memberList.size()>0){
								for(int i=0;i<memberList.size();i++){                            //发送到在线用户(除了发送者)
									if(mapUS.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
										session=mapUS.get(memberList.get(i));
										session.getAsyncRemote().sendText(toMessage.toString());
										logger.info("撤回群聊-来自客户端的消息:{}", toMessage.toString());
									}
								}
							}
							break;
						default:
							break;
					}
				}
			}else{
				int toId = jsonObject.getJSONObject("to").getInt("id");
				SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				Date date = new Date();
				String time=df.format(date);
				jsonObject.put("time", time);
				JSONObject toMessage=new JSONObject();
				toMessage.put("avatar", jsonObject.getJSONObject("mine").getString("avatar"));
				toMessage.put("type",type);
				toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
				toMessage.put("timestamp",date.getTime());
				toMessage.put("cid",jsonObject.getJSONObject("mine").getString("cid"));
				toMessage.put("time",time);
				toMessage.put("mine",false);
				toMessage.put("username",jsonObject.getJSONObject("mine").getString("username"));
				if(type.equals("friend")||type.equals("fankui")){
					toMessage.put("id", jsonObject.getJSONObject("mine").getInt("id"));
					toMessage.put("fromid", jsonObject.getJSONObject("mine").getInt("id"));
				}else{
					toMessage.put("id", jsonObject.getJSONObject("to").getInt("id"));
					toMessage.put("fromid", jsonObject.getJSONObject("mine").getInt("id"));
				}
				switch (type) {
					case "friend":           							 //单聊,记录到mongo
						if(mapUS.containsKey(toId+"")){               //如果在线，及时推送
							mapUS.get(toId+"").getAsyncRemote().sendText(toMessage.toString());               //发送消息给对方
							logger.info("单聊-来自客户端的消息:{}",toMessage.toString() );
						}else{                                        //如果不在线 就记录到数据库，下次对方上线时推送给对方。
							RedisUtils.lpush(toId + "_msg", toMessage.toString());
							logger.info("单聊-对方不在线，消息已存入redis:{}", toMessage.toString());
						}
						break;
					case "fankui":  										//家长与老师反馈,记录到mongo
						if(mapUS.containsKey(toId+"")){                  //如果在线，及时推送
							mapUS.get(toId+"").getAsyncRemote().sendText(toMessage.toString());         //发送消息给对方
							logger.info("反馈-来自客户端的消息:{}", toMessage.toString());
						}else{
							RedisUtils.lpush(toId + "_msg", toMessage.toString());
							logger.info("反馈-来自客户端的消息:{}", toMessage.toString());
						}
						break;
					case "group":
						//JSONArray memberList=JSONArray.fromObject(llClient.getGroupUser(toId));  //获取群成员userId列表
						JSONArray memberList=jsonObject.getJSONObject("to").getJSONArray("memberList");
						if(memberList.size()>0){
							for(int i=0;i<memberList.size();i++){                            //发送到在线用户(除了发送者)
								if(mapUS.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
									synchronized(session){
										try {
											session = mapUS.get(memberList.get(i));
											session.getAsyncRemote().sendText(toMessage.toString());
											logger.info("群聊-来自客户端的消息:{}", toMessage.toString());
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}/*else if(memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
        		   							      	 //如果是发送者自己，不做任何操作。
        		   						}else{    	//如果是离线用户,数据存到redis待用户上线后推送。
        		   						   RedisUtils.lpush(memberList.get(i) + "_msg", toMessage.toString());
        		   						}*/
							}
						}
						RedisUtils.set(toId+"_qunLastTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						break;
					default:
						break;
				}
			}
		}
    }  
  
    /** 
     * 发生错误时调用 
     * @param session 
     * @param error 
     */  
    @OnError
    public void onError(Session session, Throwable error) {  
    	String userId=mapSU.get(session);
    	if( userId != null && userId != "" ){
    	 	//更新redis中的用户在线状态
    		RedisUtils.set(userId+"_status","offline");
        	mapUS.remove(userId);
        	mapSU.remove(session);
			logger.info("用户"+userId+"退出llws！当前在线人数为" + mapUS.size());
    	}
		logger.error("llws发生错误!");
        error.printStackTrace();
    }  
    
    /** 
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。 
     */  
    public void sendMessage(Session session,String message) {  
           session.getAsyncRemote().sendText(message);  
    }  
	
}
