package cn.com.test;
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

import com.opensymphony.xwork2.ModelDriven;

import cn.com.bean.Result;
import cn.com.bean.Topic;
@Repository(value = "showResult")
@Scope("prototype")
public class ShowResult implements ModelDriven<Result>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Result re;
	//查询某个类型的题目表topic
	@Transactional
	public String showRes(){
				Session session = sf.getCurrentSession();
				//测试人数+1
				String sqls="update Topic set number=number+1 where id=?";
				Query querys = session.createQuery(sqls);
				querys.setLong(0, re.getId());
				querys.executeUpdate();
				//测试人数+1
				String sql = "from Result where id=? and tsource > ?  and  fsource <= ?";
				Query query = session.createQuery(sql);
				query.setInteger(0,re.getId());
				query.setInteger(1,re.getFsource());
				query.setInteger(2,re.getFsource());
				System.out.println(re.getId());
				System.out.println(re.getFsource());
				Result res= (Result) query.uniqueResult();
				System.out.println(res.getRtitle());
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				try {
					JSONArray json = JSONArray.fromObject(res);
					response.getWriter().write(json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
		return null;
	}
	@Override
	public Result getModel() {
		// TODO Auto-generated method stub
		return re;
	}
}
