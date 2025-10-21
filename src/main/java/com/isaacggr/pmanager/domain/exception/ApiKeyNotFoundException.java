package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class ApiKeyNotFoundException extends RequestException{
    public ApiKeyNotFoundException(String apiKeyId){
        super("ApiKeyNotFound", "ApiKey não encontrada: " + apiKeyId);
    }
}
