package com.creditsuisse.interview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;



@Getter
@Setter
public class EventModel {

  @JsonProperty("state")
  private EventStatus status;
  @JsonProperty("host")
  private String host;
  @JsonProperty("type")
  private String type;
  @JsonProperty("id")
  private String id;
  @JsonProperty("timestamp")
  private Timestamp timestamp;


}
