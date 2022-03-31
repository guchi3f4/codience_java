package com.example.demo.domain.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.mybatis.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean insertOne(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		 int num = userMapper.insertOne(user);
		 if(num > 0) {
			 return true;
		 } else {
			 return false; 
		 }
	}

	@Override
	public User selectOne(int userId) {
		return userMapper.selectOne(userId);
	}

	@Override
	public List<User> selectMany() {
		return userMapper.selectMany();
	}

	@Override
	public boolean updateOne(User user) {
		int num = userMapper.updateOne(user);
		 if(num > 0) {
			 return true;
		 } else {
			 return false; 
		 }
	}

	@Override
	public String selectEmail(String email) {
		return userMapper.selectEmail(email);
	}

	@Override
	public List<Article> selectPostArticles(int userId) {
		
		return userMapper.selectPostArticles(userId);
	}

	@Override
	public List<Article> selectBookmarkArticle(int userId) {
		
		return userMapper.selectBookmarkArticle(userId);
	}
}
