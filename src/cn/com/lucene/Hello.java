package cn.com.lucene;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class Hello {
    private static final String PATH_OF_FILE = "D:/img/";   // 待索引文件的目录
    private static final String PATH_OF_INDEX = "D:/index/"; // 存放索引文件的目录2
    /**
     * 测试时，要在D:/img/文件夹中准备几个包含内容的文件（比如txt格式的）
     * 然后先执行createIndex()方法，再执行searchFile()方法，最后观看控制台输出即可
     */
    public static void main(String[] args) {
        Hello h = new Hello();
        h.createIndex();
        h.searchFile();
        h.updateIndex();
    }
    //为文档创建索引
    private void createIndex() {
        Directory directory = null;//指定索引被保存的位置
        IndexWriter writer = null;//通过IndexWriter写索引
        Document doc = null;//我们索引的有可能是一段文本or数据库中的一张表
        try {
            // 这里是在硬盘上"D:/index/"文件夹中创建索引
            directory = FSDirectory.open(new File(PATH_OF_INDEX));
            // 这里通过IndexWriterConfig()构造方法的Version.LUCENE_41参数值指明索引所匹配的版本号,并使用了Lucene的标准分词器
            writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41)));
            for (File file : new File(PATH_OF_FILE).listFiles()) {
                doc = new Document();
                doc.add(new Field("content", new FileReader(file)));
                doc.add(new Field("fileName", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("filePath", file.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                writer.addDocument(doc);
            }
        }
        catch (Exception e) {
            System.out.println("创建索引的过程中遇到异常,堆栈轨迹如下");
            e.printStackTrace();
        }
        finally {
            if (null != writer) {
                try {
                    writer.close(); // IndexWriter在用完之后一定要关闭
                }
                catch (IOException ce) {
                    System.out.println("关闭IndexWriter时遇到异常,堆栈轨迹如下");
                    ce.printStackTrace();
                }
            }
        }
    }
  //检索文件
	private void searchFile() {
        IndexReader reader = null;
        try {
            reader = IndexReader.open(FSDirectory.open(new File(PATH_OF_INDEX)));
            IndexSearcher searcher = new IndexSearcher(reader);
            // 创建基于Parser搜索的Query,创建时需指定其"搜索的版本,默认搜索的域,分词器"....这里的域指的是创建索引时Field的名字
            QueryParser parser = new QueryParser(Version.LUCENE_41, "content", new StandardAnalyzer(Version.LUCENE_41));
            Query query = parser.parse("日本");       // 指定==>搜索域为content(即上一行代码指定的"content")中包含"java"的文档
            TopDocs tds = searcher.search(query, 10); // 第二个参数指定搜索后显示的条数,若查到5条则显示为5条,查到15条则只显示10条
            ScoreDoc[] sds = tds.scoreDocs;           // TopDocs中存放的并不是我们的文档,而是文档的ScoreDoc对象
            for (ScoreDoc sd : sds) {                   // ScoreDoc对象相当于每个文档的ID号,我们就可以通过ScoreDoc来遍历文档
                Document doc = searcher.doc(sd.doc);  // sd.doc得到的是文档的序号
                System.out.println(doc.get("fileName") + "[" + doc.get("filePath") + "]"); // 输出该文档所存储的信息
            }
        }
        catch (Exception e) {
            System.out.println("搜索文件的过程中遇到异常,堆栈轨迹如下");
            e.printStackTrace();
        }
        finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    System.out.println("关闭IndexReader时遇到异常,堆栈轨迹如下");
                    e.printStackTrace();
                }
            }
        }
    }
	//添加文件
	public void updateIndex() {
		IndexWriter writer = null;
		Document doc = new Document();
		try {
			 Directory directory =FSDirectory.open(new File(PATH_OF_INDEX));
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41)));
			doc.add(new Field("content", "野村小百合", Field.Store.NO,
					Field.Index.ANALYZED));
			writer.updateDocument(new Term("fileName", "1.txt"), doc);
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

	//删除文件

}