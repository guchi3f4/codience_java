package com.example.demo.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.repository.mybatis.UserMapper;

import lombok.RequiredArgsConstructor;

@Component("UserDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	
	private final  UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userMapper.selectSessionUser(username);
	}

}
