package com.example.demo.domain.entity;

import java.time.LocalDateTime;
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
	private String profileImageId;
	private LocalDateTime created;
	private String role = "ROLE_GENERAL";
	
	//該当ユーザーに関連する集計情報
	private long postCount;
	private long giveBookmarkCount;
	private long takeBookmarkCount;
	private long takeFavoriteCount;

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
