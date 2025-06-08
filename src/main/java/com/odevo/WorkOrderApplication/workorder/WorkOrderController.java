package com.odevo.WorkOrderApplication.workorder;

import com.odevo.WorkOrderApplication.workorder.domain.WorkOrderId;
import com.odevo.WorkOrderApplication.workorder.internal.assign.AssignWorkOrderService;
import com.odevo.WorkOrderApplication.workorder.internal.create.CreateWorkOrderService;
import com.odevo.WorkOrderApplication.workorder.internal.execute.ExecuteWorkOrderService;
import com.odevo.WorkOrderApplication.workorder.internal.workorderread.WorkOrderReadService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/workorders")
public class WorkOrderController {

  private final CreateWorkOrderService workOrderService;
  private final AssignWorkOrderService assignWorkOrderService;
  private final ExecuteWorkOrderService executeWorkOrderService;
  private final WorkOrderReadService workOrderReadService;

  @Autowired
  public WorkOrderController(
      CreateWorkOrderService workOrderService,
      AssignWorkOrderService assignWorkOrderService,
      ExecuteWorkOrderService executeWorkOrderService,
      WorkOrderReadService workOrderReadService) {
    this.workOrderService = workOrderService;
    this.assignWorkOrderService = assignWorkOrderService;
    this.executeWorkOrderService = executeWorkOrderService;
    this.workOrderReadService = workOrderReadService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<WorkOrderReadDTO> getWorkOrder(@PathVariable UUID id) {
    WorkOrderReadDTO workOrder = workOrderReadService.getWorkOrder(new WorkOrderId(id));
    return ResponseEntity.ok(workOrder);
  }

  @PostMapping("")
  public ResponseEntity<WorkOrderIdDTO> createWorkOrder(
      @RequestBody WorkOrderCreateDTO workOrderCreateDTO) {
    WorkOrderIdDTO workOrderIdDTO = workOrderService.create(workOrderCreateDTO);
    return ResponseEntity.ok(workOrderIdDTO);
  }

  @PutMapping("/{id}/assignee")
  public ResponseEntity<Void> assignWorkOrderToPerson(
      @PathVariable UUID id, @RequestBody WorkOrderAssignDTO workOrderAssignDTO) {
    assignWorkOrderService.assignPersonToWorkOrder(
        new WorkOrderId(id), workOrderAssignDTO.getPersonId());
    return ResponseEntity.ok(null);
  }

  @PutMapping("/{id}/execution")
  public ResponseEntity<Void> executeWorkOrder(@PathVariable UUID id) {
    executeWorkOrderService.execute(new WorkOrderId(id));
    return ResponseEntity.ok(null);
  }
}
