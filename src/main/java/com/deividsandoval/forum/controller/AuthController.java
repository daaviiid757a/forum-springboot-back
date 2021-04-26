package com.deividsandoval.forum.controller;

import com.deividsandoval.forum.dto.AuthenticationResponse;
import com.deividsandoval.forum.dto.RefreshTokenRequest;
import com.deividsandoval.forum.requests.LoginRequest;
import com.deividsandoval.forum.requests.RegisterRequest;
import com.deividsandoval.forum.repository.UserRepository;
import com.deividsandoval.forum.service.AuthService;
import com.deividsandoval.forum.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity signup (@RequestBody RegisterRequest req) {
        authService.signup(req);

        return new ResponseEntity("User registration succesful", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
        authService.verifyAccount(token);

        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("refresh/{token}")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest req) {
        return authService.refreshToken(req);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest req) {
        refreshTokenService.deleteRefreshToken(req.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully!");
    }
}
