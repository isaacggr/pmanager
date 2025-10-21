package com.isaacggr.pmanager.domain.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.isaacggr.pmanager.domain.document.ApiKey;

@Repository
public interface ApiKeyRepository extends MongoRepository<ApiKey, String>{
    
    Optional<ApiKey> findByValue(String value);
}
