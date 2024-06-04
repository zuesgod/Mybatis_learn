package com.zues.godbatis.core;

import com.zues.godbatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.Map;

public class SqlSession {
    private Transaction transactionManager;
    private Map<String, MappedStatement> mappedStatements;

    public SqlSession(Transaction transactionManager, Map<String, MappedStatement> mappedStatements) {
        this.transactionManager = transactionManager;
        this.mappedStatements = mappedStatements;
    }

    public void commit() {
        try {
            transactionManager.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback() {
        try {
            transactionManager.getConnection().rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            transactionManager.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(String sqlId, Object obj) {

        return 1;
    }
}
