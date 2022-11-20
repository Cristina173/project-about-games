package com.todolistrest.exceptions;

import java.util.List;

public class ExceptionMessage {

    private List<String> message;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
