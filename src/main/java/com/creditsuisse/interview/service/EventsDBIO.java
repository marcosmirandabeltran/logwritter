package com.creditsuisse.interview.service;

import com.creditsuisse.interview.dao.EventRepository;
import com.creditsuisse.interview.entity.EventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class EventsDBIO {

    @Autowired
    EventRepository eventRepository;

    List<EventEntity> eventList =
            Collections.synchronizedList(new ArrayList<EventEntity>());

    public EventEntity add(EventEntity event){
        eventList.add(event);
        return event;
    }

    public int persist(){
        log.info("Persisting " + eventList.size() + " events on DB");
        eventRepository.saveAll(eventList);
        return eventList.size();
    }

    public List<EventEntity> getEventList(String eventId){
        return eventRepository.findAllByEventId(eventId);
    }

}
