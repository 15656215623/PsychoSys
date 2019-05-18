package cn.com.service;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bean.User;

import com.opensymphony.xwork2.ModelDriven;
@Repository(value="userRegedit")
@Scope("prototype")
public class UserRegedit implements ModelDriven<User>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private User user;
	@Transactional
	public String regedit_user(){
         //普通用户注册   ,用户名不能重复
		Session session=sf.getCurrentSession();
		//
		String sql="from User where username=?";
		Query query=session.createQuery(sql);
		query.setString(0, user.getUsername());
		 User u=(User)query.uniqueResult();
		 String toast="";
		 String logo="http://localhost:8080/PsychoSys/img/logo.png";
		 if(u!=null){
			 toast="fail"; 
		 }else{
			 toast="success";
			 user.setLogo(logo);
			 session.save(user); 
		 }
		 HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			try {
				response.getWriter().write(toast);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null; 
	}
	@Transactional
	public String login_user(){
//		普通用户登录
		Session session=sf.getCurrentSession();
		String sql="from User where username=? and password=?";
		Query query=session.createQuery(sql);
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		 User u=(User)query.uniqueResult();
		 String toast="";
		 if(u!=null){
			 if(u.getLogo()==null||u.getLogo()==""){
				 toast="blob:http://localhost:8088/d9e245e8-5017-4862-a520-44ae7a076aea"; 
			 }
		 }else{
			 toast="fail"; 
		 }
		 //返回头像
		 HttpServletResponse response = ServletActionContext.getResponse();
		 response.setCharacterEncoding("utf-8");
			try {
				response.getWriter().write(toast);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	//忘记密码
	@Transactional
	public String forget(){
		Session  session=sf.getCurrentSession();
		String sql="update User set password=?  where username=?";
		Query query=session.createQuery(sql);
		query.setString(0,user.getPassword());
		query.setString(1,user.getUsername());
		query.executeUpdate();
		return "success";
	}
	
	public User getModel() {
  
		return user;
	}

}
