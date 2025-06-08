package com.odevo.WorkOrderApplication.workorder.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WorkOrderAssignedEvent {

  private UUID workOrderId;
  private UUID personId;

}
