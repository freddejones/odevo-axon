package com.odevo.WorkOrderApplication.workorder.internal.assign;

public class PersonNotFoundException extends RuntimeException {
  public PersonNotFoundException(String message) {
    super(message);
  }
}
