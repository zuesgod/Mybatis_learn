package com.zues;

import com.zues.dao.EmployeeDao;
import com.zues.dao.UserDao;
import com.zues.entity.EmployeeEntity;
import com.zues.entity.UserEntity;
import com.zues.utils.MybatisUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * 测试增删改查
     */
    public void testApp() throws IOException {
        UserDao userDao = MybatisUtils.getDao(UserDao.class);
        //调用UserDao接口中的方法，就可以根据UserDao的全类名匹配元素文件，通过调用的方法名匹配映射文件中的SQL标签，并执行标签中的SQL语句
//        int result = userDao.insertUser();
//        int result = userDao.updateUser("god", 22, 12);
//        int result = userDao.deleteUser(12L);
        UserEntity user = userDao.getUserById(10L);
        System.out.println("user = " + user);
//        List<UserEntity> userList = userDao.getUserList();
//        userList.forEach(System.out::println);

        //查询用户为map
//        Map<String, Object> userMap = userDao.getUserToMap(10L);
//        System.out.println("userMap = " + userMap);


        //查询多条数据为map集合 第一种
//        List<Map<String, Object>> userListToMap = userDao.getUserListToMap();
//        userListToMap.forEach(System.out::println);


        //查询多条数据为map集合 第二种
//        Map<String, Object> userListToMap = userDao.getAllUserToMap();
//        System.out.println("userListToMap = " + userListToMap);

    }


    /**
     * 测试关联查询
     */
    public void test2() throws IOException {
        EmployeeDao employeeDao = MybatisUtils.getDao(EmployeeDao.class);
//        List<EmployeeEntity> emplist = employeeDao.getEmpAndDeptByEid(4L);
//        emplist.forEach(System.out::println);

        //分布查询
        EmployeeEntity empByStep = employeeDao.getEmpByStep(10L);
        System.out.println("empByStep = " + empByStep);
    }

    /**
     * 测试连接池参数
     *  注： 1.factory.openSession(false)自动提交事务需要关闭
     *      2.连接不得close，否则无法测试出效果
     */
    public void testConnectionPoolConfig() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        for (int i = 0; i < 6; i++) {
            SqlSession sqlSession = factory.openSession(false);
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            UserEntity user = userDao.getUserById(10L);
            System.out.println("user = " + user);
        }
    }
}
