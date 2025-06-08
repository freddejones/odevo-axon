package com.odevo.WorkOrderApplication.workorder.internal.availablepersons;

import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderAssignedEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.odevo.WorkOrderApplication.workorder.domain.events.WorkOrderExecutedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Read model for getting the best fitted person to be assigned for next work order
 */
@Component
public class AvailablePersonsReadModel {

  private final Map<UUID, AssignedPerson> assignedPersons = new HashMap<>();

  @EventHandler
  public void on(WorkOrderAssignedEvent event) {
    assignedPersons.put(event.getWorkOrderId(), new AssignedPerson(event.getPersonId()));
  }

  @EventHandler
  public void on(WorkOrderExecutedEvent event) {
    assignedPersons.remove(event.getWorkOrderId());
  }

  public UUID getBestFittedPerson(Set<UUID> personIds) {
    Map<UUID, Long> assignedWorkOrderCount = new HashMap<>();

    for (AssignedPerson assignedPerson : assignedPersons.values()) {
      assignedWorkOrderCount.merge(assignedPerson.personId(), 1L, Long::sum);
    }

    UUID bestFittedPerson = null;
    long minAssignedWorkOrders = Long.MAX_VALUE;

    for (UUID personId : personIds) {
      long count = assignedWorkOrderCount.getOrDefault(personId, 0L);

      if (count < minAssignedWorkOrders) {
        minAssignedWorkOrders = count;
        bestFittedPerson = personId;
      }
    }

    return bestFittedPerson;
  }

  public record AssignedPerson(UUID personId) {}
}
