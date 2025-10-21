package com.isaacggr.pmanager.infrastructure.security;

import java.util.Objects;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.isaacggr.pmanager.domain.applicationservice.ApiKeyService;
import com.isaacggr.pmanager.domain.exception.ApiKeyExpiredException;
import com.isaacggr.pmanager.domain.exception.ApiKeyNotFoundException;
import com.isaacggr.pmanager.infrastructure.config.AppConfigProperties;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApiKeyService apiKeyService;
    private final AppConfigProperties props;

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";

    public Authentication getAuthentication(HttpServletRequest request) {
        String apikey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if (!Objects.equals(apikey, props.getSecurity().getMasterApiKey())) {
            try {
                apiKeyService.validateApiKey(apikey);
            } catch (ApiKeyNotFoundException | ApiKeyExpiredException e) {
                throw new BadCredentialsException("Api key não é valida: " + apikey, e);
            }

        }

        return new ApiKeyAuthenticationToken(apikey);
    }
}
