package com.example.demo.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Bookmark;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookmarkDaoImpl implements BookmarkDao {
	
	private final JdbcTemplate jdbcTemplate;

	@Override
	public int insertOne(Bookmark bookmark) {
		int num = jdbcTemplate.update(
			"INSERT INTO bookmark(user_id, article_id) VALUES(?, ?)",
			bookmark.getUserId(), bookmark.getArticleId()
		);
		
		return num;
	}

	@Override
	public int deleteOne(int bookmarkId) {
		int num = jdbcTemplate.update("DELETE FROM bookmark WHERE id = ?", bookmarkId);
		
		return num;
	}

	@Override
	public List<Integer> selectArticleId(int userId) {
		
		var sql = "SELECT article_id FROM bookmark WHERE user_id = ?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
		List<Integer> list = new ArrayList<Integer>();
		for(var result : resultList) {
			
			var articleId  = (Integer)result.get("article_id");
			
			list.add(articleId);
		}
		
		return list;
	}

	@Override
	public Bookmark selectBy(int userId, int articleId) {
		
		var sql = "SELECT id, user_id, article_id FROM bookmark WHERE user_id = ? AND article_id = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId, articleId);
		var bookmark = new Bookmark();
		bookmark.setBookmarkId((int)result.get("id"));
		bookmark.setUserId((int)result.get("user_id"));
		bookmark.setArticleId((int)result.get("article_id"));
		
		return bookmark;
	}
}
