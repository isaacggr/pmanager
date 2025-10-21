package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class InvalidProjectStatusException extends RequestException{
    public InvalidProjectStatusException(String statusStr){
        super("InvalidProjectStatus", "Status do projeto é invalido:  " + statusStr);
    }
}
