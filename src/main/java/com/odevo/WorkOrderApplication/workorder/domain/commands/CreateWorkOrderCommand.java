package com.odevo.WorkOrderApplication.workorder.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.common.StringUtils;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateWorkOrderCommand {

  private UUID workOrderId;
  private String instruction;

  public static CreateWorkOrderCommand init(String instruction) {
    if (StringUtils.emptyOrNull(instruction)) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    return new CreateWorkOrderCommand(UUID.randomUUID(), instruction);
  }
}
