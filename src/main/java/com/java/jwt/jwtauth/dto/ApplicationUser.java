package com.java.jwt.jwtauth.dto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.jwt.jwtauth.entity.User;

/**
 * A custom wrapper class for {@link User} which extends Spring's
 * {@link UserDetails}. To be used for spring security configuration and logic
 * 
 * @author Mangesh
 *
 */
public class ApplicationUser implements UserDetails {

	private static final long serialVersionUID = -5238446318313354199L;

	private Long id;
	private String name;
	private String username;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String email;

	private Collection<? extends GrantedAuthority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public ApplicationUser(Long id, String name, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/**
	 * static method to create a {@link ApplicationUser} object from the given User
	 * object.
	 * 
	 * @param user
	 * @return
	 */
	public static ApplicationUser create(User user) {
		List<GrantedAuthority> authorities = user
				.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		
		return new ApplicationUser(
	                user.getId(),
	                user.getName(),
	                user.getUsername(),
	                user.getEmail(),
	                user.getPassword(),
	                authorities
	        );
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		ApplicationUser that = (ApplicationUser) obj;
		return Objects.equals(this.id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}
