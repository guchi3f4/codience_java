package com.example.demo.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.entity.Article;

@Mapper
public interface ArticleMapper {
	
	public Article selectOne(int articleId);
	
	public List<Article> selectAll();
	
	public int insertOne(Article article);
	
	public int updateOne(Article article);
	
	public int deleteOne(int articleId);
	
	public Article selectLastRecord();
	
}
