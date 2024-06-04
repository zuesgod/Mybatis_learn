package com.zues.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Mybatis工具类
 */
public class MybatisUtils {


    private static SqlSessionFactory factory;

    /**
     * 工具类的构造方法都是私有化的
     * 工具类中所有的方法都是静态的，直接采用类名即可调用，不需要创建对象
     */
    private MybatisUtils(){}

    /*
     * 全局使用一个SqlSession对象
     */
    static {
        try {
            //读取MyBatis的核心配置文件
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            //创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
            //通过核心配置文件所对应的字节输入流工厂类SqlSessionFactoryBuilder，生产sqlSession对象
            factory = factoryBuilder.build(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通用获取Dao接口
     * @param t 任意Mapper接口
     */
    public static <T> T getDao(Class<T> t) throws IOException {
        //创建sqlSession对象，此时通过sqlSession对象所操作的sql都必须手动提交或回滚事务
        //创建sqlSession对象，此时通过sqlSession对象所操作的sql都会自动提交
        SqlSession sqlSession = factory.openSession(true);
        //通过代理模式创建UserDao接口的代理实现类对象
        return sqlSession.getMapper(t);
    }

}
