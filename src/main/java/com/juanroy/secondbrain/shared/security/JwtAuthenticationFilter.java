package com.juanroy.secondbrain.shared.security;

import com.juanroy.secondbrain.auth.repository.UserCredentialRepository;
import com.juanroy.secondbrain.auth.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter {
	private final JwtTokenService jwtTokenService;
	private final UserCredentialRepository userRepository;

	public JwtAuthenticationFilter(JwtTokenService jwtTokenService, UserCredentialRepository userRepository) {
		this.jwtTokenService = jwtTokenService;
		this.userRepository = userRepository;
	}

	public OncePerRequestFilter asFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				String authHeader = request.getHeader("Authorization");
				if (authHeader == null || !authHeader.startsWith("Bearer ")) {
					filterChain.doFilter(request, response);
					return;
				}

				String token = authHeader.substring(7);
				String username;
				try {
					username = jwtTokenService.extractUsername(token);
				} catch (Exception ex) {
					filterChain.doFilter(request, response);
					return;
				}

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					userRepository.findByUsername(username).ifPresent(user -> {
						if (jwtTokenService.isTokenValid(token, user.getUsername())) {
							UsernamePasswordAuthenticationToken authenticationToken =
									new UsernamePasswordAuthenticationToken(
											user.getUsername(),
											null,
											List.of(new SimpleGrantedAuthority("ROLE_USER"))
									);
							authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(authenticationToken);
						}
					});
				}

				filterChain.doFilter(request, response);
			}
		};
	}
}
