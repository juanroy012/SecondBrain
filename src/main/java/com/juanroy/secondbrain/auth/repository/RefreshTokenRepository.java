package com.juanroy.secondbrain.auth.repository;

import com.juanroy.secondbrain.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	void deleteByToken(String token);

	void deleteByUserCredentialId(Long userId);
}
