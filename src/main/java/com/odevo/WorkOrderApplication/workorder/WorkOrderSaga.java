package com.odevo.WorkOrderApplication.workorder;

import com.odevo.WorkOrderApplication.persons.Person;
import com.odevo.WorkOrderApplication.persons.PersonsService;
import com.odevo.WorkOrderApplication.workorder.domain.commands.AssignWorkOrderCommand;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderAssignedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderCreatedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderExecutedEvent;
import com.odevo.WorkOrderApplication.workorder.internal.availablepersons.AvailablePersonsReadModel;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class WorkOrderSaga {

  @Autowired private PersonsService personsService;
  @Autowired private CommandGateway commandGateway;
  @Autowired private AvailablePersonsReadModel availablePersonsReadModel;

  @StartSaga
  @SagaEventHandler(associationProperty = "workOrderId")
  public void on(WorkOrderCreatedEvent event) {
    log.info("Received WorkOrderCreatedEvent: {}", event);
    
    UUID bestFittedPerson = getBestFittedPersonToAssign();

    // assign work order
    commandGateway.sendAndWait(
        new AssignWorkOrderCommand(event.getWorkOrderId(), bestFittedPerson));
  }

  @SagaEventHandler(associationProperty = "workOrderId")
  public void on(WorkOrderAssignedEvent event) {
    log.info("Saga still in progress, {}", event);
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "workOrderId")
  public void on(WorkOrderExecutedEvent event) {
    log.info("Work order completed: {}", event);
  }

  private UUID getBestFittedPersonToAssign() {
    // Get available persons
    Set<Person> allPersons = personsService.getAllPersons();
    Set<UUID> collect = allPersons.stream().map(Person::getId).collect(Collectors.toSet());
    UUID bestFittedPerson = availablePersonsReadModel.getBestFittedPerson(collect);
    return bestFittedPerson;
  }
}
