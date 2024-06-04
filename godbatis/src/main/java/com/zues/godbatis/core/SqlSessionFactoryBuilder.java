package com.zues.godbatis.core;

import java.io.InputStream;

/**
 * SqlSessionFactory构建器对象
 * 通过SqlSessionFactoryBuilder的build方法来解析 goodBatis-config.xml文件
 * 然后创建SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    private SqlSessionFactoryBuilder(){}

    /**
     * 解析goodBatis-config.xml文件，来构建SqlSessionFactory对象
     * @param is 指向goodBatis-config.xml文件的一个输入流
     * @return
     */
    public SqlSessionFactory build(InputStream is){


        //解析完成，构建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactory();
        return factory;
    }
}
