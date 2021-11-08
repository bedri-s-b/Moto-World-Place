package com.example.motoworldplace.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="group_pictures")
public class CroupPictureEntity extends BaseEntity{

    private GroupEntity pictures;

    @ManyToOne
    public GroupEntity getPictures() {
        return pictures;
    }

    public CroupPictureEntity setPictures(GroupEntity pictures) {
        this.pictures = pictures;
        return this;
    }
}
