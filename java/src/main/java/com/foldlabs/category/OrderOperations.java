package com.foldlabs.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.ConnectionPoolDataSource;

public class OrderOperations {
  
  public static interface Command {
    void execute();
  }
  
  public static class Order {
    
    public Object getOrderKey() {
      return null;
    }
    
  }
  
  public static class ShoppingCart {

  }
  
  private HashMap<String,Object> _db;
  private ConnectionPoolDataSource dbPool;
  
  public void addOrderFrom(final ShoppingCart cart, final String userName, final Order order) throws SQLException {
    wrapInTransaction(new Command() {
      public void execute() {
        add(order, userKeyBasedOn(userName));
        addLineItemsFrom(cart, order.getOrderKey());
      }
    });
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
  
  public void addLineItemsFrom(ShoppingCart cart, Object orderKey) {
    // TODO Auto-generated method stub
    
  }
  
  public void add(Order order, Object userKeyBasedOn) {
    // TODO Auto-generated method stub
    
  }
  
  private Object userKeyBasedOn(String userName) {
    // TODO Auto-generated method stub
    return null;
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
