package com.example.spring_course.service;

import com.example.spring_course.controller.model.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final String issuer;

    public TokenService(JwtEncoder encoder, @Value("${app.jwt.issuer}") String issuer) {
        this.encoder = encoder;
        this.issuer = issuer;
    }

    public TokenResponse issue(Authentication auth) {
        Instant now = Instant.now();
        var claims = JwtClaimsSet.builder().issuer(issuer)
                .subject(auth.getName()).issuedAt(now).expiresAt(now.plusSeconds(900))
                .claim("roles", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()).build();
        var header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new TokenResponse(token, "Bearer", 900L);
    }
}
