package com.juanroy.secondbrain.auth.controller;

import com.juanroy.secondbrain.auth.dto.request.LoginRequest;
import com.juanroy.secondbrain.auth.dto.request.RefreshTokenRequest;
import com.juanroy.secondbrain.auth.dto.request.RegisterRequest;
import com.juanroy.secondbrain.auth.dto.response.AuthTokenResponse;
import com.juanroy.secondbrain.auth.dto.response.UserAuthView;
import com.juanroy.secondbrain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthTokenResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.refreshToken(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
		authService.logout(request);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/me")
	public ResponseEntity<UserAuthView> me() {
		return ResponseEntity.ok(authService.currentUser());
	}
}
