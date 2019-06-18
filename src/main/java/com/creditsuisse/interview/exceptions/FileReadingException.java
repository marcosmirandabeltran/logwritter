package com.creditsuisse.interview.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileReadingException extends RuntimeException {

  String error;

  public FileReadingException(String error) {
    super(error);
    this.error = error;
  }
}
