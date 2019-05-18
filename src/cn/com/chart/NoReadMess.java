package cn.com.chart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
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

import com.opensymphony.xwork2.ModelDriven;

import cn.com.bean.Chart;
@Repository(value="noReadMess")
@Scope("prototype")
public class NoReadMess  implements ModelDriven<Chart>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Chart c;
	//查询未读消息
	@Transactional
	public String UserMess(){
		Session session=sf.getCurrentSession();
		//查询
		String sql="select distinct self from Chart where other=?  and lflag=0 ";
		Query query=session.createQuery(sql);
		query.setString(0, c.getOther());
		List<String> user=query.list();
		System.out.println("未读取消息用户:"+user.size());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			JSONArray json = JSONArray.fromObject(user);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//查询几条记录显示在页面上面
	//老师方
		@Transactional
		public String lqueryh() {
			Session session=sf.getCurrentSession();
			//根据时间查询前三条信息
			String sql="from Chart where self=? and other=?  order by time desc";
			Query query=session.createQuery(sql);
		    query.setString(0,c.getSelf());
		    query.setString(1,c.getOther());
		    query.setMaxResults(3);//设置查询的最大条数
			List<Chart> list=query.list();
			//倒序显示
			Collections.reverse(list);
			//先把未读的消息变成1
			String sq="update Chart set lflag=1 where self=? and other=?";
			Query quers=session.createQuery(sq);
			quers.setString(0, c.getSelf());
			quers.setString(1,c.getOther());
			quers.executeUpdate();
			//先把未读的消息变成1
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
	public Chart getModel() {
		// TODO Auto-generated method stub
		return c;
	}
}
