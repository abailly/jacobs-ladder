package com.foldlabs.category;

import java.sql.SQLException;

public interface Command<A,B> {
  B execute(A a) throws SQLException;
}