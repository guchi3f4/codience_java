package com.example.demo.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Category;
import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.repository.mybatis.ArticleMapper;
import com.example.demo.domain.service.SharedService.ArticleTagSharedServiceImpl;
import com.example.demo.domain.service.SharedService.CategorySharedServiceImpl;
import com.example.demo.domain.service.SharedService.TagSharedServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleMapper dao;
	
	private final CategorySharedServiceImpl categorySharedService;
	
	private final TagSharedServiceImpl tagSharedService;
	
	private final ArticleTagSharedServiceImpl articleTagSharedService;
	
	@Override
	//記事新規投稿の一連の処理
	public void insertArticle(Article article, List<String> tagNameList) {
		 
		//カテゴリの登録処理
		Optional<Category> categoryOpt = categorySharedService.insertCategory(article);
		
		//タグの登録処理
		List<Tag> tagList = tagSharedService.inserTag(article, tagNameList);
		
		//記事の登録処理
		categoryOpt.ifPresent(category -> article.setCategoryId(category.getCategoryId()));
		insertOne(article);
		
		//記事_タグ 中間クラスでのマッピング処理
		Article lastArticle = selectLastRecord();
		articleTagSharedService.insertArticleTag(tagList, lastArticle);
	}
	
	@Override
	//記事更新の一連の処理
	public void updateArticle(Article article, List<String> tagNameList) {
		 
		//カテゴリの登録処理
		Optional<Category> categoryOpt = categorySharedService.insertCategory(article);
		
		//タグの登録処理
		List<Tag> tagList = tagSharedService.inserTag(article, tagNameList);
		
		//記事の登録処理
		categoryOpt.ifPresent(category -> article.setCategoryId(category.getCategoryId()));
		updateOne(article);
		
		//記事_タグ 中間クラスでのマッピング処理
		articleTagSharedService.updateArticleTag(article, tagList);
	}
	
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
		
		return dao.insertOne(article);
	}

	@Override
	public int updateOne(Article article) {
		
		return dao.updateOne(article);
	}

	@Override
	public int deleteOne(int articleId) {
		
		return dao.deleteOne(articleId);
	}

	@Override
	public Article selectLastRecord() {
		
		return dao.selectLastRecord();
	}
}
