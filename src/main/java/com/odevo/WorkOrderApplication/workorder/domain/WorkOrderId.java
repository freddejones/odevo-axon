package com.odevo.WorkOrderApplication.workorder.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class WorkOrderId {

  private final UUID id;
}
