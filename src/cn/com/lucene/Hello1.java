package cn.com.lucene;
import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class Hello1 {
	/*
	 * 定义一组数据,用来演示搜索(这里有一封邮件为例) 假设每一个变量代表一个Document,这里就定义了6个Document
	 */
	// 邮件编号
	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	// 邮件主题
	private String[] names = { "Michael", "Scofield", "Tbag", "Jack", "Jade",
			"Jadyer" };
	// 邮件地址
	private String[] emails = { "aa@jadyer.us", "bb@jadyer.cn", "cc@jadyer.cc",
			"dd@jadyer.tw", "ee@jadyer.hk", "ff@jadyer.me" };
	// 邮件内容
	private String[] contents = { "my blog", "my website", "my name",
			"I am JavaDeveloper", "I am from Haerbin", "I like Lucene" };
	// 它的创建是比较耗时耗资源的,所以这里只让它创建一次,此时reader处于整个生命周期中,实际应用中也可能直接放到ApplicationContext里面
	private static IndexReader reader = null;
	private Directory directory = null;

	public Hello1() {
		try {
			directory = FSDirectory.open(new File("D:/index/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Hello1 instance = new Hello1();
		instance.createIndex();
		instance.searchFile();
		instance.updateIndex();
		instance.getDocsCount();
	}

	/**
	 * 获取IndexReader实例
	 */
	private IndexReader getIndexReader() {
		try {
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
	 * 通过IndexReader获取文档数量
	 */
	public void getDocsCount() {
		System.out.println("maxDocs:" + this.getIndexReader().maxDoc());
		System.out.println("numDocs:" + this.getIndexReader().numDocs());
		System.out.println("deletedDocs:"
				+ this.getIndexReader().numDeletedDocs());
	}

	/**
	 * 创建索引
	 */
	public void createIndex() {
		IndexWriter writer = null;
		Document doc = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41)));
			writer.deleteAll(); // 创建索引之前,先把文档清空掉
			for (int i = 0; i < ids.length; i++) { // 遍历ID来创建文档
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("name", names[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", contents[i], Field.Store.NO,
						Field.Index.ANALYZED));
				writer.addDocument(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException ce) {
					ce.printStackTrace();
				}
			}
		}
	}
	/**
	 * 搜索文件
	 */
	public void searchFile() {
		IndexSearcher searcher = new IndexSearcher(this.getIndexReader());
		Query query = new TermQuery(new Term("content", "my")); // 精确搜索:搜索"content"中包含"my"的文档
		try {
			TopDocs tds = searcher.search(query, 10);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc); // sd.doc得到的是文档的序号
				System.out.print("(" + sd.doc + "|" + sd.score + ")"
				+ doc.get("name") + "[" + doc.get("email") + "]-->");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新索引
	 * 
	 * @see Lucene其实并未提供更新索引的方法,这里的更新操作内部是先删除再添加的方式
	 * @see 因为Lucene认为更新索引的代价,与删除后重建索引的代价,二者是差不多的
	 */
	public void updateIndex() {
		IndexWriter writer = null;
		Document doc = new Document();
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41)));
			doc.add(new Field("id", "1111", Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("name", names[0], Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("email", emails[0], Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", contents[0], Field.Store.NO,
					Field.Index.ANALYZED));
			writer.updateDocument(new Term("id", "1"), doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException ce) {
					ce.printStackTrace();
				}
			}
		}
	}

	public void deleteIndex() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41)));
			// 其参数可以传Query或Term....Query指的是可以查询出一系列的结果并将其全部删掉,而Term属于精确查找
			writer.deleteDocuments(new Term("id", "1")); // 删除索引文档中id为1的文档
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException ce) {
					ce.printStackTrace();
				}
			}
		}
	}

}