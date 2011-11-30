package com.foldlabs.category;

import java.sql.SQLException;

public class OrderOperations {
  
  public static class Order {
    
    public Object getOrderKey() {
      return null;
    }
    
  }
  
  public static class ShoppingCart {

  }
  
  public Command<ShoppingCart,DatabaseOperations<Order>> addOrderFrom(final String userName) throws SQLException {
    return new Command<ShoppingCart,DatabaseOperations<Order>>() {
      public DatabaseOperations<Order> execute(ShoppingCart cart) {
        Order order = createOrder(cart, userName);
        add(order, userKeyBasedOn(userName));
        addLineItemsFrom(cart, order.getOrderKey());
        return new DatabaseOperations.Return<Order>(order);
      }
    };
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
  
  private Order createOrder(ShoppingCart cart, String userName) {
    return new Order();
  }
}
