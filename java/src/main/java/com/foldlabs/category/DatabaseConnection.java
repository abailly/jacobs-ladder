package com.foldlabs.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.ConnectionPoolDataSource;

public class DatabaseConnection {
  
  HashMap<String,Object> _db;
  private ConnectionPoolDataSource dbPool;

  protected void cleanUp() throws SQLException {
    Connection connection = (Connection) _db.get("connection");
    boolean transactionState = ((Boolean) _db.get("transation state")).booleanValue();
    Statement s = (Statement) _db.get("statement");
    PreparedStatement ps = (PreparedStatement) _db.get("prepared statement");
    ResultSet rs = (ResultSet) _db.get("result set");
    connection.setAutoCommit(transactionState);
    connection.close();
    if (s != null) s.close();
    if (ps != null) ps.close();
    if (rs != null) rs.close();
  }

  protected void setupDataInfrastructure() throws SQLException {
    _db = new HashMap<String,Object>();
    Connection c = dbPool.getPooledConnection().getConnection();
    _db.put("connection", c);
    _db.put("transaction state", Boolean.valueOf(setupTransactionStateFor(c)));
  }

  protected void rollbackTransaction() throws SQLException {
    ((Connection) _db.get("connection")).rollback();
  }

  protected void completeTransaction() throws SQLException {
    ((Connection) _db.get("connection")).commit();
  }

  private boolean setupTransactionStateFor(Connection c) throws SQLException {
    boolean transactionState = c.getAutoCommit();
    c.setAutoCommit(false);
    return transactionState;
  }
  
}