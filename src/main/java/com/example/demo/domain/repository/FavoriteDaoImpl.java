package com.example.demo.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Favorite;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FavoriteDaoImpl implements FavoriteDao {
	
	private final JdbcTemplate jdbcTemplate;

	@Override
	public int insertOne(Favorite favorite) {
		
		int num = jdbcTemplate.update(
			"INSERT INTO favorite(user_id, article_id) VALUES(?, ?)",
			favorite.getUserId(), favorite.getArticleId()
		);
		
		return num;
	}

	@Override
	public int deleteOne(int favoriteId) {
		
		int num = jdbcTemplate.update("DELETE FROM favorite WHERE id = ?", favoriteId);
		
		return num;
	}

	@Override
	public List<Integer> selectArticleId(int userId) {
		
		var sql = "SELECT article_id FROM favorite WHERE user_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
		List<Integer> list = new ArrayList<Integer>();
		
		for(var result : resultList) {
			
			var articleId = (Integer)result.get("article_id");
			list.add(articleId);
		}
		
		return list;
	}

	@Override
	public Favorite selectBy(int userId, int articleId) {
		
		var sql = "SELECT id, user_id, article_id FROM favorite WHERE user_id = ? AND article_id = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId, articleId);
		
		var favorite = new Favorite();
		favorite.setFavoriteId((int)result.get("id"));
		favorite.setUserId((int)result.get("user_id"));
		favorite.setArticleId((int)result.get("article_id"));
		
		return favorite;
	}
}
