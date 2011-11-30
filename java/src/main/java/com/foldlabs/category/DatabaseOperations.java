package com.foldlabs.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.ConnectionPoolDataSource;

public class DatabaseOperations {
  
  private HashMap<String,Object> _db;
  private ConnectionPoolDataSource dbPool;

  public DatabaseOperations() {
    super();
  }

  public void wrapInTransaction(Command command) throws SQLException {
    setupDataInfrastructure();
    try {
      command.execute();
      completeTransaction();
    } catch (SQLException sqlx) {
      rollbackTransaction();
      throw sqlx;
    } finally {
      cleanUp();
    }
  }

  private void setupDataInfrastructure() throws SQLException {
    _db = new HashMap<String,Object>();
    Connection c = dbPool.getPooledConnection().getConnection();
    _db.put("connection", c);
    _db.put("transaction state", Boolean.valueOf(setupTransactionStateFor(c)));
  }

  private void cleanUp() throws SQLException {
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

  private void rollbackTransaction() throws SQLException {
    ((Connection) _db.get("connection")).rollback();
  }

  private void completeTransaction() throws SQLException {
    ((Connection) _db.get("connection")).commit();
  }

  private boolean setupTransactionStateFor(Connection c) throws SQLException {
    boolean transactionState = c.getAutoCommit();
    c.setAutoCommit(false);
    return transactionState;
  }
  
}