package com.foldlabs.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.ConnectionPoolDataSource;

public abstract class DatabaseOperations<A> {
  
  private static class Return<A> extends DatabaseOperations<A> {
    
    private final A a;
    
    public Return(A a) {
      this.a = a;
    }
    
    @Override
    protected A run() throws SQLException {
      return a;
    }
    
  }
  
  private static class Bind<A,B> extends DatabaseOperations<B> {
    
    private final DatabaseOperations<A> a;
    private final Command<A,DatabaseOperations<B>> command;
    
    public Bind(DatabaseOperations<A> a, Command<A,DatabaseOperations<B>> command) {
      this.a = a;
      this.command = command;
    }
    
    protected B run() throws SQLException {
      DatabaseOperations<B> txA = command.execute(a.run());
      return txA.run();
    }
    
  }
  
  private HashMap<String,Object> _db;
  private ConnectionPoolDataSource dbPool;
  
  public static <A> DatabaseOperations<A> unit(A a) {
    return new Return<A>(a);
  }
  
  public final <B> DatabaseOperations<B> bind(Command<A,DatabaseOperations<B>> command) throws SQLException {
    return new Bind<A,B>(this, command);
  }
  
  public final A commit() throws SQLException {
    setupDataInfrastructure();
    try {
      A a = run();
      completeTransaction();
      return a;
    } catch (SQLException sqlx) {
      rollbackTransaction();
      throw sqlx;
    } finally {
      cleanUp();
    }
  }
  
  protected abstract A run() throws SQLException;
  
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