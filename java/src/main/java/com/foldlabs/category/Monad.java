package com.foldlabs.category;

import java.sql.SQLException;

public abstract class Monad<A> {
  
  static class Return<A> extends Monad<A> {
    
    private final A a;
    
    public Return(A a) {
      this.a = a;
    }
    
    @Override
    protected A run() throws SQLException {
      return a;
    }
    
  }
  
  static class Bind<A,B> extends Monad<B> {
    
    private final Monad<A> a;
    private final Command<A,Monad<B>> command;
    
    public Bind(Monad<A> a, Command<A,Monad<B>> command) {
      this.a = a;
      this.command = command;
    }
    
    protected B run() throws SQLException {
      Monad<B> txA = command.execute(a.run());
      return txA.run();
    }
    
  }
  
  public static <A> Monad<A> unit(A a) {
    return new Return<A>(a);
  }
  
  public Monad() {
    super();
  }
  
  public final <B> Monad<B> Return(B b) {
    return unit(b);
  }
  
  public final <B> Monad<B> bind(Command<A,Monad<B>> command) throws SQLException {
    return new Bind<A,B>(this, command);
  }
  
  protected abstract A run() throws SQLException;
  
}