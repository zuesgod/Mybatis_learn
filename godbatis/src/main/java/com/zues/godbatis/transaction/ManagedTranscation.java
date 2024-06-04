package com.zues.godbatis.transaction;


import java.sql.Connection;

/**
 * Managed事务管理器 (目前不实现了)
 */
public class ManagedTranscation implements Transaction{
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
