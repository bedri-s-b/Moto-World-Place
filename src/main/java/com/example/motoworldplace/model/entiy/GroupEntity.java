package com.example.motoworldplace.model.entiy;

import com.example.motoworldplace.model.entiy.enums.GroupEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "moto_groups")
public class GroupEntity extends BaseEntity {

    private GroupEnum name;
    private Set<UserEntity> members;

    public GroupEntity() {
    }


    @ManyToMany(mappedBy = "group",fetch = FetchType.EAGER)
    public Set<UserEntity> getMembers() {
        return members;
    }

    public GroupEntity setMembers(Set<UserEntity> members) {
        this.members = members;
        return this;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public GroupEnum getName() {
        return name;
    }

    public GroupEntity setName(GroupEnum name) {
        this.name = name;
        return this;
    }
}
