package com.zues.godbatis.core;

import com.zues.godbatis.datasource.JNDIDataSource;
import com.zues.godbatis.datasource.PooledDataSource;
import com.zues.godbatis.datasource.UNPooledDataSource;
import com.zues.godbatis.transaction.JDBCTransaction;
import com.zues.godbatis.transaction.ManagedTranscation;
import com.zues.godbatis.transaction.Transaction;
import com.zues.godbatis.utils.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlSessionFactory构建器对象
 * 通过SqlSessionFactoryBuilder的build方法来解析 goodBatis-config.xml文件
 * 然后创建SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    public  SqlSessionFactoryBuilder() {
    }

    /**
     * 解析goodBatis-config.xml文件，来构建SqlSessionFactory对象
     *
     * @param inputStream 指向goodBatis-config.xml文件的一个输入流
     * @return SqlSessionFactory对象
     */
    public SqlSessionFactory build(InputStream inputStream){
        SqlSessionFactory factory = null;
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            Element configurationElement = (Element) document.selectSingleNode("/configuration");
            Element environmentsElement = (Element) document.selectSingleNode("/configuration/environments");
            String defaultEnv = environmentsElement.attributeValue("default");
            Element environmentElt = (Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultEnv + "']");
            // 解析配置文件，创建数据源对象
            Element dataSourceElt = environmentElt.element("dataSource");
            DataSource dataSource = getDataSource(dataSourceElt);
            // 解析配置文件，创建事务管理器对象
            Element transactionManagerElt = environmentElt.element("transactionManager");
            Transaction transaction = getTransactionManager(dataSource, transactionManagerElt);
            // 解析配置文件，获取所有的SQL映射对象
            Element mappers = configurationElement.element("mappers");
            Map<String, MappedStatement> mappedStatementMap = getMapperStatements(mappers);
            // 将以上信息封装到SqlSessionFactory对象中
            factory = new SqlSessionFactory(transaction, mappedStatementMap);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return factory;
    }

    /**
     * 解析mapper.xml文件，并封装为key=sqlId  value=MappedStatement对象的Map数据
     *
     * @param mappers
     * @return
     */
    private Map<String, MappedStatement> getMapperStatements(Element mappers) {
        Map<String, MappedStatement> statementHashMap = new HashMap<>();
        mappers.elements().forEach(element -> {
            try {
                String resource = element.attributeValue("resource");
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(Resources.getResourceAsStream(resource));
                Element mapper = (Element) document.selectSingleNode("/mapper");
                String namespace = mapper.attributeValue("namespace");
                mapper.elements().forEach(sqlMapper -> {
                    String sqlId = sqlMapper.attributeValue("id");
                    String sql = sqlMapper.getTextTrim();
                    String parameterType = sqlMapper.attributeValue("parameterType");
                    String resultType = sqlMapper.attributeValue("resultType");
                    String sqlType = sqlMapper.getName().toLowerCase();
                    //封装最终的map对象
                    MappedStatement mappedStatement = new MappedStatement(sql, sqlId, sqlType, parameterType, resultType);
                    statementHashMap.put(namespace + "." + sqlId, mappedStatement);
                });

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        return statementHashMap;
    }

    /**
     * 解析xml配置获取对应的数据源对象
     *
     * @param dataSourceElt 数据源配置节点
     * @return DataSource数据源对象
     */
    private DataSource getDataSource(Element dataSourceElt) {
        Map<String, String> dataSourceMap = new HashMap<>();
        dataSourceElt.elements().forEach(propertyElt -> {
            dataSourceMap.put(propertyElt.attributeValue("name"), propertyElt.attributeValue("value"));
        });
        String dataSourceType = dataSourceElt.attributeValue("type").toUpperCase();
        DataSource dataSource = null;
        //目前该框架没有实现
        if ("POOLED".equals(dataSourceType)) {
            dataSource = new PooledDataSource();
        }

        if ("UNPOOLED".equals(dataSourceType)) {
            dataSource = new UNPooledDataSource(dataSourceMap.get("driver"), dataSourceMap.get("url"),
                    dataSourceMap.get("username"), dataSourceMap.get("password"));
        }

        //目前该框架没有实现
        if ("JNDI".equals(dataSourceType)) {
            dataSource = new JNDIDataSource();
        }

        return dataSource;
    }


    /**
     * 解析xml配置获取对应的事务管理器对象
     *
     * @param transactionManagerElt 事务管理器节点
     * @return
     */
    private Transaction getTransactionManager(DataSource dataSource, Element transactionManagerElt) {
        String type = transactionManagerElt.attributeValue("type").toUpperCase();
        Transaction transaction = null;
        if ("JDBC".equals(type)) {
            transaction = new JDBCTransaction(dataSource, false);
        }

        if ("MANAGED".equals(type)) {
            transaction = new ManagedTranscation();
        }
        return transaction;
    }
}
