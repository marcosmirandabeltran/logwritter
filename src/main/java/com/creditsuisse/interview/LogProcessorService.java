package com.creditsuisse.interview;

import ch.qos.logback.core.encoder.EchoEncoder;
import java.nio.file.Path;

public interface LogProcessorService  {
    int readFile(String path) throws Exception;
}
