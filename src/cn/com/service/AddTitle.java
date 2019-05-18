package cn.com.service;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cn.com.bean.Title;
@Repository(value = "addTitle")
@Scope("prototype")
public class AddTitle {
	@Autowired
	private SessionFactory sf;
	private String options;
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	private String title;
	// 创建测试的小题目和选项
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
   private int id;
	public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

	@Transactional
	public String createTitle() {
		Session session = sf.getCurrentSession();
		System.out.println(title);
		System.out.println(options);
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 1.把json字符串转换为数组对象
		JSONArray json = JSONArray.fromObject(options);
		// 怎么把这个数组对象转换为其他的
		for (int i = 0; i < json.size(); i++) {
			// 3、把里面的对象转化为JSONObject
			JSONObject job = json.getJSONObject(i);
			// 4、把里面想要的参数一个个用.属性名的方式获取到
			 String key = (String) job.get("otitle");
			 String val = (String) job.get("source");
			 Integer ly=Integer.parseInt(val);
			 if(ly instanceof Integer){
				 System.out.println("true");
			 }else{
				 System.out.println("false");
			 }
			 map.put(key, ly);
		}
		Title t = new Title();
		t.setTitle(title);
		t.setId(id);
		t.setOptions(map);
		session.save(t);
		return null;
	}

}
