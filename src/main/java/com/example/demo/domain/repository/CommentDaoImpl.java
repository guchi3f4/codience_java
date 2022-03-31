package com.example.demo.domain.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public int insert(Comment comment) {
		
		int num = jdbcTemplate.update(
			"INSERT INTO comment(user_id, article_id, comment, created) VALUES(?, ?, ?, ?)",
			comment.getUserId(), comment.getArticleId(), comment.getComment(), comment.getCreated()
		);
		
		return num;
	}

	@Override
	public int delete(int commentId) {
		
		int num = jdbcTemplate.update("DELETE FROM comment WHERE id = ?", commentId);
		
		return num;
	}

	@Override
	public List<Comment> selectByArticleId(int articleId) {
		
		var sql = "SELECT comment.id, user_id, article_id, comment, comment.created, user.id, name, profile_image_id"
				+ " FROM comment JOIN user ON user_id = user.id WHERE article_id = ?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, articleId);
		List<Comment> list = new ArrayList<Comment>();
		
		for(var result : resultList) {
			
			var comment = new Comment();
			comment.setCommentId((int)result.get("id"));
			comment.setUserId((int)result.get("user_id"));
			comment.setArticleId((int)result.get("article_id"));
			comment.setComment((String)result.get("comment"));
			comment.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			
			var user = new User();
			user.setUserId((int)result.get("id"));
			user.setName((String)result.get("name"));
			user.setProfileImageId((String)result.get("profile_image_id"));
			
			comment.setUser(user);
			
			list.add(comment);
		}
		
		return list;
	}
	
	
}
