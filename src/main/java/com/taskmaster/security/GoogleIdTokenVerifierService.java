package com.taskmaster.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.taskmaster.exception.AuthenticationFailureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleIdTokenVerifierService {

    private final GoogleProperties googleProperties;
    private GoogleIdTokenVerifier verifier;

    @PostConstruct
    void init() {
        if (googleProperties.getClientId() == null || googleProperties.getClientId().isBlank()) {
            log.warn("security.google.client-id is not configured — Google login will fail until it is set");
        }
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleProperties.getClientId()))
                .build();
    }

    public GoogleIdToken.Payload verify(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new AuthenticationFailureException("Invalid Google ID token");
            }
            return idToken.getPayload();
        } catch (GeneralSecurityException | java.io.IOException ex) {
            throw new AuthenticationFailureException("Could not verify Google ID token: " + ex.getMessage());
        }
    }
}
