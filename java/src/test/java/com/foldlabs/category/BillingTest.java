package com.foldlabs.category;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.Mockito;

public class BillingTest {
  
  @Test
  public void billingDoesAllOperationsInASingleTransaction() throws Exception {
    Billing billing = new Billing();
    DatabaseConnection db = mock(DatabaseConnection.class);
    billing.placeOrder(db, null, null);
    
    Mockito.verify(db, Mockito.times(1)).completeTransaction();
  }
  
}
