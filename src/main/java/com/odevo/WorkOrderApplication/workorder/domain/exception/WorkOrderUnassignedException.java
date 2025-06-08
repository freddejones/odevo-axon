package com.odevo.WorkOrderApplication.workorder.domain.exception;

public class WorkOrderUnassignedException extends RuntimeException {

  public WorkOrderUnassignedException(String message) {
    super(message);
  }

}
