package com.foldlabs.category;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.ShoppingCart;

public class Billing {
  
  public void placeOrder(final ShoppingCart cart, final String userName) throws SQLException {
    DatabaseOperations<ShoppingCart> db = new DatabaseOperations.Return<ShoppingCart>(cart);
    final OrderOperations order = new OrderOperations();
    final InventoryOperations inventory = new InventoryOperations();
    /* @formatter:off */
    db.bind(order.addOrderFrom(userName))
      .bind(inventory.prepareDispatching(userName))
      .commit();
    /* @formatter:on */
  }
}
