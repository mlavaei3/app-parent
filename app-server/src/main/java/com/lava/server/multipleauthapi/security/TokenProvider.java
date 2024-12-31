package com.lava.server.multipleauthapi.security;

import com.lava.server.multipleauthapi.config.AppProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {
    private static final Logger LOG = LoggerFactory.getLogger(TokenProvider.class);

    private final AppProperties appProperties;
    private Algorithm ALGORITHM;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        ALGORITHM = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return JWT.create()
                .withSubject(Long.toString(userPrincipal.getId()))
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(ALGORITHM);
    }

    public Long getUserIdFromToken(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String subject = decodedJWT.getSubject();
        return Long.parseLong(subject);
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            LOG.error("Invalid or expired JWT.");
        }
        return false;
    }

}
