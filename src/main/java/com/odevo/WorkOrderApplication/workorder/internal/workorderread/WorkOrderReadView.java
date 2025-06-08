package com.odevo.WorkOrderApplication.workorder.internal.workorderread;

import com.odevo.WorkOrderApplication.workorder.WorkOrderReadDTO;
import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderAssignedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderCreatedEvent;
import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderExecutedEvent;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

@Component
class WorkOrderReadView {

  private final EventStore eventStore;

  public WorkOrderReadView(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @QueryHandler(queryName = "workOrderRM")
  public WorkOrderReadDTO getWorkOrderBy(WorkOrderId workOrderId) {
    // TODO: check if it's not found.
    return getWorkOrderReadDTO(workOrderId.getId());
  }

  private WorkOrderReadDTO getWorkOrderReadDTO(UUID workOrderId) {
    WorkOrderReadDTO workOrderReadDTO = new WorkOrderReadDTO();

    eventStore.readEvents(workOrderId.toString())
            .asStream()
            .forEach((Consumer<DomainEventMessage<?>>) domainEventMessage -> {
              if (domainEventMessage.getPayload() instanceof WorkOrderCreatedEvent event) {
                workOrderReadDTO.setId(event.getWorkOrderId());
                workOrderReadDTO.setInstruction(event.getInstruction());
                workOrderReadDTO.setWorkOrderStatus(WorkOrderReadDTO.WorkOrderStatus.CREATED);
              } else if (domainEventMessage.getPayload() instanceof WorkOrderAssignedEvent event) {
                workOrderReadDTO.setAssignee(event.getPersonId().toString());
                workOrderReadDTO.setWorkOrderStatus(WorkOrderReadDTO.WorkOrderStatus.ASSIGNED);
              } else if (domainEventMessage.getPayload() instanceof WorkOrderExecutedEvent) {
                workOrderReadDTO.setWorkOrderStatus(WorkOrderReadDTO.WorkOrderStatus.EXECUTED);
              }
            });

    if (workOrderReadDTO.getId() == null) {
      throw new AggregateNotFoundException(workOrderId.toString(), "WorkOrder "+ workOrderId + " does not exist");
    }

    return workOrderReadDTO;
  }

}
