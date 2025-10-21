package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class TaskNotFoundException extends RequestException{
    public TaskNotFoundException(String taskId){
        super("TaskNotFound", "Tarefa não encontrado: " + taskId);
    }
}
