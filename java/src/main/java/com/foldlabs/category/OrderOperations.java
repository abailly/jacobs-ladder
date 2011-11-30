package com.foldlabs.category;

import static com.foldlabs.category.DatabaseOperations.unit;

import java.sql.SQLException;

public class OrderOperations {
  
  public static class Order {
    
    public Object getOrderKey() {
      return null;
    }
    
  }
  
  public static class ShoppingCart {

  }
  
  public Command<Order,DatabaseOperations<Order>> addOrderFrom(final String userName) throws SQLException {
    return new Command<Order,DatabaseOperations<Order>>() {
      public DatabaseOperations<Order> execute(Order order) {
        add(order, userKeyBasedOn(userName));
        addLineItemsFrom(order, order.getOrderKey());
        return unit(order);
      }
    };
  }
  
  public void addLineItemsFrom(Order order, Object orderKey) {}
  
  public void add(Order order, Object userKeyBasedOn) {}
  
  private Object userKeyBasedOn(String userName) {
    return null;
  }
  
  public Command<ShoppingCart,DatabaseOperations<Order>> createOrder(final String userName) {
    return new Command<OrderOperations.ShoppingCart,DatabaseOperations<Order>>() {
      
      @Override
      public DatabaseOperations<Order> execute(ShoppingCart cart) throws SQLException {
        return unit(createOrder(cart, userName));
      }
      
      private Order createOrder(ShoppingCart cart, String userName) {
        return new Order();
      }
    };
  }
}
