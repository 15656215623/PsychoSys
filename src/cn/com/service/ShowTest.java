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
import cn.com.bean.Topic;

@Repository(value = "showTest")
@Scope("prototype")
public class ShowTest {
	@Autowired
	private SessionFactory sf;

	@Transactional
	public String Show() {
		Session session = sf.getCurrentSession();
		String sql = "from Topic";
		Query query = session.createQuery(sql);
		query.setMaxResults(4);
		List<Topic> list = query.list();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			JSONArray json = JSONArray.fromObject(list);
			response.getWriter().write(json.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
