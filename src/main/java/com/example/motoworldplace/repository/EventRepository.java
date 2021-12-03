package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.EventEntity;
import com.example.motoworldplace.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    @Query("SELECT e FROM EventEntity e LEFT JOIN FETCH e.membersCome WHERE e.id = :eId")
    Optional<EventEntity> findByIdCurrent(@Param("eId") Long eId);


    @Query(value = "SELECT e FROM EventEntity e WHERE e.group.name = :groupName ORDER BY e.stared")
    List<EventEntity> findEventsByGroupNameOrderByStared(@Param("groupName") String groupName);
}
