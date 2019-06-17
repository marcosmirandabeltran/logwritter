package com.creditsuisse.interview.controller;

import com.creditsuisse.interview.LogProcessorService;
import com.creditsuisse.interview.model.ReadFileRequest;
import com.creditsuisse.interview.service.EventsDBIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LogProcessorController {

    @Autowired
    LogProcessorService logProcessorService;
    @Autowired
    EventsDBIO eventsDBIO;

    @PostMapping(path = "/readFile", produces = "application/json")
    public String readFile(@RequestBody ReadFileRequest request) {
        log.info("Received request /readFile with parameter" + request.getFilePath());
        int eventsGenerated = logProcessorService.readFile(request.getFilePath());
        log.info("Generated and stored " + +eventsGenerated);
        return "Generated and stored " + +eventsGenerated;
    }

    @GetMapping("events")
    public String getEvents(@RequestParam("id") String id) {
        return "events " + eventsDBIO.getEventList(id);
    }

}
