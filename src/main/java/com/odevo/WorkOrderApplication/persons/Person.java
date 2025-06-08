package com.odevo.WorkOrderApplication.persons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

  private UUID id;
  private String name;
  private String email;

}
