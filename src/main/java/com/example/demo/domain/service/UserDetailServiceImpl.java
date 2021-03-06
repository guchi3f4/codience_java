package com.example.demo.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.mybatis.UserMapper;

import lombok.RequiredArgsConstructor;

@Component("UserDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	
	private final  UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userMapper.selectSessionUser(username);
		
		if(user == null) {
			return new User(); 
		} else {
			return user;
		}
	}

}
