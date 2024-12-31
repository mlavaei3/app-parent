package com.lava.server.multipleauthapi.controller;

import com.lava.server.multipleauthapi.model.payload.UserResponse;
import com.lava.server.multipleauthapi.security.CurrentUser;
import com.lava.server.multipleauthapi.security.UserPrincipal;
import com.lava.server.multipleauthapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.getUserInfoById(userPrincipal.getId()));
    }

}