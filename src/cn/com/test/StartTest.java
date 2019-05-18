package cn.com.test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.opensymphony.xwork2.ModelDriven;

import cn.com.bean.Title;
import cn.com.bean.Topic;
@Repository(value = "startTest")
@Scope("prototype")
public class StartTest implements ModelDriven<Title>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Title t;
	//最关键的部分，根据题目， 显示标题和选项
	@Transactional
	public String options(){
		Session session = sf.getCurrentSession();
		String sql = "from Title  where id=?";
		Query query = session.createQuery(sql);
		query.setInteger(0, t.getId());
		List<Title> list= query.list();
		//数据处理
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
	@Override
	public Title getModel() {
		// TODO Auto-generated method stub
		return t;
	}
}
