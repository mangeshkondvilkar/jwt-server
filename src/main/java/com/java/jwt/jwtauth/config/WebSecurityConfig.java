package com.java.jwt.jwtauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.java.jwt.jwtauth.security.JwtAuthenticationEntryPoint;
import com.java.jwt.jwtauth.security.JwtAuthenticationFilter;
import com.java.jwt.jwtauth.security.SecurityConstants;
import com.java.jwt.jwtauth.service.UserDetailsServiceImpl;

/**
 * @author Mangesh
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true,
	jsr250Enabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
//	@Autowired
//	private JwtAuthenticationProvider authenticationProvider;
	
	/**
	 * Authenticate user and return token on successful authentication
	 * @return
	 */
	@Bean
	public JwtAuthenticationFilter authenticationFilter() {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
//		filter.setAuthenticationManager(authenticationManager());
//		filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
		return filter;
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_IN_UP_URL)
					.permitAll()
				.anyRequest()
					.authenticated()
				.and()
					.exceptionHandling()
						.authenticationEntryPoint(authenticationEntryPoint)
				.and()
					.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//					.addFilter(new JwtAuthorizationFilter(authenticationManager()))
				.headers()
					.cacheControl();
	}

}
