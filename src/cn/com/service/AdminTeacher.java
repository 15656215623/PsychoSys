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

import cn.com.bean.Teacher;
import cn.com.lucene.TeacherIndex;
@Repository(value = "adminTeacher")
@Scope("prototype")
public class AdminTeacher {
	@Autowired
	private SessionFactory sf;
	private int tid;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	//	查询数据
	@Transactional
	public String show() {
		// 普通用户注册 ,用户名不能重复
		Session session = sf.getCurrentSession();
		// 查询是否重复
		String sql = "from Teacher where flag='1'";
		Query query = session.createQuery(sql);
		List<Teacher> list = query.list();
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
	//查询待审核律师用户
	@Transactional
	public String passuser() {
		// 普通用户注册 ,用户名不能重复
		Session session = sf.getCurrentSession();
		// 查询是否重复
		String sql = "from Teacher where flag='0'";
		Query query = session.createQuery(sql);
		List<Teacher> list = query.list();
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
	//审核律师用户
		@Transactional
		public String setpass() {
			// 普通用户注册 ,用户名不能重复
			Session session = sf.getCurrentSession();
			// 查询是否重复
			String sql = "update Teacher set flag='1'  where tid=?";
			Query query = session.createQuery(sql);
			query.setInteger(0, tid);
			query.executeUpdate();
			return null;
		}
	//删除数据
	@Transactional
	public String deleteuser() {
		// 普通用户注册 ,用户名不能重复
		Session session = sf.getCurrentSession();
		// 查询是否重复
		String sql = "delete Teacher where tid=?";
		Query query = session.createQuery(sql);
		query.setInteger(0,tid);
		query.executeUpdate();
		//删除的同时，把索引里面的文件删除一样
		 //对全文检索建立的索引进行修改
		TeacherIndex index=new TeacherIndex();
		String value=Integer.toString(tid);
		//先删除，再修改
		index.delete("tid", value);
		return null;
	}
	
}
