package cn.com.service;
import java.io.IOException;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.zhenzi.sms.ZhenziSmsClient;
@Repository(value="sendMessage")
@Scope("prototype")
public class SendMessage {
private String tell;
public String getTell() {
	return tell;
}
public void setTell(String tell) {
	this.tell = tell;
}
public String sendSms() {
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

