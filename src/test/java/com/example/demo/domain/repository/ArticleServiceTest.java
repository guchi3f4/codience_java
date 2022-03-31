package com.example.demo.domain.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.service.ArticleService;

@SpringJUnitConfig
@Transactional
@SpringBootTest
@DisplayName("ArticleServiceクラスのテスト")
class ArticleServiceTest {
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	@DisplayName("1件取得した場合のテスト")
	void test1() {
		
		Article article = articleService.selectOne(1);
		
		//取得レコードの確認
		assertThat(article.getArticleId(), is(1));
	}
	
	@Test
	@DisplayName("全件取得した場合のテスト")
	void test2() {
		
		List<Article> articleList = articleService.selectAll();
		
		//取得件数の確認
		assertThat(articleList.size(), is(3));
	}
	
	@Test
	@DisplayName("1件登録した場合のテスト")
	void test3() {
		
		var article = new Article();
		article.setUserId(1);
		article.setTitle("タイトル");
		article.setLink("https://www.google.com");
		article.setSummary("概要");
		article.setBody("本文");
		article.setCreated(LocalDateTime.now());
		article.setUpdated(LocalDateTime.now());
		
		int num = articleService.insertOne(article);
		
		//登録件数の確認
		assertThat(num, is(1));
	}
	
	@Test
	@DisplayName("1件更新した場合のテスト")
	void test5() {
		
		var article = new Article();
		article.setArticleId(1);
		article.setTitle("タイトル");
		article.setLink("https://www.google.com");
		article.setSummary("概要");
		article.setBody("本文");
		article.setUpdated(LocalDateTime.now());
		
		int num = articleService.updateOne(article);
		
		//更新件数の確認
		assertThat(num, is(1));
	}
	
	@Test
	@DisplayName("1件削除した場合のテスト")
	void test6() {
		
		int num = articleService.deleteOne(1);
		
		//登録件数の確認
		assertThat(num, is(1));
	}

}
