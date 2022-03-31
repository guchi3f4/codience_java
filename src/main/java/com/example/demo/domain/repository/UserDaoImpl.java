package com.example.demo.domain.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public int insertOne(User user) {
		
		String password = passwordEncoder.encode(user.getPassword());
		
		int num = jdbcTemplate.update(
			"INSERT INTO user(name, email, password, created) VALUES(?, ?, ?, ?)",
			user.getName(), user.getEmail(),password, user.getCreated()
		);
		
		return num;
	}

	@Override
	public User selectOne(int userId) {
		
		var sql = "select a.id, name, email, password, introduction, profile_image_id, created,"
				+ " give_bookmark_count, post_count, take_bookmark_count, take_favorite_count from"
				+ " (select user.id, name, email, password, introduction, profile_image_id, created,"
				+ " Count(bookmark.id) AS give_bookmark_count FROM user"
				+ " LEFT JOIN bookmark ON user.id = bookmark.user_id"
				+ " GROUP BY user.id) a"
				+ " LEFT JOIN"
				+ " (SELECT  user.id, COUNT(article.id) AS post_count,"
				+ " COUNT(bookmark.id) AS take_bookmark_count,"
				+ " COUNT(favorite.id) AS take_favorite_count FROM user"
				+ " LEFT JOIN article ON user.id = article.user_id"
				+ " LEFT JOIN bookmark ON article.id = bookmark.article_id"
				+ " LEFT JOIN favorite ON article.id = favorite.article_id GROUP BY user.id) b"
				+ " ON a.id = b.id"
				+ " WHERE a.id = ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId);
		
		var user = new User();
		user.setUserId((int)result.get("id"));
		user.setName((String)result.get("name"));
		user.setEmail((String)result.get("email"));
		user.setPassword((String)result.get("password"));
		user.setIntroduction((String)result.get("introduction"));
		user.setProfileImageId((String)result.get("profile_image_id"));
		user.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		user.setGiveBookmarkCount((long)result.get("give_bookmark_count"));
		user.setPostCount((long)result.get("post_count"));
		user.setTakeBookmarkCount((long)result.get("take_bookmark_count"));
		user.setTakeFavoriteCount((long)result.get("take_favorite_count"));
		
		return user;
	}

	@Override
	public List<User> selectMany() {
		
		var sql = "select a.id, name, email, password, introduction, profile_image_id, created,"
				+ " give_bookmark_count, post_count, take_bookmark_count, take_favorite_count from"
				+ " (select user.id, name, email, password, introduction, profile_image_id, created,"
				+ " Count(bookmark.id) AS give_bookmark_count FROM user"
				+ " LEFT JOIN bookmark ON user.id = bookmark.user_id"
				+ " GROUP BY user.id) a"
				+ " LEFT JOIN"
				+ " (SELECT  user.id, COUNT(article.id) AS post_count,"
				+ " COUNT(bookmark.id) AS take_bookmark_count,"
				+ " COUNT(favorite.id) AS take_favorite_count FROM user"
				+ " LEFT JOIN article ON user.id = article.user_id"
				+ " LEFT JOIN bookmark ON article.id = bookmark.article_id"
				+ " LEFT JOIN favorite ON article.id = favorite.article_id GROUP BY user.id) b"
				+ " ON a.id = b.id";	
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<User> list = new ArrayList<User>();
		for(var result : resultList) {
			
			var user = new User();
			user.setUserId((int)result.get("id"));
			user.setName((String)result.get("name"));
			user.setEmail((String)result.get("email"));
			user.setPassword((String)result.get("password"));
			user.setIntroduction((String)result.get("introduction"));
			user.setProfileImageId((String)result.get("profile_image_id"));
			user.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			user.setGiveBookmarkCount((long)result.get("give_bookmark_count"));
			user.setPostCount((long)result.get("post_count"));
			user.setTakeBookmarkCount((long)result.get("take_bookmark_count"));
			user.setTakeFavoriteCount((long)result.get("take_favorite_count"));
			
			list.add(user);
		}
		return list;
	}

	@Override
	public int updateOne(User user) {
		int num = jdbcTemplate.update(
			"UPDATE user SET name = ?, introduction = ?, profile_image_id = ? WHERE id = ?",
			user.getName() ,user.getIntroduction(), user.getProfileImageId(), user.getUserId()
		);
		System.out.println("更新:" + num);
		
		return num;
	}

	@Override
	public String selectEmail(String email) {
		
		String sql = "SELECT email FROM user WHERE email = ?";
		
		try {
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
			String resultEmail = (String)result.get("email");
			
			return resultEmail;
			
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Article> selectPostArticles(int userId) {
		
		var sql = "SELECT article.id, user_id, title, link, summary, body, article.created, updated,"
				 + " profile_image_id"	 
				 + " FROM article JOIN user ON user_id = user.id"
				 + " WHERE article.user_id = ?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
		List<Article> list = new ArrayList<Article>();
		
		for(var result : resultList) {
			
			var article = new Article();
			article.setArticleId((int)result.get("id"));
			article.setUserId((int)result.get("user_id"));
			article.setTitle((String)result.get("Title"));
			article.setLink((String)result.get("link"));
			article.setSummary((String)result.get("summary"));
			article.setBody((String)result.get("body"));
			article.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			article.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
			
			var user = new User();
			user.setUserId((int)result.get("user_id"));
			user.setProfileImageId((String)result.get("profile_image_id"));
			
			article.setUser(user);
			
			list.add(article);
		}
		
		return list;
	}

	@Override
	public List<Article> selectBookmarkArticle(int userId) {
		
		var sql = "SELECT * FROM bookmark JOIN article ON article_id = article.id WHERE bookmark.user_id = ?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
		List<Article> list = new ArrayList<Article>();
		
		for(var result : resultList) {
			
			var article = new Article();
			article.setArticleId((int)result.get("id"));
			article.setUserId((int)result.get("user_id"));
			article.setTitle((String)result.get("Title"));
			article.setLink((String)result.get("link"));
			article.setSummary((String)result.get("summary"));
			article.setBody((String)result.get("body"));
			article.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			article.setUpdated(((Timestamp)result.get("updated")).toLocalDateTime());
			
			var user = new User();
			user.setUserId((int)result.get("user_id"));
			user.setProfileImageId((String)result.get("profile_image_id"));
			
			article.setUser(user);
			
			list.add(article);
		}
		
		return list;
	}
}
