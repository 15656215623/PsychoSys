package cn.com.service;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ModelDriven;

import cn.com.bean.Teacher;
import cn.com.bean.User;
import cn.com.lucene.TeacherIndex;
@Repository(value = "teacherLogin")
@Scope("prototype")
public class TeacherLogin implements ModelDriven<Teacher> {
private String name;
private String pass;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
@Autowired
private SessionFactory sf;
//老师登录
@Transactional
public String login(){
	Session session = sf.getCurrentSession();
	// 查询是否重复
	String sql = "from Teacher where flag='1' and username=? and password=?";
	Query query = session.createQuery(sql);
	query.setString(0, name);
	query.setString(1, pass);
	Teacher t = (Teacher) query.uniqueResult();
	String tom="";
	if(t!=null){
		tom=t.getImageUrl();
	}else{
		tom="fail";
	}
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setCharacterEncoding("utf-8");
	try {
		response.getWriter().write(tom.toString());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}
//获取老师的个人信息
@Transactional
public String info(){
	Session session = sf.getCurrentSession();
	// 查询是否重复
	String sql = "from Teacher where flag='1' and username=?";
	Query query = session.createQuery(sql);
	query.setString(0, name);
	Teacher t = (Teacher) query.uniqueResult();
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setCharacterEncoding("utf-8");
	try {
		JSONObject json=new JSONObject();
		json.put("list", t);
		response.getWriter().write(json.toString());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}
//根据id查询某个老师
@Transactional
public String showone(){
	Session session = sf.getCurrentSession();
	// 查询是否重复
	String sql = "from Teacher where flag='1' and tid=?";
	Query query = session.createQuery(sql);
	query.setInteger(0, tea.getTid());
	Teacher t = (Teacher) query.uniqueResult();
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setCharacterEncoding("utf-8");
	try {
		JSONObject json=new JSONObject();
		json.put("list", t);
		response.getWriter().write(json.toString());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}
//资料的修改
@Transactional
public String update(){
	Session session = sf.getCurrentSession();
	// 查询是否重复
	String sql = "update Teacher set time=?,tell=?,instroduce=? where username=?";
	Query query = session.createQuery(sql);
	query.setString(0, tea.getTime());	
	query.setString(1, tea.getTell());	
	query.setString(2, tea.getInstroduce());	
	query.setString(3, tea.getUsername());
	int row=query.executeUpdate();
    //对全文检索建立的索引进行修改
	TeacherIndex index=new TeacherIndex();
	int tid= tea.getTid();
	String value=Integer.toString(tid);
	//先删除，再修改
	index.delete("tid", value);
	index.update("tid",value, tea);
	return null;
}
//资料的修改
@Autowired
private Teacher tea;
public Teacher getModel() {
	// TODO Auto-generated method stub
	return tea;
}
}
