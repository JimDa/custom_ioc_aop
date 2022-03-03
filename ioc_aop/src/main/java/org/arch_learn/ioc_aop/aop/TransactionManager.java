package org.arch_learn.ioc_aop.aop;

import org.arch_learn.orm.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    public TransactionManager() {
    }
    public static void beginTransaction() throws SQLException {
        Connection currentConnection = ConnectionUtils.getCurrentConnection();
        currentConnection.setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        Connection currentConnection = ConnectionUtils.getCurrentConnection();
        currentConnection.commit();
    }

    public static void rollBack() throws SQLException {
        Connection currentConnection = ConnectionUtils.getCurrentConnection();
        currentConnection.rollback();
    }

    public static void close() throws SQLException {
        Connection currentConnection = ConnectionUtils.getCurrentConnection();
        currentConnection.close();
    }
}
