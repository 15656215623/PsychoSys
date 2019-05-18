package cn.com.bean;
import org.springframework.stereotype.Repository;
@Repository(value="chart")
public class Chart {
//存储聊天记录的;
private int cid;
private String self;
private String other;
private String active;
private String content;
private String time;
private int uflag;
private int lflag;
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public String getSelf() {
	return self;
}
public void setSelf(String self) {
	this.self = self;
}
public String getOther() {
	return other;
}
public void setOther(String other) {
	this.other = other;
}
public String getActive() {
	return active;
}
public void setActive(String active) {
	this.active = active;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getUflag() {
	return uflag;
}
public void setUflag(int uflag) {
	this.uflag = uflag;
}
public int getLflag() {
	return lflag;
}
public void setLflag(int lflag) {
	this.lflag = lflag;
}

}
