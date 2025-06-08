package com.odevo.WorkOrderApplication.workorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class WorkOrderReadDTO {

  @JsonProperty("workOrderId")
  private UUID id;

  @JsonProperty("instruction")
  private String instruction;

  @JsonProperty("assignee")
  private String assignee;

  @JsonProperty("status")
  private WorkOrderStatus workOrderStatus;

  public enum WorkOrderStatus {
    CREATED,
    ASSIGNED,
    EXECUTED
  }
}
