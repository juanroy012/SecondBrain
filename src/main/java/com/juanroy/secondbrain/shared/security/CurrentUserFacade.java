package com.juanroy.secondbrain.shared.security;

import com.juanroy.secondbrain.auth.model.UserCredential;
import com.juanroy.secondbrain.auth.repository.UserCredentialRepository;
import com.juanroy.secondbrain.shared.exception.AppException;
import com.juanroy.secondbrain.shared.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserFacade {
	private final UserCredentialRepository userRepository;

	public CurrentUserFacade(UserCredentialRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String requireUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
			throw new AppException(ErrorCode.UNAUTHORIZED);
		}
		return authentication.getName();
	}

	public Long requireUserId() {
		String username = requireUsername();
		UserCredential user = userRepository.findByUsername(username)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		return user.getId();
	}
}
