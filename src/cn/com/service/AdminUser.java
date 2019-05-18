package cn.com.service;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cn.com.bean.User;
@Repository(value = "adminUser")
@Scope("prototype")
public class AdminUser {
	@Autowired
	private SessionFactory sf;
	private int uid;
	
public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	//	查询数据
	@Transactional
	public String queryuser() {
		// 普通用户注册 ,用户名不能重复
		Session session = sf.getCurrentSession();
		// 查询是否重复
		String sql = "from User";
		Query query = session.createQuery(sql);
		List<User> list = query.list();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			JSONArray json = JSONArray.fromObject(list);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//删除数据
	@Transactional
	public String deleteuser() {
		// 普通用户注册 ,用户名不能重复
		Session session = sf.getCurrentSession();
		// 查询是否重复
		String sql = "delete User where uid=?";
		Query query = session.createQuery(sql);
		query.setInteger(0,uid);
		query.executeUpdate();
		return null;
	}
	
}
