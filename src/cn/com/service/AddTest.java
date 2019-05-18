package cn.com.service;
import java.io.IOException;

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

import com.opensymphony.xwork2.ModelDriven;
@Repository(value = "addTest")
@Scope("prototype")
public class AddTest implements ModelDriven<Topic>{
	@Autowired
	private SessionFactory sf;
    @Autowired
     private Topic t;
    //创建测试的题目
     @Transactional
     public String saveTopic(){
    	 Session session = sf.getCurrentSession();
    	 String [] sz={"人际","性格","能力","情感"};
    	 int i=Integer.parseInt(t.getKinds());
    	 t.setKinds(sz[i]);
         session.save(t);
         //返回Id
         String sql="from Topic where subject=?";
         Query query=session.createQuery(sql);
     	 query.setString(0, t.getSubject());
     	  Topic topic=(Topic) query.uniqueResult();
     	  int id=topic.getId();
     	 HttpServletResponse response = ServletActionContext.getResponse();
 		response.setCharacterEncoding("utf-8");
 		try {
 			response.getWriter().write(Integer.toString(id));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    	 return null;
     }
	public Topic getModel() { 
		return t;
	}
}
