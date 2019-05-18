package cn.com.bean;
import org.springframework.stereotype.Repository;
@Repository(value="teacher")
public class Teacher {
private int tid;//唯一标识符
private String imageUrl;
private String username;//真实姓名
private String tell;//手机号码
private String indentity;//身份证
private String areas;//擅长领域
private String quality;//从业资质
private String number;//证书编号
private String instroduce;//个人简介
private String time;//执业年限
private String password;//执业年限
private int flag;//标识符号
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getInstroduce() {
	return instroduce;
}
public void setInstroduce(String instroduce) {
	this.instroduce = instroduce;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public int getTid() {
	return tid;
}
public void setTid(int tid) {
	this.tid = tid;
}

public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getTell() {
	return tell;
}
public void setTell(String tell) {
	this.tell = tell;
}
public String getIndentity() {
	return indentity;
}
public void setIndentity(String indentity) {
	this.indentity = indentity;
}

public String getAreas() {
	return areas;
}
public void setAreas(String areas) {
	this.areas = areas;
}
public String getQuality() {
	return quality;
}
public void setQuality(String quality) {
	this.quality = quality;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
}
