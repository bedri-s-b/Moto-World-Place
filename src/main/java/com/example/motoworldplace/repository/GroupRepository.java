package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.enums.GroupEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    GroupEntity findByName(String name);

    boolean existsByName(String name);

    List<GroupEntity> findByAdmin_Username(String username);


}
