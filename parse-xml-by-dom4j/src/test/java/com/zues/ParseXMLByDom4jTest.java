package com.zues;

import junit.framework.TestCase;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 测试Dom4j的xml解析
 */
public class ParseXMLByDom4jTest extends TestCase {


    /**
     * 解析Mybatis-config的核心配置文件xml
     */
    public void test1() throws DocumentException {
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        //获取输入流
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("Mybatis-config.xml");
        //读XML文件，返回document对象，document对象是文档对象，代表了整个XML文件
        Document document = saxReader.read(inputStream);
        //获取文档当中的跟标签
//        Element rootElement = document.getDocument().getRootElement();

        /*
            1.解析environments标签
            获取default默认环境的id
            xpath是做标签路径匹配的，能够让我们快速定位XML文件中的元素
         */
        String xpath = "/configuration/environments";
        //Element是Node类的子类，方法更多，使用更方便
        Element environments = ((Element) document.selectSingleNode(xpath));
        //获取environments的default属性的值
        String defaultId = environments.attributeValue("default");
        //获取到正在使用的environment标签
        Element devElement = ((Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']"));

        //获取事务管理器类型
        String transactionManagerType = devElement.element("transactionManager").attributeValue("type");
        System.out.println("事务管理器类型:" + transactionManagerType);
        //获取数据源类型
        Element dataSource = devElement.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println("数据源类型:" + dataSourceType);
        //获取数据源节点
        List<Element> dataSourceElements = dataSource.elements();
        dataSourceElements.forEach(property -> {
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            System.out.println(name + "=" + value);
        });

        /*
            获取mappers标签
            不想从根标签获取，想从任意位置开始，获取所有的某个标签，xpath该这样写
         */
        xpath = "//mapper";
        List<Node> mappers = document.selectNodes(xpath);
        mappers.forEach(mapper -> {
            Element element = (Element) mapper;
            String resource = element.attributeValue("resource");
            System.out.println("mapper配置 = " + resource);
        });

    }


    /**
     * 解析Mybatis的mapper核心配置文件xml
     */
    public void test2() throws DocumentException {
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        //获取输入流
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mapper/UserDao.xml");
        //读XML文件，返回document对象，document对象是文档对象，代表了整个XML文件
        Document document = saxReader.read(inputStream);
        String xpath = "/mapper";
        //Element是Node类的子类，方法更多，使用更方便
        Element mapper = ((Element) document.selectSingleNode(xpath));
        List<Element> sqlElements = mapper.elements();

        //遍历所有子节点的sql语句
        sqlElements.forEach(element -> {
            //获取sqlID
            String sqlId = element.attributeValue("id");
            System.out.println("sqlId = " + sqlId);

            //获取resultType
            String resultType = element.attributeValue("resultType");
            System.out.println("resultType = " + resultType);

            /*
                获取标签中的sql语句（表示获取标签中的文本内容，并且去除空白）
                select * from user where id = #{id}
                select * from user where id = ?
                mybatis封装了JDBC,早晚要执行带有?的sql语句，需要提前转换
             */
            String sqltext = element.getTextTrim();
            String newSql = sqltext.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
        });

    }

}
