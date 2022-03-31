package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;

public interface UserService {
	public boolean insertOne(User user);
	
	public User selectOne(int userId);
	
	public List<User> selectMany();
	
	public boolean updateOne(User user);
	
	public String selectEmail(String email);
	
	public List<Article> selectPostArticles(int userId);
	
	public List<Article> selectBookmarkArticle(int userId);
}
