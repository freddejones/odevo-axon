package com.odevo.WorkOrderApplication.workorder.internal.assign;

import com.odevo.WorkOrderApplication.persons.PersonsService;
import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import com.odevo.WorkOrderApplication.workorder.domain.commands.AssignWorkOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssignWorkOrderService {

  private final PersonsService personsService;
  private final CommandGateway commandGateway;

  @Autowired
  public AssignWorkOrderService(PersonsService personsService, CommandGateway commandGateway) {
    this.personsService = personsService;
    this.commandGateway = commandGateway;
  }

  public void assignPersonToWorkOrder(WorkOrderId workOrderId, UUID personId) {
    validate(personId);
    AssignWorkOrderCommand command = new AssignWorkOrderCommand(workOrderId.getId(), personId);
    commandGateway.sendAndWait(command);
  }

  private void validate(UUID personId) {
    boolean personExists = personsService.getAllPersons()
            .stream()
            .anyMatch(person -> person.getId().equals(personId));
    if (!personExists) {
      throw new PersonNotFoundException("Person to assign does not exist");
    }
  }

}
