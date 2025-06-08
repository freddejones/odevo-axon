package com.odevo.WorkOrderApplication.workorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderCreateDTO {

  @JsonProperty(value = "instruction", required = true)
  private String instruction;

}
