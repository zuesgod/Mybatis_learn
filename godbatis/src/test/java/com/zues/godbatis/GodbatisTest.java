package com.zues.godbatis;

import com.zues.godbatis.core.SqlSession;
import com.zues.godbatis.core.SqlSessionFactory;
import com.zues.godbatis.core.SqlSessionFactoryBuilder;
import com.zues.godbatis.utils.Resources;
import junit.framework.TestCase;

import java.io.InputStream;

public class GodbatisTest  extends TestCase {

    public void testSqlSessionFactory(){
        InputStream inputStream = Resources.getResourceAsStream("goodBatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        System.out.println("sqlSession = " + sqlSession);
    }


}
