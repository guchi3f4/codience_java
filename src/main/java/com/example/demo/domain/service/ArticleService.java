package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Article;

public interface ArticleService {
	
	public Article selectOne(int articleId);
	
	public List<Article> selectAll();
	
	public int insertOne(Article article);
	
	public int updateOne(Article article);
	
	public int deleteOne(int articleId);
	
	public Article selectLastRecord();

}
