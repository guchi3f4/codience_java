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
import com.example.demo.domain.repository.mybatis.ArticleMapper;

@SpringJUnitConfig
@SpringBootTest
@Transactional
@DisplayName("ArticleDaoクラスのテスト")
public class ArticleDaoTest {
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Test
	@DisplayName("1件取得した場合のテスト")
	public void Test1() {
		
		//取得レコード
		//1, 1, 'hello', 'https://www.google.com/', 'おはよう',　null, '2022-1-12 15:00:00', '2022-1-12 15:00:00'
		
		Article article = articleMapper.selectOne(1);
		
		assertThat(article.getArticleId(), is(1));
		assertThat(article.getUserId(), is(1));
		assertThat(article.getTitle(), is("hello"));
		assertThat(article.getLink(), is("https://www.google.com/"));
		assertThat(article.getSummary(), is("おはよう"));
		assertThat(article.getBody(), nullValue());
		assertThat(article.getCreated(), is(LocalDateTime.parse("2022-01-12T15:00")));
		assertThat(article.getUpdated(), is(LocalDateTime.parse("2022-01-12T15:00")));
		assertThat(article.getUser().getUserId(), is(1));
		
	}
	
	@Test
	@DisplayName("全件取得した場合のテスト")
	public void Test2() {
		
		List<Article> articleList = articleMapper.selectAll();
		
		assertThat(articleList.size(), is(3));	
	}
	
	@Test
	@DisplayName("1件登録した場合のテスト")
	public void Test3() {
		
		var article = new Article();
		article.setUserId(1);
		article.setTitle("タイトル");
		article.setLink("https://www.google.com");
		article.setSummary("概要");
		article.setBody("本文");
		article.setCreated(LocalDateTime.now());
		article.setUpdated(LocalDateTime.now());
		LocalDateTime createDate = article.getCreated();
		LocalDateTime updateDate = article.getUpdated();
		
		int num = articleMapper.insertOne(article);
		
		//登録したレコードを取得
		Article selectArticle = articleMapper.selectOne(4);
		
		//登録件数の確認
		assertThat(num, is(1));
		//登録したレコードの確認
		assertThat(selectArticle.getArticleId(), is(4));
		assertThat(selectArticle.getUserId(), is(1));
		assertThat(selectArticle.getTitle(), is("タイトル"));
		assertThat(selectArticle.getLink(), is("https://www.google.com"));
		assertThat(selectArticle.getSummary(), is("概要"));
		assertThat(selectArticle.getBody(),is("本文"));
		assertThat(selectArticle.getCreated(), is(createDate));
		assertThat(selectArticle.getUpdated(), is(updateDate));
	}
	
	@Test
	@DisplayName("1件更新した場合のテスト")
	public void Test4() {
		
		//更新レコード
		//1, 1, 'hello', 'https://www.google.com/', 'おはよう', null, '2022-01-12 15:00:00', '2022-01-12 15:00:00'
		
		var article = new Article();
		article.setArticleId(1);
		article.setTitle("タイトル");
		article.setLink("https://change");
		article.setSummary("概要");
		article.setBody("本文");
		article.setUpdated(LocalDateTime.now());
		LocalDateTime updateDate = article.getUpdated();
		
		//1件更新
		int num = articleMapper.updateOne(article);
		
		//更新したレコードを取得
		Article selectArticle = articleMapper.selectOne(1);
		
		//更新件数の確認
		assertThat(num, is(1));
		//更新したレコードの確認
		assertThat(selectArticle.getArticleId(), is(1)); //更新不可
		assertThat(selectArticle.getUserId(), is(1)); //更新不可
		assertThat(selectArticle.getTitle(), is("タイトル"));
		assertThat(selectArticle.getLink(), is("https://change"));
		assertThat(selectArticle.getSummary(), is("概要"));
		assertThat(selectArticle.getBody(),is("本文"));
		assertThat(selectArticle.getCreated(), is(LocalDateTime.parse("2022-01-12T15:00"))); //更新不可
		assertThat(selectArticle.getUpdated(), is(updateDate));
	}
	
	@Test
	@DisplayName("1件削除した場合のテスト")
	public void Test5() {
		
		int num = articleMapper.deleteOne(1);
		
		//削除件数の確認
		assertThat(num, is(1));
		//削除したレコードの確認
		assertThat(articleMapper.selectOne(1), nullValue());
	}
}
