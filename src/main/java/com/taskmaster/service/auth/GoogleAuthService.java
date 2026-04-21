package com.taskmaster.service.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.taskmaster.dto.auth.request.GoogleLoginRequestDto;
import com.taskmaster.dto.auth.response.AuthResponseDto;
import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import com.taskmaster.entity.enums.RoleEnum;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.security.GoogleIdTokenVerifierService;
import com.taskmaster.security.JwtProperties;
import com.taskmaster.security.JwtService;
import com.taskmaster.service.notification.RegisterNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleIdTokenVerifierService googleVerifier;
    private final PersonRepository personRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RegisterNotificationService registerNotificationService;

    @Transactional
    public AuthResponseDto loginWithGoogle(GoogleLoginRequestDto request) {
        GoogleIdToken.Payload payload = googleVerifier.verify(request.getIdToken());
        String email = payload.getEmail();
        String sub = payload.getSubject();

        PersonEntity person = personRepository.findByGoogleSub(sub)
                .or(() -> personRepository.findByEmail(email))
                .map(existing -> linkGoogleSubIfMissing(existing, sub, payload))
                .orElseGet(() -> provision(payload));

        String token = jwtService.issueToken(person);
        return new AuthResponseDto(token, jwtProperties.getTtlSeconds(), person.getId(), person.getEmail(), person.getRole());
    }

    private PersonEntity linkGoogleSubIfMissing(PersonEntity person, String sub, GoogleIdToken.Payload payload) {
        boolean changed = false;
        if (person.getGoogleSub() == null) {
            person.setGoogleSub(sub);
            changed = true;
        }
        Object pic = payload.get("picture");
        if (pic instanceof String s && !s.equals(person.getPicture())) {
            person.setPicture(s);
            changed = true;
        }
        return changed ? personRepository.save(person) : person;
    }

    private PersonEntity provision(GoogleIdToken.Payload payload) {
        PersonEntity person = new PersonEntity();
        person.setEmail(payload.getEmail());
        person.setGoogleSub(payload.getSubject());
        person.setGivenName(stringClaim(payload, "given_name", "User"));
        person.setFamilyName(stringClaim(payload, "family_name", ""));
        person.setPicture(stringClaim(payload, "picture", null));
        person.setRole(RoleEnum.USER);
        PersonEntity saved = personRepository.save(person);

        RegisterNotificationRequestDto welcome = new RegisterNotificationRequestDto();
        welcome.setPersonId(saved.getId().toString());
        welcome.setNotificationType(NotificationTypeEnum.REGISTRATION);
        welcome.setChannel(NotificationChannelEnum.IN_APP);
        welcome.setMessage("Welcome " + saved.getGivenName() + ", your Taskmaster account is ready.");
        registerNotificationService.registerNotification(saved.getId().toString(), welcome);

        log.info("Provisioned new person {} via Google sub {}", saved.getId(), saved.getGoogleSub());
        return saved;
    }

    private String stringClaim(GoogleIdToken.Payload payload, String key, String fallback) {
        Object v = payload.get(key);
        return v instanceof String s ? s : fallback;
    }
}
