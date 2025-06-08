package com.odevo.WorkOrderApplication.persons;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PersonsService {

  static Set<Person> persons;

  static {
    EasyRandom easyRandom = new EasyRandom();
    persons = easyRandom.objects(Person.class, 2).collect(Collectors.toSet());
    log.info("Available persons: {}", persons);
  }

  public Set<Person> getAllPersons() {
    return persons;
  }

}
