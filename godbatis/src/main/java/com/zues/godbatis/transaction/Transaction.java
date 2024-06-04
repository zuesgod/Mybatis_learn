package com.zues.godbatis.transaction;

import java.sql.Connection;

public interface Transaction {

    /**
     * 事务提交
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();

    /**
     * 事务关闭
     */
    void close();


    /**
     * 开启数据库连接
     */
    void openConnection();

    /**
     * 获取连接对象
     * @return Connection
     */
    Connection getConnection();
}
