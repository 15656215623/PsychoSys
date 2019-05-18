package cn.com.tools;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class Test {
public static void main(String[] args) {
	/*author:命运的信徒
	 * date:2019/5/18
	 */
	String str ="[{'otitle':'会','source':'7'},{'otitle':'不会','source':'3'}]";
	//1.把字符串类型的json数组对象转化JSONArray
	JSONArray json=JSONArray.fromObject(str);
	//2、循环遍历这个数组
	Map<String, Integer> map = new HashMap<String, Integer>();
	 for(int i=0;i<json.size();i++){
		 //3、把里面的对象转化为JSONObject
		  JSONObject job = json.getJSONObject(i);   
		  // 4、把里面想要的参数一个个用.属性名的方式获取到
		  String key = (String) job.get("otitle");
		  String val = (String) job.get("source");
		  Integer iy=Integer.parseInt(val);
		 /* if(iy instanceof Integer){ System.out.println("true");  }else{
			  System.out.println("false");
		  }*/
			map.put(key, iy);
		  System.out.println(job.get("otitle")+":"+job.get("source")) ; 
	  }

}
}
