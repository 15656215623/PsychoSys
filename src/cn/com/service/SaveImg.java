package cn.com.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cn.com.tools.RandomStr;
@Repository(value="saveImg")
@Scope("prototype")
public class SaveImg {
	private File file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Transactional
	public String copyfile(){
		//url
		 String url="http://localhost:8080/PsychoSys/img/";
		 String fn=RandomStr.getRandomString(10);
		 //文件的拷贝
			//存储图片的地址
			 HttpServletRequest request = ServletActionContext.getRequest();
			String dsk=request.getSession().getServletContext().getRealPath("/img");
			String cfn=dsk+"/"+fn+".jpg";
			File fl=new File(cfn);
			FileOutputStream fout=null;
			InputStream in=null;
			try {
			     fout=new FileOutputStream(fl);
				in=new FileInputStream(file);
				byte [] by=new byte[1024];
				int length=0;
				try {
					while((length=in.read(by))>-1){
						fout.write(by, 0, length);
					}
					url=url+fn+".jpg";
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(in!=null){
						in.close();
					}
					if(fout!=null){
						fout.close();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		//把地址
			 HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				try {
					response.getWriter().write(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return null;
	}
}
