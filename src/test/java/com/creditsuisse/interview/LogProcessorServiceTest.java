package com.creditsuisse.interview;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;

import com.creditsuisse.interview.controller.LogProcessorController;
import com.creditsuisse.interview.entity.EventEntity;
import com.creditsuisse.interview.exceptions.FileReadingException;
import com.creditsuisse.interview.model.EventModel;
import com.creditsuisse.interview.model.ReadFileRequest;
import com.creditsuisse.interview.service.EventGeneratorService;
import com.creditsuisse.interview.service.EventsDBIO;
import com.creditsuisse.interview.service.LogProcessorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogProcessorServiceTest {

  @Mock
  ObjectMapper mapper;



  @Mock
  EventGeneratorService eventGeneratorService;

  @Mock
  private EventsDBIO eventsDBIO;


  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
  }

  @InjectMocks
  private LogProcessorService logProcessorService = new LogProcessorServiceImpl();


  @Test
  public void testExceptionProcessingFile() {
    int numberOfEntries = 5;
    Mockito.when(eventsDBIO.add(notNull())).thenReturn(EventEntity.builder().build());
    Mockito.when(eventsDBIO.persist()).thenReturn(numberOfEntries);
    String filepath = getClass().getClassLoader().getResource("jsonSample.txt").getPath();

    org.junit.jupiter.api.Assertions
        .assertThrows(FileReadingException.class, () -> logProcessorService.readFile(filepath));

  }

  @Test
  public void testValidFile() throws Exception {
    int numberOfEntries = 5;
    Mockito.when(eventsDBIO.add(notNull())).thenReturn(EventEntity.builder().build());
    Mockito.when(eventsDBIO.persist()).thenReturn(numberOfEntries);
    Mockito.when(mapper.readValue(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(new EventModel());
    Mockito.doNothing().when(eventGeneratorService).logEvent(any());
    //This very likely will fail on non windows machines, there is a problem loading paths with windows with / at the start (i.e : /C:/blabla/file.txt) ,so
    //we are removing the first character (/) this should be unified in path agnostics of the OS
    String filepath = getClass().getClassLoader().getResource("jsonSample.txt").getPath()
        .substring(1);

    try {
      int linesProcessed = logProcessorService.readFile(filepath);
      assert(linesProcessed == numberOfEntries);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assert (false);
    }

  }


}
