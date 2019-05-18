package cn.com.chart;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.opensymphony.xwork2.ModelDriven;
import cn.com.bean.Chart;
@Repository(value = "saveChart")
@Scope("prototype")
public class SaveChart implements ModelDriven<Chart> {
	@Autowired
	private SessionFactory sf;
	@Autowired
	private Chart c;
	@Transactional
	public String insertdata() {
		org.hibernate.Session se = sf.getCurrentSession();
		se.save(c);
		return null;
	}
	@Override
	public Chart getModel() {
		// TODO Auto-generated method stub
		return c;
	}
}
