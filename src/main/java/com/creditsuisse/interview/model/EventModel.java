package com.creditsuisse.interview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

enum EVENT_STATUS {
  STARTED, FINISHED
}

@Getter
@Setter
public class EventModel {

  @JsonProperty("state")
  private EVENT_STATUS status;
  @JsonProperty("host")
  private String host;
  @JsonProperty("type")
  private String type;
  @JsonProperty("id")
  private String id;
  @JsonProperty("timestamp")
  private Timestamp timestamp;


}
