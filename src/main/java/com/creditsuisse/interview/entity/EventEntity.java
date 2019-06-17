package com.creditsuisse.interview.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class EventEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    String eventId;
    long duration;
    String type;
    String host;
    boolean alert;


}
