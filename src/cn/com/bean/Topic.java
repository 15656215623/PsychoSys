package cn.com.bean;
import org.springframework.stereotype.Repository;
@Repository(value="topic")
public class Topic {
private int id;
private String kinds;
private String subject;
private String instroduce;
private String img;
private int number;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getKinds() {
	return kinds;
}
public void setKinds(String kinds) {
	this.kinds = kinds;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getInstroduce() {
	return instroduce;
}
public void setInstroduce(String instroduce) {
	this.instroduce = instroduce;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
}
