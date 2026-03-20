package com.juanroy.secondbrain.auth.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordStrengthValidator {
	private static final Pattern UPPER = Pattern.compile(".*[A-Z].*");
	private static final Pattern LOWER = Pattern.compile(".*[a-z].*");
	private static final Pattern DIGIT = Pattern.compile(".*[0-9].*");
	private static final Pattern SPECIAL = Pattern.compile(".*[^a-zA-Z0-9].*");

	public boolean isStrong(String password) {
		if (password == null || password.length() < 8 || password.length() > 100) {
			return false;
		}
		return UPPER.matcher(password).matches()
				&& LOWER.matcher(password).matches()
				&& DIGIT.matcher(password).matches()
				&& SPECIAL.matcher(password).matches();
	}
}
