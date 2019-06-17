package com.creditsuisse.interview.service;

import com.creditsuisse.interview.entity.EventEntity;
import com.creditsuisse.interview.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class EventGeneratorService {

  @Autowired
  private EventsDBIO eventsDBIO;

  Map<String, EventModel> eventMap = new ConcurrentHashMap<>();
  @Value("${alert.time}")
  private int alertTime;

  public void logEvent(EventModel newEvent) {
    if (eventMap.putIfAbsent(newEvent.getId(), newEvent) != null) {
      eventMap.computeIfPresent(newEvent.getId(), (id, oldEvent) -> {
        if (oldEvent.getStatus() != newEvent.getStatus()) {
          buildEventEntry(newEvent, oldEvent);
          eventMap.remove(id);
        }
        return null;
      });
    }
  }

  private synchronized EventEntity buildEventEntry(EventModel event1, EventModel event2) {
    long time = Math.abs(
        event1.getTimestamp().getTime()
            - event2.getTimestamp().getTime());

    return eventsDBIO.add(
        EventEntity.builder()
            .duration(time)
            .alert(time > alertTime)
            .eventId(event1.getId())
            .host(event1.getHost())
            .type(event1.getType())
            .build());
  }
}
