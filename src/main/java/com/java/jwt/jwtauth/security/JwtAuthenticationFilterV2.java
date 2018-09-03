package com.java.jwt.jwtauth.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.jwt.jwtauth.dto.ApplicationUser;

/**
 * @author Mangesh
 *
 */
public class JwtAuthenticationFilterV2 extends AbstractAuthenticationProcessingFilter {

	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilterV2() {
		super("**/rest/auth/**");
	}

	public JwtAuthenticationFilterV2(AuthenticationManager authenticationManager) {
		super("**/rest/auth/**");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			// custom user authentication
			ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(),
					ApplicationUser.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					applicationUser.getUsername(), applicationUser.getPassword(), new ArrayList<GrantedAuthority>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String jwtToken = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername()).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + jwtToken);
	};

}
