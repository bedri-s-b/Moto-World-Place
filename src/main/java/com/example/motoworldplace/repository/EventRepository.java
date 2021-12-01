package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    @Query("SELECT e FROM EventEntity e LEFT JOIN FETCH e.membersCome WHERE e.id = :eId")
    EventEntity findByIdCurrent(@Param("eId") Long eId);
}
