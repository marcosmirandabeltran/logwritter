package com.creditsuisse.interview;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;

import com.creditsuisse.interview.controller.LogProcessorController;
import com.creditsuisse.interview.entity.EventEntity;
import com.creditsuisse.interview.model.ReadFileRequest;
import com.creditsuisse.interview.model.ReadFileResponse;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import com.creditsuisse.interview.service.EventsDBIO;
import com.creditsuisse.interview.service.LogProcessorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.InitBinder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogProcessorControllerTest {


  @Mock
  LogProcessorService logProcessorService;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
  }

  @InjectMocks
  private LogProcessorController logProcessorController = new LogProcessorController();


  @Test
  public void testProcessValidFile() {

    int numberOfEntries = 5;
    try {
      Mockito.when(logProcessorService.readFile(any())).thenReturn(numberOfEntries);
      String filepath = getClass().getClassLoader().getResource("jsonSample.txt").getPath();
      ReadFileRequest req = new ReadFileRequest();
      req.setFilePath(filepath);
      ReadFileResponse res = logProcessorController.readFile(req);

      assert (res.getNumberOfMessagesProcessed() == numberOfEntries);
    } catch (Exception e) {
      assert (false);
    }

  }
}
