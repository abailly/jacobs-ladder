package com.foldlabs.category;

import static com.foldlabs.category.DatabaseOperations.unit;

import java.sql.SQLException;

import com.foldlabs.category.OrderOperations.Order;

public class InventoryOperations {
  
  public class Invoice {
    
    public Object getInvoiceKey() {
      return 12;
    }
    
  }
  
  public Command<Order,DatabaseOperations<Invoice>> prepareDispatching(final String userName) throws SQLException {
    return new Command<Order,DatabaseOperations<Invoice>>() {
      
      @Override
      public DatabaseOperations<Invoice> execute(Order order) {
        Invoice invoice = createInvoice(order);
        depleteInventoryWithItems(order, invoice.getInvoiceKey());
        return unit(invoice);
      }
    };
  }
  
  protected void depleteInventoryWithItems(Order order, Object orderKey) {
    // TODO Auto-generated method stub
  }
  
  protected Invoice createInvoice(Order order) {
    return new Invoice();
  }
  
}
