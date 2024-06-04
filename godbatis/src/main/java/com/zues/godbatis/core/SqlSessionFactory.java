package com.zues.godbatis.core;

import com.zues.godbatis.transaction.Transaction;

import java.util.Map;

/**
 * SqlSessionFactory对象
 *          一个数据库对应一个SqlSessionFactory对象
 *          通过SqlSessionFactory对象可以获取SqlSession对象(开启会话)
 *          一个SqlSessionFactory对象可以开启多个SqlSession会话
 */
public class SqlSessionFactory {

    /**
     * 事务管理器属性
     */
    private Transaction transaction;

    /**
     * 存放sql语句的Map集合
     * key是sqlId
     * value是对应SQL标签的信息对象
     */
    private Map<String,MappedStatement> mappedStatements;

//    public SqlSession openSession(){};
}
