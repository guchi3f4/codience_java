package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Comment;

public interface CommentDao {
	
	public int insert(Comment comment);
	
	public int delete(int commentId);
	
	public List<Comment> selectByArticleId(int articleId);
}
