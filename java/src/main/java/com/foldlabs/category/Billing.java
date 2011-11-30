package com.foldlabs.category;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.Order;
import com.foldlabs.category.OrderOperations.ShoppingCart;

public class Billing {
  
  public void placeOrder(final ShoppingCart cart, final String userName, final Order order) throws SQLException {
    DatabaseOperations db = new DatabaseOperations();
    final OrderOperations operations = new OrderOperations();
    final InventoryOperations inventory = new InventoryOperations();
    /* @formatter: off */
    db.bind(operations.addOrderFrom(cart, userName, order))
      .bind(inventory.prepareDispatching(cart, userName, order))
      .commit();
  }
}
