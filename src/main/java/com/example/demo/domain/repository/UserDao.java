package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.User;

public interface UserDao {
	
	public int insertOne(User user);
	
	public User selectOne(int userId);
	
	public List<User> selectMany();
	
	public int updateOne(User user);
}
