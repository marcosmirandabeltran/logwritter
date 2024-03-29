package com.creditsuisse.interview.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventProcessingException extends RuntimeException {
    String error, line;
    public EventProcessingException(String error, String line){
        super(error);
        this.error = error;
        this.line = line;
    }
}
