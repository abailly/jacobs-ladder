package com.foldlabs.category;

import java.sql.SQLException;

public interface Command {
  void execute() throws SQLException;
}