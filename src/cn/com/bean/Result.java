package cn.com.bean;
import org.springframework.stereotype.Repository;
@Repository(value="result")
public class Result {
private int rid;
private int id;//对于的题目id
private String rtitle;//总体的类型
private String rdesp;//具体结果
private int fsource;//最低
private int tsource;//最高
public int getRid() {
	return rid;
}
public void setRid(int rid) {
	this.rid = rid;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getRtitle() {
	return rtitle;
}
public void setRtitle(String rtitle) {
	this.rtitle = rtitle;
}
public String getRdesp() {
	return rdesp;
}
public void setRdesp(String rdesp) {
	this.rdesp = rdesp;
}
public int getFsource() {
	return fsource;
}
public void setFsource(int fsource) {
	this.fsource = fsource;
}
public int getTsource() {
	return tsource;
}
public void setTsource(int tsource) {
	this.tsource = tsource;
}

}
