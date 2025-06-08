package com.odevo.WorkOrderApplication.configuration;

import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {


  @Bean
  public EmbeddedEventStore eventStore(EventStorageEngine storageEngine) {
    return EmbeddedEventStore.builder()
            .storageEngine(storageEngine)
            .build();
  }

  @Bean
  public EventStorageEngine storageEngine() {
    return new InMemoryEventStorageEngine();
  }

  @Bean
  public InMemoryTokenStore tokenStore() {
    return new InMemoryTokenStore();
  }
}
