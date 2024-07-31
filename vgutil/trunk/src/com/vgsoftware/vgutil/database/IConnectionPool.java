package com.vgsoftware.vgutil.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An interface defining common methods that a connection pool should
 * implement.
 *
 * @author Viktor Gars
 * @version 1.0
 */
public interface IConnectionPool
{
    public Connection getConnection() throws SQLException;
    public Connection getConnection(long timeout) throws SQLException;
    public void releaseConnection(Connection conn) throws SQLException;
    public void shutdown() throws SQLException;
}
