package com.creditsuisse.interview.service;

import com.creditsuisse.interview.LogProcessorService;
import com.creditsuisse.interview.exceptions.EventProcessingException;
import com.creditsuisse.interview.model.EventModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class LogProcessorServiceImpl implements LogProcessorService {

  @Autowired
  EventsDBIO eventsDBIO;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private EventGeneratorService eventGeneratorService;

  public int readFile(String path) {
    log.info("Accessing to file on path " + path);
    try {
      Files.lines(Paths.get(path)).parallel().forEach(line -> {
        try {
          getEvent(line);
        } catch (EventProcessingException e) {
          log.warn("Problem processing line " + e.getLine() + e.getError());
        }
      });

    } catch (IOException e) {
      log.error("Problem accessing to file " + path + " - " + e.getMessage());
    } catch (Exception e) {
      log.error("General failure on file " + path + " - " + e.getMessage());
    }

    return eventsDBIO.persist();
  }


  public EventModel getEvent(String line) throws EventProcessingException {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    val eventOrE = getEventFromLine(line);

    if (eventOrE.isSuccess()) {
      eventGeneratorService.logEvent(eventOrE.get());
      return eventOrE.get();
    } else {
      throw new EventProcessingException("Failed to create an event", line);
    }

  }

  private Try<EventModel> getEventFromLine(String line) {
    return Try.of(() -> objectMapper.readValue(line, EventModel.class));
  }
}

