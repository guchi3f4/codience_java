package com.example.demo.domain.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArticleDaoImpl implements ArticleDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public Article selectOne(int articleId) {
		var sql = "SELECT article.id, user_id, title, link, summary, body, article.created, updated,"
				 + " profile_image_id"	 
				 + " FROM article JOIN user ON user_id = user.id"
				 + " WHERE article.id = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, articleId);
		var article = new Article();
		article.setArticleId((int)result.get("id"));
		article.setTitle((String)result.get("Title"));
		article.setLink((String)result.get("link"));
		article.setSummary((String)result.get("summary"));
		article.setBody((String)result.get("body"));
		article.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		article.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
		
		var user = new User();
		user.setUserId((int)result.get("user_id"));
		user.setProfileImageId((byte[])result.get("profile_image_id"));
		
		article.setUser(user);
		
		return article;
	}

	@Override
	public List<Article> selectAll() {
		 var sql = "SELECT article.id, user_id, title, link, summary, body, article.created, updated,"
				 + " profile_image_id"	 
				 + " FROM article JOIN user ON user_id = user.id";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Article> list = new ArrayList<Article>();
		for(var result : resultList) {
			var article = new Article();
			article.setArticleId((int)result.get("id"));
			article.setTitle((String)result.get("Title"));
			article.setLink((String)result.get("link"));
			article.setSummary((String)result.get("summary"));
			article.setBody((String)result.get("body"));
			article.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			article.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
			
			var user = new User();
			user.setUserId((int)result.get("user_id"));
			user.setProfileImageId((byte[])result.get("profile_image_id"));
			
			article.setUser(user);
			list.add(article);
		}
		return list;
	}

	@Override
	public int insertOne(Article article) {
		int num = jdbcTemplate.update(
			"INSERT INTO article(user_id, title, link, summary, body, created, updated)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?)",
			article.getUserId(), article.getTitle(), article.getLink(), article.getSummary(),
			article.getBody(), article.getCreated(), article.getUpdated()
		);
		
		return num;
	}

	@Override
	public int updateOne(Article article) {
		int num = jdbcTemplate.update(
			"UPDATE article SET title = ?, link = ?, summary = ?, body = ?, updated = ?"
			+ " WHERE id = ?",
			article.getTitle(), article.getLink(), article.getSummary(),
			article.getBody(), article.getUpdated(), article.getArticleId()
		);
		
		return num;
	}

	@Override
	public int deleteOne(int articleId) {
		int num = jdbcTemplate.update("DELETE FROM article WHERE id = ?", articleId);
		return num;
	}

}
