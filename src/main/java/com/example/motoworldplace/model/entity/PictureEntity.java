package com.example.motoworldplace.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity{

    private String url;
    private String publicId;

    //

    private GroupEntity group;

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureEntity setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    //
    @ManyToOne
    public GroupEntity getGroup() {
        return group;
    }

    public PictureEntity setGroup(GroupEntity group) {
        this.group = group;
        return this;
    }
}
