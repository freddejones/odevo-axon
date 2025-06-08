package com.odevo.WorkOrderApplication.workorder.internal.create;

import com.odevo.WorkOrderApplication.workorder.WorkOrderCreateDTO;
import com.odevo.WorkOrderApplication.workorder.WorkOrderIdDTO;
import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import com.odevo.WorkOrderApplication.workorder.domain.commands.CreateWorkOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkOrderService {

  private final CommandGateway commandGateway;

  @Autowired
  public CreateWorkOrderService(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  public WorkOrderIdDTO create(WorkOrderCreateDTO workOrderCreateDTO) {
    WorkOrderId workOrderId =
            commandGateway.sendAndWait(CreateWorkOrderCommand.init(workOrderCreateDTO.getInstruction()));
    return new WorkOrderIdDTO(workOrderId.getId());
  }

}
