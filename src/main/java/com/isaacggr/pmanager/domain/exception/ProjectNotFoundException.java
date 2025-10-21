package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class ProjectNotFoundException extends RequestException{
    public ProjectNotFoundException(String projectId){
        super("ProjectNotFound", "Projeto não encontrado: " + projectId);
    }
}
