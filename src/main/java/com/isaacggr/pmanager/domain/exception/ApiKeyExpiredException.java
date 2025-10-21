package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class ApiKeyExpiredException extends RequestException{
    public ApiKeyExpiredException(String apiKeyId){
        super("ApiKeyExpired", "ApiKey expirada: " + apiKeyId);
    }
}
