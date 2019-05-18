package cn.com.lucene;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bean.Teacher;
@Repository(value = "teacherIndex")
@Scope("prototype")
public class TeacherIndex {
/*author:命运的信徒
 * arm:为表teacher建立索引
 * 并且进行查询
 */
	String indexpath="D:/index/index";
	String idpath="D:/index/id/1.txt";
	private static IndexReader reader = null;
	private Directory directory = null;
	@Autowired
	private SessionFactory sf;
	private String content;
	private String key;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	//对数据库中的表建立索引
	@Transactional
	public String CreateIndex(){
		//1.查找idpath里面的数据，如果是null的或者不存在的话，就创建，然后输入0进去
		String storeId=getStoreId(idpath);
		//2.如果有数据，然后就开始查询数据库中大于这个数据的结果集
		Session session=sf.getCurrentSession();
		List<Teacher> list=getResult(storeId,session);
		//3.为这个结果集建立索引，把这个结果集中最大的id，写到idpath里面去
		if(list.size()>0){
			indexBuilding(indexpath,idpath,list);
		}
		//4.开始搜索功能了
		List<Teacher> lt=searchFile(content,key);
		//把符合条件的数据都显示在页面上面
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		JSONArray js=JSONArray.fromObject(lt);
		try {
			response.getWriter().write(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取IndexReader实例
	 */
	private IndexReader getIndexReader() {
		try {
			directory=FSDirectory.open(new File(indexpath));
			if (reader == null) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader ir = IndexReader.open(directory);
				if (ir != null) {
					reader.close(); // 关闭原reader
					reader = ir; // 赋予新reader
				}
			}
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // 发生异常则返回null
	}
	/**
	 * 搜索文件
	 */
	public List<Teacher> searchFile(String content,String keyword) {
		List<Teacher> list=new ArrayList<Teacher>();
		IndexSearcher searcher = new IndexSearcher(this.getIndexReader());
	    //创建一个查询条件解析器
        QueryParser parser = new QueryParser(content,new StandardAnalyzer());
        org.apache.lucene.search.Query query=null;
		try {
			query = parser.parse(keyword);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			TopDocs tds = searcher.search(query, 10);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc); // sd.doc得到的是文档的序号
				Teacher tt=parse(doc);
				list.add(tt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	//获取存储的id
		public static String getStoreId(String path) {
			String storeId = "";
			try {
				File file = new File(path);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileReader fr = new FileReader(path);
				BufferedReader br = new BufferedReader(fr);
				storeId = br.readLine();
				if (storeId == null || storeId == ""){
					storeId = "0";
				}
					
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return storeId;
		}
		//查询新增的数据
		public  List<Teacher> getResult(String storeId,Session session){
			String sql="from Teacher where tid > ? and flag=1 order by tid asc";
			Query query=session.createQuery(sql);
			int tid=Integer.parseInt(storeId);
			query.setInteger(0, tid);
			List<Teacher> list=query.list();
			return list;
		}
		//把新增的数据建立索引文件
		public static boolean indexBuilding(String path, String storeIdPath,
				List<Teacher> list) {
			try {
				Analyzer luceneAnalyzer = new StandardAnalyzer();
				Directory directory = FSDirectory.open(new File(path));
				IndexWriter writer = new IndexWriter(directory,
						new IndexWriterConfig(Version.LUCENE_41,luceneAnalyzer));
				int id = 0;
				 Document doc = null;
				 for (Teacher t : list) {
					 doc=TeacherIndex.addIndex(t);
					 writer.addDocument(doc);
					 id=t.getTid();
				}
				 //获取新的id
				writer.commit();
				writer.close();
				writeStoreId(storeIdPath,Integer.toString(id));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("出错了" + e.getClass() + "\n   错误信息为:   "
						+ e.getMessage());
				return false;
			}

		}
        //增加索引的类,把对象转换成文本类型

		public static  Document  addIndex(Teacher t){
			Document doc=new Document();
			doc.add(new Field("tid", Integer.toString(t.getTid()),Field.Store.YES,Field.Index.NOT_ANALYZED));
			doc.add(new TextField("imageUrl",t.getImageUrl(),Field.Store.YES));
			doc.add(new TextField("username",t.getUsername(),Field.Store.YES));
			doc.add(new TextField("tell",t.getTell(),Field.Store.YES));
			doc.add(new TextField("indentity",t.getIndentity(),Field.Store.YES));
			doc.add(new TextField("areas", t.getAreas(), Field.Store.YES));
			doc.add(new TextField("quality",t.getQuality(),Field.Store.YES));
			doc.add(new TextField("number",t.getNumber(),Field.Store.YES));
			doc.add(new TextField("instroduce",t.getInstroduce(),Field.Store.YES));
			doc.add(new TextField("time",t.getTime(),Field.Store.YES));
			doc.add(new IntField("flag",t.getFlag(),Field.Store.YES));
			doc.add(new TextField("password",t.getPassword(),Field.Store.YES));
			
			return doc;
		}
		//把文本类型转换为对象
		public static Teacher parse(Document doc){
			Teacher tea=new Teacher();
			int id=Integer.parseInt(doc.get("tid"));
			tea.setTid(id);
			tea.setImageUrl(doc.get("imageUrl"));
			tea.setUsername(doc.get("username"));
			tea.setTell(doc.get("tell"));
			tea.setIndentity(doc.get("indentity"));
			tea.setAreas(doc.get("areas"));
			tea.setQuality(doc.get("quality"));
			tea.setNumber(doc.get("number"));
			tea.setInstroduce(doc.get("instroduce"));
			tea.setTime(doc.get("time"));
			tea.setPassword(doc.get("pasword"));
			
			System.out.println(tea.getUsername());
			return tea;
		}
		//写id入1.txt文件
		public static boolean writeStoreId(String path, String storeId) {
			boolean b = false;
			try {
				File file = new File(path);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(path);
				PrintWriter out = new PrintWriter(fw);
				System.out.println("要存储的id:"+storeId);
				out.write(storeId);
				out.close();
				fw.close();
				b = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return b;
		}

		//删除操作
		public void delete(String key,String value){
			try {
				Analyzer analyzer = new StandardAnalyzer();
				Directory directory = FSDirectory.open(new File(indexpath));
				IndexWriter writer;
				writer = new IndexWriter(directory,
						new IndexWriterConfig(Version.LUCENE_41,analyzer));
				//精准删除-对应的字段一定不能是分词的
				writer.deleteDocuments(new Term(key,value));
				writer.commit();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//修改操作
		@SuppressWarnings("deprecation")
		public void update(String key,String value,Teacher tt){
			try {
				Analyzer luceneAnalyzer = new StandardAnalyzer();
				Directory directory = FSDirectory.open(new File(indexpath));
				IndexWriter writer;
				writer = new IndexWriter(directory,
						new IndexWriterConfig(Version.LUCENE_41,luceneAnalyzer));
				Document doc=addIndex(tt);
				writer.updateDocument(new Term(key,value), doc);
				writer.commit();
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
}
