package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Favorite;
import com.example.demo.domain.repository.FavoriteDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	
	private final FavoriteDao dao;

	@Override
	public int insertOne(Favorite favorite) {
		
		return dao.insertOne(favorite);
	}

	@Override
	public int deleteOne(int favoriteId) {
		
		return dao.deleteOne(favoriteId);
	}

	@Override
	public List<Integer> selectArticleId(int userId) {
		
		return dao.selectArticleId(userId);
	}

	@Override
	public Favorite selectBy(int userId, int articleId) {
		
		return dao.selectBy(userId, articleId);
	}
}
