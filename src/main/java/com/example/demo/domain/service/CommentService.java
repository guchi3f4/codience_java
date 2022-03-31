package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Comment;

public interface CommentService {
	
	public int insert(Comment comment);
	
	public int delete(int commentId);
	
	public List<Comment> selectByArticleId(int articleId);

}
