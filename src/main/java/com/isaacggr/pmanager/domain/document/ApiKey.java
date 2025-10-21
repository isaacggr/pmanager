package com.isaacggr.pmanager.domain.document;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "api_key")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {

    @Id
    private String id;

    private String value;

    private Instant expiresWhen;

    @CreatedDate
    private Instant createdWhen;

    public boolean isExpired(Instant now) {
        return now.isAfter(expiresWhen);
    };
}
