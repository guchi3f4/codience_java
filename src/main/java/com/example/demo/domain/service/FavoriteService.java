package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Favorite;

public interface FavoriteService {
	
	public int insertOne(Favorite favorite);
	
	public int deleteOne(int favoriteId);
	
	public List<Integer> selectArticleId(int userId);
	
	public Favorite selectBy(int userId, int articleId);
}
