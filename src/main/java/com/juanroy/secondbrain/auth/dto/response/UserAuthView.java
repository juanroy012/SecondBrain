package com.juanroy.secondbrain.auth.dto.response;

import java.time.Instant;

public record UserAuthView(
		Long id,
		String username,
		String email,
		Instant createdAt
) {
}
