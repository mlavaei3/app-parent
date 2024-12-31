package com.lava.server.multipleauthapi.security;

import com.lava.server.multipleauthapi.repository.UserRepository;
import com.lava.server.multipleauthapi.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with email: %s.", email)));

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with ID: %s.", id)));

        return UserPrincipal.create(user);
    }

}
