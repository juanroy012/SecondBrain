package com.juanroy.secondbrain.auth.mapper;

import com.juanroy.secondbrain.auth.dto.response.AuthTokenResponse;
import com.juanroy.secondbrain.auth.dto.response.UserAuthView;
import com.juanroy.secondbrain.auth.model.UserCredential;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
	public UserAuthView toUserView(UserCredential user) {
		return new UserAuthView(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
	}

	public AuthTokenResponse toAuthTokenResponse(
			String accessToken,
			String refreshToken,
			long expiresInSeconds,
			UserCredential user
	) {
		return new AuthTokenResponse(accessToken, refreshToken, "Bearer", expiresInSeconds, toUserView(user));
	}
}
