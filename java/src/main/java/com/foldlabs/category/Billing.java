package com.foldlabs.category;

import static com.foldlabs.category.DatabaseOperations.unit;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.ShoppingCart;

public class Billing {
  
  public void placeOrder(DatabaseConnection databaseConnection, final ShoppingCart cart, final String userName) throws SQLException {
    final OrderOperations order = new OrderOperations();
    final InventoryOperations inventory = new InventoryOperations();
    /* @formatter:off */
    unit(cart).bind(order.createOrder(userName))
      .bind(order.addOrderFrom(userName))
      .bind(inventory.prepareDispatching(userName))
      .commit(databaseConnection);
    /* @formatter:on */
  }
}
