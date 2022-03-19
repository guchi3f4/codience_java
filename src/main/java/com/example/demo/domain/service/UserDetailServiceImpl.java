package com.example.demo.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.repository.LoginDaoImpl;

import lombok.RequiredArgsConstructor;

@Component("UserDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	
	private final LoginDaoImpl dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return dao.selectSessionUser(username);
	}

}
