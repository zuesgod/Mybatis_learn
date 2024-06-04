package com.zues.godbatis.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务管理器 (goodbatis框架目前只对JDBCTransaction进行实现)
 */
public class JDBCTransaction implements Transaction{

    /**
     * 数据源属性
     * 经典的设计: 面相接口编程
     */
    private DataSource dataSource;

    /**
     * 自动提交标志
     * true表示自动提交  默认是true
     * false表示手动提交
     */
    private boolean autoCommit = true;

    /**
     * 连接对象
     */
    private Connection connection;

    public JDBCTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    public void openConnection(){
        //懒汉式获取数据库连接
        if(connection == null){
            try {
                this.connection = dataSource.getConnection();
                this.connection.setAutoCommit(this.autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Connection getConnection(){
        return this.connection;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
