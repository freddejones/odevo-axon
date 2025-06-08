package com.odevo.WorkOrderApplication.workorder.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssignWorkOrderCommand {

  @TargetAggregateIdentifier
  private UUID workOrderId;

  private UUID personId;

}
