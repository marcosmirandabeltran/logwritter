package com.creditsuisse.interview.dao;

import com.creditsuisse.interview.entity.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<EventEntity, Long> {

    public List<EventEntity> findAllByEventId(String eventId);
}
