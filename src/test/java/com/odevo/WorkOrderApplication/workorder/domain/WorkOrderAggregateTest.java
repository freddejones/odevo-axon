package com.odevo.WorkOrderApplication.workorder.domain;

import com.odevo.WorkOrderApplication.workorder.domain.commands.AssignWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.commands.CreateWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.commands.ExecuteWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderAssignedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderCreatedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderExecutedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.exception.WorkOrderUnassignedException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class WorkOrderAggregateTest {

  private AggregateTestFixture<WorkOrderAggregate> fixture;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(WorkOrderAggregate.class);
  }

  @Test
  void create_work_order() {
    CreateWorkOrderCommand command = CreateWorkOrderCommand.init("myinstruction");

    fixture.
            givenNoPriorActivity()
            .when(command)
            .expectEvents(new WorkOrderCreatedEvent(command.getWorkOrderId(), command.getInstruction()));
  }

  @Test
  void assign_work_order_for_unassigned_order() {
    CreateWorkOrderCommand command = CreateWorkOrderCommand.init("myinstruction");

    UUID personId = UUID.randomUUID();
    AssignWorkOrderCommand assignCommand = new AssignWorkOrderCommand(command.getWorkOrderId(), personId);

    fixture.
            givenCommands(command)
            .when(assignCommand)
            .expectEvents(new WorkOrderAssignedEvent(command.getWorkOrderId(), personId));
  }

  @Test
  void assign_work_order_for_already_assigned_order() {
    CreateWorkOrderCommand command = CreateWorkOrderCommand.init("myinstruction");
    UUID personIdOne = UUID.randomUUID();
    AssignWorkOrderCommand assignCommand = new AssignWorkOrderCommand(command.getWorkOrderId(), personIdOne);

    AssignWorkOrderCommand reAssignCommand = new AssignWorkOrderCommand(command.getWorkOrderId(), personIdOne);

    fixture.
            givenCommands(command, assignCommand)
            .when(reAssignCommand)
            .expectNoEvents();
  }

  @Test
  void execute_work_order_for_unassigned_order() {
    CreateWorkOrderCommand command = CreateWorkOrderCommand.init("myinstruction");

    ExecuteWorkOrderCommand executeCommand = new ExecuteWorkOrderCommand(command.getWorkOrderId());

    fixture.
            givenCommands(command)
            .when(executeCommand)
            .expectException(WorkOrderUnassignedException.class);
  }

  @Test
  void execute_work_order_for_assigned_order() {
    CreateWorkOrderCommand command = CreateWorkOrderCommand.init("myinstruction");
    UUID personIdOne = UUID.randomUUID();
    AssignWorkOrderCommand assignCommand = new AssignWorkOrderCommand(command.getWorkOrderId(), personIdOne);

    ExecuteWorkOrderCommand executeCommand = new ExecuteWorkOrderCommand(command.getWorkOrderId());

    fixture.
            givenCommands(command, assignCommand)
            .when(executeCommand)
            .expectEvents(new WorkOrderExecutedEvent(command.getWorkOrderId()));
  }
}