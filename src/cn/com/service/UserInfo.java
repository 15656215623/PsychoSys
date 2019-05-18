package cn.com.service;
import java.io.IOException;
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
@Repository(value="userInfo")
@Scope("prototype")
public class UserInfo {
	@Autowired
	private SessionFactory sf;
	private String name;
	private String pass;
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//查询用户信息
	@Transactional
	public String quser(){
		Session session=sf.getCurrentSession();
		String sql="from User where username=?";
		Query query=session.createQuery(sql);
		query.setString(0, name);
		User u=(User)query.uniqueResult();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		JSONObject js=new JSONObject();	
		js.put("user", u);
		try {
			response.getWriter().write(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//修改用户信息
	@Transactional
	public String updatepass(){
		Session session=sf.getCurrentSession();
		String sql="update User set password=?  where username=?";
		Query query=session.createQuery(sql);
		query.setString(0, pass);
		query.setString(1, name);
		query.executeUpdate();
		return null;
	}
}
