package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.repository.CommentDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImple implements CommentService {

	private final CommentDao dao;

	@Override
	public int insert(Comment comment) {
		
		return dao.insert(comment);
	}

	@Override
	public int delete(int commentId) {
		
		return dao.delete(commentId);
	}

	@Override
	public List<Comment> selectByArticleId(int articleId) {
		
		return dao.selectByArticleId(articleId);
	}
}
