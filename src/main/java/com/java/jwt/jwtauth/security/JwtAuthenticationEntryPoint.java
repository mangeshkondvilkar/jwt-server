/**
 * 
 */
package com.java.jwt.jwtauth.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * An entry point class which will be called for any exceptions. It is used to
 * return a {@code 401 Unauthorized} error code to clients that try to access a
 * protected resource without a valid authentication.
 * 
 * {@link #commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
 * method is called whenever an exception is thrown due to an unauthenticated
 * user trying to access a resource that requires authentication.
 * 
 * 
 * @author Mangesh
 * @date 2 Sep 2018
 * @company self
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		LOGGER.error("Responding with Unauthorized access error - {} ", authException.getMessage());
		response
				.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Sorry, You are not authorized to access this reosource");
	}

}
