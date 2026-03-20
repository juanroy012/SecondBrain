package com.juanroy.secondbrain.auth.service;

import com.juanroy.secondbrain.auth.validator.PasswordStrengthValidator;
import com.juanroy.secondbrain.shared.exception.AppException;
import com.juanroy.secondbrain.shared.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyService {
    private final PasswordEncoder encoder;
    private final PasswordStrengthValidator validator;

    public PasswordPolicyService(PasswordEncoder encoder, PasswordStrengthValidator validator) {
        this.encoder = encoder;
        this.validator = validator;
    }

    public void validateOrThrow(String rawPassword) {
        if (!validator.isStrong(rawPassword)) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }
    }

    public String hashPassword(String raw) {
        return encoder.encode(raw);
    }

    public boolean match(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }
}
