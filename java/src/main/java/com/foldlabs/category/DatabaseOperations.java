package com.foldlabs.category;

import java.sql.SQLException;

public abstract class DatabaseOperations<A> extends DatabaseConnection {
  
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
  
  public static <A> DatabaseOperations<A> unit(A a) {
    return new Return<A>(a);
  }
  
  public final <B> DatabaseOperations<B> bind(Command<A,DatabaseOperations<B>> command) throws SQLException {
    return new Bind<A,B>(this, command);
  }
  
  public final A commit(DatabaseConnection db) throws SQLException {
    db.setupDataInfrastructure();
    try {
      A a = run();
      db.completeTransaction();
      return a;
    } catch (SQLException sqlx) {
      db.rollbackTransaction();
      throw sqlx;
    } finally {
      db.cleanUp();
    }
  }
  
  protected abstract A run() throws SQLException;
  
}