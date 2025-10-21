package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class DuplicateProjectException extends RequestException{
    public DuplicateProjectException(String name){
        super("ProjectDuplicate", "Já existe um projeto com este nome: " + name);
    }
}
