package com.foldlabs.category;

import java.sql.SQLException;


public class OrderOperations extends DatabaseOperations {
  
  public static class Order {
    
    public Object getOrderKey() {
      return null;
    }
    
  }
  
  public static class ShoppingCart {

  }
  
  public void addOrderFrom(final ShoppingCart cart, final String userName, final Order order) throws SQLException {
    wrapInTransaction(new Command() {
      public void execute() {
        add(order, userKeyBasedOn(userName));
        addLineItemsFrom(cart, order.getOrderKey());
      }
    });
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
}
