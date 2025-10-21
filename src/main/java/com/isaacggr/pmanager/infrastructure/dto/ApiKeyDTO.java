package com.isaacggr.pmanager.infrastructure.dto;

import java.time.Instant;
import com.isaacggr.pmanager.domain.document.ApiKey;
import lombok.Data;

@Data
public class ApiKeyDTO {

    private final String id;

    private final String value;

    private final Instant expiresWhen;

    public static ApiKeyDTO create(ApiKey apiKey) {
        return new ApiKeyDTO(
        apiKey.getId(), 
        apiKey.getValue(), 
        apiKey.getExpiresWhen());
    }
}
