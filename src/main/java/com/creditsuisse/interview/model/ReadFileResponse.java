package com.creditsuisse.interview.model;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Setter;


public class ReadFileResponse {


  private int numberOfMessagesProcessed;

  private String response;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public int getNumberOfMessagesProcessed() {
    return numberOfMessagesProcessed;
  }

  public void setNumberOfMessagesProcessed(int numberOfMessagesProcessed) {
    this.numberOfMessagesProcessed = numberOfMessagesProcessed;
  }

  public ReadFileResponse(String response, int numberOfMessagesProcessed) {
    this.response = response;
    this.numberOfMessagesProcessed = numberOfMessagesProcessed;
  }

}
