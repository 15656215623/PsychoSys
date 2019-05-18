package cn.com.service;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cn.com.bean.User;
import com.zhenzi.sms.ZhenziSmsClient;
@Repository(value="getMessage")
@Scope("prototype")
public class GetMessage {
@Autowired
private SessionFactory sf;
private String tell;

public String getTell() {
	return tell;
}
public void setTell(String tell) {
	this.tell = tell;
}
@Transactional
public String sendSms() {
	//快捷登录,如果没有注册过就注册，如果有就直接登录
	//1.先查后登录
	Session session=sf.getCurrentSession();
	String sql="from User where username=?";
	Query query=session.createQuery(sql);
	query.setString(0, tell);
	 User u=(User)query.uniqueResult();
	 if(u==null){
		 User uu=new User();
		 uu.setUsername(tell);
		 uu.setPassword("000000");
		 session.save(uu);
	 }
	//1.先查后登录
	   JSONObject json=null;
		//生成6位验证码
		String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
		//发送短信
		ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "101470", "71797c5b-054b-4384-86db-eadfa22b4dd3");
		String result;
		try {
			result = client.send(tell, "在线心理测试平台，您的验证码为:" + verifyCode + ",该码有效期为5分钟，若非本人操作，请忽略这条信息");
			json = JSONObject.fromObject(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(json.getInt("code") != 0){System.out.println("发送短信失败");}
		//将验证码给前台做校验
		HttpServletResponse response=ServletActionContext.getResponse();
		JSONObject js=new JSONObject();	
		js.put("screct", verifyCode);
		try {
			response.getWriter().write(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
}
}

