package com.creditsuisse.interview.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventProcessingException extends Exception {
    String error, line;
    public EventProcessingException(String error, String line){
        super(error);
        this.error = error;
        this.line = line;
    }
}
