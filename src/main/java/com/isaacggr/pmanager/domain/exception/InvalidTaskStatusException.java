package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class InvalidTaskStatusException extends RequestException{
    public InvalidTaskStatusException(String statusStr){
        super("InvalidTaskStatus", "Status da tarefa é invalido:  " + statusStr);
    }
}
