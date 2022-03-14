package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.repository.ArticleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleDao dao;
	
	@Override
	public Article selectOne(int articleId) {
		return dao.selectOne(articleId);
	}

	@Override
	public List<Article> selectAll() {
		return dao.selectAll();
	}

	@Override
	public boolean insertOne(Article article) {
		int num = dao.insertOne(article);
		if(num > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateOne(Article article) {
		int num = dao.updateOne(article);
		if(num > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteOne(int articleId) {
		int num = dao.deleteOne(articleId);
		if(num > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
