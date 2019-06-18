package com.creditsuisse.interview.controller;

import com.creditsuisse.interview.LogProcessorService;
import com.creditsuisse.interview.exceptions.FileReadingException;
import com.creditsuisse.interview.model.ReadFileRequest;
import com.creditsuisse.interview.model.ReadFileResponse;
import com.creditsuisse.interview.service.EventsDBIO;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LogProcessorController {

  @Autowired
  LogProcessorService logProcessorService;
  @Autowired
  EventsDBIO eventsDBIO;

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(path = "/readFile", produces = "application/json")
  public ReadFileResponse readFile(@RequestBody ReadFileRequest request) {
    log.info("Received request /readFile with parameter" + request.getFilePath());
    Try<Integer> tryReadFile = readFile(request.getFilePath());

    if (tryReadFile.isSuccess()) {
      int eventsGenerated = tryReadFile.get();
      log.info("Generated and stored " + +eventsGenerated);
      return new ReadFileResponse("Generated and stored " + +eventsGenerated, eventsGenerated);
    } else {
      tryReadFile.onFailure(t -> {
        log.error("Failure during the request");
      });
      throw new FileReadingException("Problem processing the file " );
    }


  }

  @GetMapping("events")
  public String getEvents(@RequestParam("id") String id) {
    return "events " + eventsDBIO.getEventList(id);
  }


  private Try<Integer> readFile(String path) {
    return Try.of(() -> logProcessorService.readFile(path));
  }


}
