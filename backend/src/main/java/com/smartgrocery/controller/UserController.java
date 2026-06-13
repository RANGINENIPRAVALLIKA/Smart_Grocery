package com.smartgrocery.controller;

import com.smartgrocery.dto.UserDto;
import com.smartgrocery.security.UserPrincipal;
import com.smartgrocery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userService.getProfile(user.getUserId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@AuthenticationPrincipal UserPrincipal user,
                                                @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateProfile(user.getUserId(), dto));
    }
}
