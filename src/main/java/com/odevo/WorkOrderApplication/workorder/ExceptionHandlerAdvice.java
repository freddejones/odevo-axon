package com.odevo.WorkOrderApplication.workorder;

import com.odevo.WorkOrderApplication.workorder.domain.exception.WorkOrderUnassignedException;
import com.odevo.WorkOrderApplication.workorder.internal.assign.PersonNotFoundException;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = WorkOrderController.class)
public class ExceptionHandlerAdvice {

  @ExceptionHandler(WorkOrderUnassignedException.class)
  public ResponseEntity<Object> handleUnassignedWorkOrder(WorkOrderUnassignedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
  }

  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<Object> handlePersonNotFound(PersonNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
  }

  @ExceptionHandler(AggregateNotFoundException.class)
  public ResponseEntity<Object> handleAggregateNotFound(AggregateNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
  }

  private record ErrorResponse(int code, String message) {}
}
