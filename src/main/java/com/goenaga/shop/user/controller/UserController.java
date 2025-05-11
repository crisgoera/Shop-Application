package com.goenaga.shop.user.controller;

import com.goenaga.shop.user.model.UserDTO;
import com.goenaga.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity getUserProfile(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(userService.getUserFromToken(authHeader.substring(7)));
    }

    @PatchMapping("/profile/edit")
    public ResponseEntity updateUserProfile(@RequestHeader("Authorization") String authHeader,
    @RequestBody UserDTO updateDetails) {
        return ResponseEntity.ok(userService.updateUserProfile(authHeader.substring(7), updateDetails));
    }
}
