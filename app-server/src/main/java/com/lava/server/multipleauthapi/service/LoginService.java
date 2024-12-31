package com.lava.server.multipleauthapi.service;

import com.lava.server.multipleauthapi.repository.UserRepository;
import com.lava.server.multipleauthapi.security.TokenProvider;
import com.lava.server.multipleauthapi.model.entity.User;
import com.lava.server.multipleauthapi.model.error.BadCredentialsException;
import com.lava.server.multipleauthapi.model.error.UserNotFoundException;
import com.lava.server.multipleauthapi.model.payload.LoginRequest;
import com.lava.server.multipleauthapi.model.payload.LoginResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LOG.debug("Login request: {}", loginRequest);
        Authentication authentication;

        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email not registered by administrator yet."));

        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
            userRepository.saveAndFlush(user);
        }

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return new LoginResponse(token);
    }
}
