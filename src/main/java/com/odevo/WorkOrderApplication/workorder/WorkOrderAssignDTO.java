package com.odevo.WorkOrderApplication.workorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderAssignDTO {

  private UUID personId;

}
