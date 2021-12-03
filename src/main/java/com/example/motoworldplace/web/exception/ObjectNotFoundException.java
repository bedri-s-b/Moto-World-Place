package com.example.motoworldplace.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    private  Long objectId;

    private String name;

    public ObjectNotFoundException(Long objectId) {
        super("Object with id " + objectId + " not found!");
        this.objectId = objectId;
    }

    public ObjectNotFoundException(String name) {
        super("Object with name " + name + " not found!");
        this.name = name;
    }


    public Long getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

}
