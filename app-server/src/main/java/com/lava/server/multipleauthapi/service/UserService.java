package com.lava.server.multipleauthapi.service;

import com.lava.server.multipleauthapi.repository.UserRepository;
import com.lava.server.multipleauthapi.model.entity.User;
import com.lava.server.multipleauthapi.model.payload.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserInfoById(Long id) {
        LOG.debug("Getting user info by id: {}", id);

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: %s.".formatted(id)));

        return UserMapper.mapToUserResponse(user);
    }

}
