# FULLY FUNCTIONAL POC

# How to start the application

First of all, application properties should be updated with something as follow:

alert.time=3
spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver
spring.datasource.url=jdbc:hsqldb:file:C:\\Users\\mirandm\\hsqldb\\working/
spring.datasource.username=SA
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update


Line 9 , 10 and 11 should be updated with the HSQLDB details.


Then application can be executed from InterviewApplication.java , this will expose 2 Rest API endpoints documented 
at the bottom of this document

# Technologies
Per practice purposes, the application is using Java 8 with Spring Boot, Spring Data, Lombok, and Vavr ,
its being used some basic Functional Programming 

# Decisions taken

In the HSQLDB it has been choosen as primary key an autoincremental long instead of the eventID, this has been done to allow multiple
events with the same name, so for example:

{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495216}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495217}
{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495218}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495219}

should persist 2 events, as it didn't make sense block one event to appear multiple times, however, in the case that some event would 
have duplicate state, the second one will be ignored, as the timestamp calculation wouldn't make sense, so for example

{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495216}
{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495217}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495218}

would ignore the STARTED event at the 1491377495217



Data Structures:

The events has been stored in a ConcurrentHashMap looking for the fastest performance performance,
as only blocks the entries that are being hit in that moment.


File Read:

It has been readed using Streams through the file trying to look for performance/scalability , however, Java 8 have a bug with 
Files.lines and multithreading that is affecting to the performance (https://bugs.openjdk.java.net/browse/JDK-8072773) Has been fixed in 
Java 9 , but wasn't accepted per requirements. merging the pull request into this project would improve the performance further , TBD

Note: Multithread for file reading wont be really usefull in Hard Disk Drives(Mechanical), as it is normally better in terms of performance read an
Hard Disk Drive sequentially , this case wont apply in SSD disks

HSQL :

Right now, per performance reason we are persisting the file once all of the processing has done instead of inserting the entries one 
by one, this is just to avoid hitting the file DB masively from every thread and having a big impact on the application, this can be improved in several ways:

TBD: Batches on the file Writting, Limited Multithread in the filewritting and Async API response for a big file

The HSQL Schema will be created in runtime if the table doesnt exist, 


# Performance
About 2s in process 400mb file without writting to HSQLDB
About 30 seconds on 7 million entries (400mb)  while writting to HSQLDB only once after the processing (Decision choose)
About 3 minutes while writting multithreadly and storing individualy every event (This would save us 
of storing in a list all of the events but delay too much the response)


# Test

Test is not implemented as some Gradle mistake doesn't let the test classes to find the other classes, 
but it would be used Junit + Mockito , TBD






## Spring boot application exposing this API's on the port 8080:

### POST /readFile ###

Reads a file full of entries in the format below, and persist it on the database
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216}

#### REQUEST:
JSON with this format: 

{
  "filePath": "$PATH"
}

This would be a valid example on a Windows File System

{
  "filePath": "C:/jsonlog2.txt"
}


### GET /events?eventID ###
Retrieves all of the events of a particular eventName

A valid example would be 
127.0.0.1:8080/events?id=scsmbstgrf

It would retrieve the events for that particular eventID if it was persisted previously




