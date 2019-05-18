package cn.com.chart;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.hibernate.SessionFactory;

import net.sf.json.JSONObject;
@ServerEndpoint("/javasocket/{uname}")
public class SocketPart {
	//日期化
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
	//存储会话的集合,value类型是java类class SocketPart
	private static Map<String,SocketPart> map=new ConcurrentHashMap<String,SocketPart>();
    private String username;
    private Session session;
	private SessionFactory sf;
	public SessionFactory getSf() {
		return sf;
	}
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	@OnOpen
	public void open(@PathParam("uname")String username,Session session){
		System.out.println("欢迎你:"+username);
		this.username=username;
		this.session=session;
		map.put(username,this);
	}
	@OnClose
	public void close(){
		map.remove(this.username);
		try {
			this.session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("关闭");
	}
	@OnError
	public void error(Throwable t) {
		// 添加处理错误的操作
		close();
		System.out.println("发生错误");
		
		t.printStackTrace();
	}
	@OnMessage
	public void mess(String message,Session session){
		JSONObject jsonObject = JSONObject.fromObject(message);
		jsonObject.put("date", DATE_FORMAT.format(new Date()));
		//把当前集合的大小给前台，不然的话，就不知道怎么存储
		jsonObject.put("cusize",map.size());
	//接收到信息
		for (String s : map.keySet()) {
			if(this.username.equals(map.get(s).username)){
				jsonObject.put("isSelf", true);
			}else{
				jsonObject.put("isSelf", false);
			}
			map.get(s).session.getAsyncRemote().sendText(jsonObject.toString());
		}
	}
}
