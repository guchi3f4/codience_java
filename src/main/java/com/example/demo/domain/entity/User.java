package com.example.demo.domain.entity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class User implements UserDetails {
	
	private int userId;
	private String name;
	private String email;
	private String password;
	private String introduction;
	private byte[] profileImageId;
	private LocalDateTime created;
	private String role = "ROLE_GENERAL";
	
	public String getImgOut() throws IOException{
        return Base64.getEncoder().encodeToString(this.profileImageId);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
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
}
