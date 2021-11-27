package com.example.motoworldplace.model.binding;

import javax.validation.constraints.Size;

public class UserMessageBindingModel {

    private String message;

    @Size(min = 5)
    public String getMessage() {
        return message;
    }

    public UserMessageBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
