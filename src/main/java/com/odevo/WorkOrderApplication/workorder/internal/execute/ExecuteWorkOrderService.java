package com.odevo.WorkOrderApplication.workorder.internal.execute;

import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import com.odevo.WorkOrderApplication.workorder.domain.commands.ExecuteWorkOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecuteWorkOrderService {

  private final CommandGateway commandGateway;

  @Autowired
  public ExecuteWorkOrderService(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  public void execute(WorkOrderId workOrderId) {
    ExecuteWorkOrderCommand command = new ExecuteWorkOrderCommand(workOrderId.getId());
    commandGateway.sendAndWait(command);
  }

}
