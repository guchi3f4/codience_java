package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;

public interface UserDao {
	
	public int insertOne(User user);
	
	public User selectOne(int userId);
	
	public List<User> selectMany();
	
	public int updateOne(User user);
	
	public String selectEmail(String email);
	
	public List<Article> selectPostArticles(int userId);
	
	public List<Article> selectBookmarkArticle(int userId);
}
