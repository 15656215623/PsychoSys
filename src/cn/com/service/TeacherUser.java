package cn.com.service;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cn.com.bean.Teacher;
import com.opensymphony.xwork2.ModelDriven;
@Repository(value="teacherUser")
@Scope("prototype")
public class TeacherUser implements ModelDriven<Teacher>{
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Teacher tea;
	private List<String> area;
	public List<String> getArea() {
		return area;
	}
	public void setArea(List<String> area) {
		this.area = area;
	}
	@Transactional
	public String regedit_user(){
		
         //普通用户注册   ,用户名不能重复
		Session session=sf.getCurrentSession();
		//查询是否重复
		String sql="from Teacher where username=?";
		Query query=session.createQuery(sql);
		query.setString(0, tea.getUsername());
		Teacher t=(Teacher)query.uniqueResult();
		 String toast="";
		 String [] zxs ={"国家二级咨询师","国家三级咨询师","注册系统咨询师","注册系统督导师","其他"};
		 String q="";
		 if(t!=null){
			 toast="fail"; 
		 }else{
			 //文件的拷贝
			 //处理数据
			 Integer o=Integer.parseInt(tea.getQuality());
			 tea.setQuality(zxs[o]);
			 tea.setAreas(area.toString());
			 //头像的上传和路径更改
			 //头像的上传和路径更改
			 toast="success";
			 session.save(tea); 
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
	
	public Teacher getModel() {
		return tea;
	}

}
