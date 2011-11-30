package com.foldlabs.category;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.Order;
import com.foldlabs.category.OrderOperations.ShoppingCart;

public class Billing {
  
  public void placeOrder(ShoppingCart cart, String userName, Order order) throws SQLException {
    OrderOperations operations = new OrderOperations();
    operations.addOrderFrom(cart, userName, order);
    
    InventoryOperations inventory = new InventoryOperations();
    inventory.prepareDispatching(cart, userName, order);
  }
}
