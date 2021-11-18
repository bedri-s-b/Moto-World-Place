package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

    List<MessageEntity> findByToUser_Username(String username);

    List<MessageEntity> findByFromUser_Username(String username);
}
