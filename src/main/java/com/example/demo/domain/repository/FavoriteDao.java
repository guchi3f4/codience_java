package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Favorite;

public interface FavoriteDao {
	
	public int insertOne(Favorite favorite);
	
	public int deleteOne(int favoriteId);
	
	public List<Integer> selectArticleId(int userId);
	
	public Favorite selectBy(int userId, int articleId);
}
