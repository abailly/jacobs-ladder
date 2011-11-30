package com.foldlabs.category;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.Order;
import com.foldlabs.category.OrderOperations.ShoppingCart;

public class InventoryOperations extends DatabaseOperations {
  
  public class Invoice {

    public Object getInvoiceKey() {
      // TODO Auto-generated method stub
      return null;
    }

  }
  
  public void prepareDispatching(final ShoppingCart cart, final String userName, final Order order) throws SQLException {
    wrapInTransaction(new Command() {
      
      @Override
      public void execute() {
        Invoice invoice = createInvoice(order);
        depleteInventoryWithItems(cart, invoice.getInvoiceKey());
      }
    });
  }
  
  protected void depleteInventoryWithItems(ShoppingCart cart, Object orderKey) {
    // TODO Auto-generated method stub
  }
  
  protected Invoice createInvoice(Order order) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
