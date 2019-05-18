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
import cn.com.bean.Topic;
@Repository(value = "showTopic")
@Scope("prototype")
public class ShowTopic implements ModelDriven<Topic>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Topic topic;
	//查询某个类型的题目表topic
	@Transactional
	public String showOne(){
				Session session = sf.getCurrentSession();
				String sql = "from Topic where kinds=?";
				Query query = session.createQuery(sql);
				query.setString(0, topic.getKinds());
				List<Topic> list= query.list();
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
	//根据id查询具体的topic表
	@Transactional
	public String showtopic(){
		Session session = sf.getCurrentSession();
		String sql = "from Topic where id=?";
		Query query = session.createQuery(sql);
		query.setInteger(0, topic.getId());
		Topic t= (Topic) query.uniqueResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			JSONArray json = JSONArray.fromObject(t);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Topic getModel() {
		// TODO Auto-generated method stub
		return topic;
	}
}
