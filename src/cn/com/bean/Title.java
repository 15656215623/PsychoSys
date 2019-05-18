package cn.com.bean;
import java.util.Map;
import org.springframework.stereotype.Repository;
@Repository(value="title")
public class Title {
private int tid;
private int id;
private String title;
private Map<String,Integer> options;
public Map<String, Integer> getOptions() {
	return options;
}
public void setOptions(Map<String, Integer> options) {
	this.options = options;
}
public int getTid() {
	return tid;
}
public void setTid(int tid) {
	this.tid = tid;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

}
