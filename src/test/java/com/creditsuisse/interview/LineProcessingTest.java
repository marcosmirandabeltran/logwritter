package com.creditsuisse.interview;

import com.creditsuisse.interview.model.EventModel;
import com.creditsuisse.interview.model.EventStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class LineProcessingTest {

  @Autowired
  private ObjectMapper json;

@Test
public void testProcessEntry() throws Exception{

  EventModel model = json.readValue("{\"id\":\"scsmbstgrf\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}", EventModel.class);

  assert(model.getStatus() == EventStatus.STARTED);
  assert(model.getId().equals("scsmbstgrf"));
}

}
