package cn.com.lucene;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class IncrementIndex {

	public static void main(String[] args) {
		try {//1、新建一个增量索引
			IncrementIndex index = new IncrementIndex();
			// 索引的存放路径
			String path = "D:/index/index";
			// 存储索引文件记录的ID的路径
			String storeIdPath = "D:/index/id/1.txt";
			//索引id
			String storeId = "";
			// 查询当前存储的id
			storeId = index.getStoreId(storeIdPath);
			// 查询表里面新增的数据
			ResultSet rs = index.getResult(storeId);
			//为新增的数据建立索引
			index.indexBuilding(path, storeIdPath, rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void buildIndex(String indexFile, String storeIdFile) {
		try {
			String path = indexFile;// 索引文件的存放路径
			String storeIdPath = storeIdFile;// 存储ID的路径
			String storeId = "";
			storeId = getStoreId(storeIdPath);
			ResultSet rs = getResult(storeId);
			indexBuilding(path, storeIdPath, rs);
			storeId = getStoreId(storeIdPath);
			System.out.println(storeId);// 打印出这次存储起来的ID
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//查询新增的数据
	public static ResultSet getResult(String storeId) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://localhost:3306/psy";
		String userName = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		String sql = "select  * from teacher";
		ResultSet rs = stmt.executeQuery(sql + " where tid > '" + storeId
				+ "'order by tid");
		return rs;
	}

	public static boolean indexBuilding(String path, String storeIdPath,
			ResultSet rs) {
		try {
			Analyzer luceneAnalyzer = new StandardAnalyzer();
			Directory directory = FSDirectory.open(new File(path));
			IndexWriter writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_41,
							new StandardAnalyzer(Version.LUCENE_41)));
			//虽然把id更新了，但是没有为新增的数据建立索引
			String storeId = "";
			boolean indexFlag = false;
			String id;
			 Document doc = null;
			while (rs.next()) {
				//在这里把新增的字段建立索引
				id = rs.getInt("tid") + "";
				storeId = id;// 将拿到的id给storeId，这种拿法不合理，这里为了方便
				indexFlag = true;
				   doc = new Document();
	                doc.add(new Field("username",rs.getString("username"),Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("tell", rs.getString("tell"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("indentity", rs.getString("indentity"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("areas", rs.getString("areas"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("number", rs.getString("number"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("instroduce", rs.getString("instroduce"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                doc.add(new Field("time", rs.getString("time"), Field.Store.YES, Field.Index.NOT_ANALYZED));
	                writer.addDocument(doc);
			}
			writer.commit();
			writer.close();
			if (indexFlag) {
				// 将最后一个的ID存到磁盘文件中
				writeStoreId(storeIdPath, storeId);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错了" + e.getClass() + "\n   错误信息为:   "
					+ e.getMessage());
			return false;
		}

	}

	// 取得存储在磁盘中的ID
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
			if (storeId == null || storeId == "")
				storeId = "0";
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storeId;
	}

	// 将ID写入到磁盘文件中
	public static boolean writeStoreId(String path, String storeId) {
		boolean b = false;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(path);
			PrintWriter out = new PrintWriter(fw);
			out.write(storeId);
			out.close();
			fw.close();
			b = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
}