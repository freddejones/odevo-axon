package com.odevo.WorkOrderApplication.workorder.domain;

import com.odevo.WorkOrderApplication.workorder.domain.commands.AssignWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.commands.CreateWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.commands.ExecuteWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderAssignedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderCreatedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderExecutedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.exception.WorkOrderUnassignedException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.*;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class WorkOrderAggregate {

  @AggregateIdentifier
  private UUID workOrderId;
  private boolean assigned;
  private boolean executed;

  private WorkOrderAggregate() {}

  @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
  @CommandHandler
  public WorkOrderId createWorkOrder(CreateWorkOrderCommand cmd) {
    AggregateLifecycle.apply(new WorkOrderCreatedEvent(cmd.getWorkOrderId(), cmd.getInstruction()));
    return new WorkOrderId(cmd.getWorkOrderId());
  }

  @CommandHandler
  public void handle(AssignWorkOrderCommand cmd) {
    if (executed || assigned) {
      return;
    }

    AggregateLifecycle.apply(new WorkOrderAssignedEvent(this.workOrderId, cmd.getPersonId()));
  }

  @CommandHandler
  public void handle(ExecuteWorkOrderCommand cmd) {

    if (executed) {
      return;
    }

    if (!assigned) {
      throw new WorkOrderUnassignedException("Cannot execute this command because it is not assigned to anyone");
    }

    AggregateLifecycle.apply(new WorkOrderExecutedEvent(this.workOrderId));
  }

  @EventSourcingHandler
  public void on(WorkOrderCreatedEvent event) {
    this.workOrderId = event.getWorkOrderId();
    this.assigned = false;
    this.executed = false;
  }

  @EventSourcingHandler
  public void on(WorkOrderAssignedEvent event) {
    this.assigned = true;
  }

  @EventSourcingHandler
  public void on(WorkOrderExecutedEvent event) {
    this.executed = true;
  }
}
