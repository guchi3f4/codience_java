package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Article;

public interface ArticleDao {
	
	public Article selectOne(int articleId);
	
	public List<Article> selectAll();
	
	public int insertOne(Article article);
	
	public int updateOne(Article article);
	
	public int deleteOne(int articleId);
}
