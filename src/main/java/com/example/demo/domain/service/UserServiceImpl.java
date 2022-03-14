package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserDao dao;

	@Override
	public boolean insertOne(User user) {
		 int num = dao.insertOne(user);
		 if(num > 0) {
			 return true;
		 } else {
			 return false; 
		 }
	}

	@Override
	public User selectOne(int userId) {
		return dao.selectOne(userId);
	}

	@Override
	public List<User> selectMany() {
		return dao.selectMany();
	}

	@Override
	public boolean updateOne(User user) {
		int num = dao.updateOne(user);
		 if(num > 0) {
			 return true;
		 } else {
			 return false; 
		 }
	}

}
