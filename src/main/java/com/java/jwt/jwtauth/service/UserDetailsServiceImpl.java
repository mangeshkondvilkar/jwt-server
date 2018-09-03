package com.java.jwt.jwtauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.jwt.jwtauth.dao.UserRepository;
import com.java.jwt.jwtauth.dto.ApplicationUser;
import com.java.jwt.jwtauth.entity.User;

/**
 * Extend spring's {@link UserDetailsService} interface to provide custom
 * implementation for {@link #loadUserByUsername(String)}. Customize the way to
 * authorize users and their roles
 * 
 * @author Mangesh
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		User user = userRepository
				.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + usernameOrEmail));

		return ApplicationUser.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository
				.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return ApplicationUser.create(user);
	}

}
