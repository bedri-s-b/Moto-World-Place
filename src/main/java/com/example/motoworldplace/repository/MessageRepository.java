package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entiy.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

}
