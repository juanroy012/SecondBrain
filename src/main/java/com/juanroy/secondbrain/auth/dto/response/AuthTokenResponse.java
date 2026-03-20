package com.juanroy.secondbrain.auth.dto.response;

public record AuthTokenResponse(
		String accessToken,
		String refreshToken,
		String tokenType,
		long expiresInSeconds,
		UserAuthView user
) {
}
