package com.foldlabs.category;

import static com.foldlabs.category.DatabaseOperations.unit;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.ShoppingCart;

public class Billing {
  
  public void placeOrder(final ShoppingCart cart, final String userName) throws SQLException {
    DatabaseOperations<ShoppingCart> db = unit(cart);
    final OrderOperations order = new OrderOperations();
    final InventoryOperations inventory = new InventoryOperations();
    /* @formatter:off */
    db.bind(order.createOrder(userName))
      .bind(order.addOrderFrom(userName))
      .bind(inventory.prepareDispatching(userName))
      .commit();
    /* @formatter:on */
  }
}
