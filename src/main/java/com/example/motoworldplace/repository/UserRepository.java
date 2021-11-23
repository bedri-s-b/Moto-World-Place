package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.picture = :byPublicId WHERE u.id = :userId")
    void changePicture(@Param("userId") Long userId,
                       @Param("byPublicId") PictureEntity byPublicId);




}
