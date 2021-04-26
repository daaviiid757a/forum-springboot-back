package com.deividsandoval.forum.service;

import com.deividsandoval.forum.dto.AuthenticationResponse;
import com.deividsandoval.forum.dto.RefreshTokenRequest;
import com.deividsandoval.forum.requests.LoginRequest;
import com.deividsandoval.forum.requests.RegisterRequest;
import com.deividsandoval.forum.exceptions.ForumException;
import com.deividsandoval.forum.models.NotificationEmail;
import com.deividsandoval.forum.models.User;
import com.deividsandoval.forum.models.VerificationToken;
import com.deividsandoval.forum.repository.UserRepository;
import com.deividsandoval.forum.repository.VerificationTokenRepository;
import com.deividsandoval.forum.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RefreshTokenService refreshTokenService;

    private static String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";

    @Transactional
    public void signup(RegisterRequest req) {
        User user = new User();

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);

        mailService.send(new NotificationEmail("Please activate your account", user.getEmail(), "Thank you for signing up to Spring Reddit, please click on the below url to activate your account: " + ACTIVATION_EMAIL + "/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        verificationToken.orElseThrow(() -> new ForumException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ForumException("User not found"));
        user.setEnabled(true);

        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest req) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtProvider.generateToken(authenticate);

        return AuthenticationResponse
                .builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(req.getUsername())
                .build();
    }

    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest req) {
        refreshTokenService.validateRefreshToken(req.getRefreshToken());

        String token = jwtProvider.generateTokenWithUserName(req.getUsername());

        return AuthenticationResponse
                .builder()
                .authenticationToken(token)
                .refreshToken(req.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(req.getUsername())
                .build();
    }
}
