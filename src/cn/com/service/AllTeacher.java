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
@Repository(value = "allTeacher")
@Scope("prototype")
public class AllTeacher{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Teacher tea;
	//当前页
    private int i;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	//统计当前用户的数量
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	// 管理员-用户模块 对普通用户和律师用户的删除，分页查询
	@Transactional
	public String ateacher() {
		Session session = sf.getCurrentSession();
		String sql = "from Teacher where flag=1";
		Query query = session.createQuery(sql);
		@SuppressWarnings("unchecked")
		List<Teacher> list = query.list();
		//总数量   第几页，从前台返回的数据
		count=list.size();
		//每页显示10条数据
		int allpage=count/8+1;//总共allpage页
		//如果当前页是-1的话，就是1；如果当前页面是最后一页+1的话，就变成1
		if(i<=0){i=1;}
		if(i>allpage){i=allpage;}
		int s=(i-1)*8;
		int e=8;
		if(i==allpage){e=count-s;}
		System.out.println("总页数是："+allpage+"i的值是："+i);
		String sqls = "from Teacher where flag=1";
		Query querys = session.createQuery(sqls);
        querys.setFirstResult(s);
        querys.setMaxResults(e);
		List<Teacher> lists = querys.list();
		// 通过Json的形式传送给前台
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			JSONArray json = JSONArray.fromObject(lists);
			response.getWriter().write(json.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
