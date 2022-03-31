package com.example.demo.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.repository.mybatis.ArticleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleMapper dao;
	
	@Override
	public Article selectOne(int articleId) {
		return dao.selectOne(articleId);
	}

	@Override
	public List<Article> selectAll() {
		return dao.selectAll();
	}

	@Override
	public int insertOne(Article article) {
		
		int num = dao.insertOne(article);
		
		return num;
	}

	@Override
	public int updateOne(Article article) {
		int num = dao.updateOne(article);
		
		return num;
		
	}

	@Override
	public int deleteOne(int articleId) {
		int num = dao.deleteOne(articleId);
		
		return num;
	}

	@Override
	public Article selectLastRecord() {
		
		return dao.selectLastRecord();
	}
	
}
