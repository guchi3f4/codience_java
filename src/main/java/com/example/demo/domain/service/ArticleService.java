package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Article;

public interface ArticleService {
	
	public Article selectOne(int articleId);
	
	public List<Article> selectAll();
	
	public boolean insertOne(Article article);
	
	public boolean updateOne(Article article);
	
	public boolean deleteOne(int articleId);

}
