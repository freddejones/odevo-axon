package com.odevo.WorkOrderApplication.workorder.internal.workorderread;

import com.odevo.WorkOrderApplication.workorder.WorkOrderReadDTO;
import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderReadService {

  private final QueryBus queryBus;

  @Autowired
  public WorkOrderReadService(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  public WorkOrderReadDTO getWorkOrder(WorkOrderId workOrderId) {
    GenericQueryMessage<WorkOrderId, WorkOrderReadDTO> query =
            new GenericQueryMessage<>(workOrderId, "workOrderRM", ResponseTypes.instanceOf(WorkOrderReadDTO.class));

    return queryBus.query(query)
            .thenApply(QueryResponseMessage::getPayload)
            .join();
  }

}
