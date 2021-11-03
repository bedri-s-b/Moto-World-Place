package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entiy.GroupEntity;
import com.example.motoworldplace.model.entiy.enums.GroupEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    GroupEntity findByName(GroupEnum name);
}
