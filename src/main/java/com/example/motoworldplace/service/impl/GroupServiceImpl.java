package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entiy.GroupEntity;
import com.example.motoworldplace.model.entiy.enums.GroupEnum;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.service.GroupService;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void initGroup() {
        if (groupRepository.count() != 0){
            return;
        }
        GroupEntity group = new GroupEntity().setName(GroupEnum.FREE_RAIDER);
        groupRepository.save(group);
    }
}
