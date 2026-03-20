package com.juanroy.secondbrain.auth.service;

import com.juanroy.secondbrain.auth.dto.request.LoginRequest;
import com.juanroy.secondbrain.auth.dto.request.RefreshTokenRequest;
import com.juanroy.secondbrain.auth.dto.request.RegisterRequest;
import com.juanroy.secondbrain.auth.dto.response.AuthTokenResponse;
import com.juanroy.secondbrain.auth.dto.response.UserAuthView;
import com.juanroy.secondbrain.auth.mapper.AuthMapper;
import com.juanroy.secondbrain.auth.model.RefreshToken;
import com.juanroy.secondbrain.auth.model.UserCredential;
import com.juanroy.secondbrain.auth.repository.RefreshTokenRepository;
import com.juanroy.secondbrain.auth.repository.UserCredentialRepository;
import com.juanroy.secondbrain.shared.exception.AppException;
import com.juanroy.secondbrain.shared.exception.ErrorCode;
import com.juanroy.secondbrain.shared.security.CurrentUserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordPolicyService passwordService;
    private final JwtTokenService jwtTokenService;
    private final AuthMapper authMapper;
    private final CurrentUserFacade currentUserFacade;

    @Transactional
    public AuthTokenResponse registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()
                || userRepository.findByUsername(request.username()).isPresent()) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        passwordService.validateOrThrow(request.password());

        UserCredential user = UserCredential.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordService.hashPassword(request.password()))
                .build();

        UserCredential savedUser = userRepository.save(user);
        return issueTokens(savedUser);
    }

    @Transactional
    public AuthTokenResponse login(LoginRequest request) {
        UserCredential user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordService.match(request.password(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        refreshTokenRepository.deleteByUserCredentialId(user.getId());
        return issueTokens(user);
    }

    @Transactional
    public AuthTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_INVALID));

        if (refreshToken.getRevokedAt() != null || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        UserCredential user = refreshToken.getUserCredential();
        String newAccessToken = jwtTokenService.generateToken(user);

        return authMapper.toAuthTokenResponse(
                newAccessToken,
                refreshToken.getToken(),
                jwtTokenService.getAccessTokenExpirationSeconds(),
                user
        );
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {
        if (request.refreshToken() == null || request.refreshToken().isBlank()) {
            return;
        }
        refreshTokenRepository.deleteByToken(request.refreshToken());
    }

    @Transactional(readOnly = true)
    public UserAuthView currentUser() {
        String username = currentUserFacade.requireUsername();
        UserCredential user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return authMapper.toUserView(user);
    }

    private AuthTokenResponse issueTokens(UserCredential user) {
        String accessToken = jwtTokenService.generateToken(user);
        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserCredential(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiresAt(Instant.now().plusSeconds(60L * 60 * 24 * 14));
        refreshTokenRepository.save(refreshToken);

        return authMapper.toAuthTokenResponse(
                accessToken,
                refreshTokenValue,
                jwtTokenService.getAccessTokenExpirationSeconds(),
                user
        );
    }
}
