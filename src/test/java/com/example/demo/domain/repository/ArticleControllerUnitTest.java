package com.example.demo.domain.repository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.app.article.form.ArticleForm;
import com.example.demo.app.comment.form.CommentForm;
import com.example.demo.app.user.form.SignupForm;
import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.ArticleService;
import com.example.demo.domain.service.BookmarkService;
import com.example.demo.domain.service.CommentService;
import com.example.demo.domain.service.FavoriteService;
import com.example.demo.domain.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerUnitTest {
	
	@Autowired
	private  MockMvc mockMvc;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookmarkService bookmarkService;
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private  CommentService commentService;
	
	@Test
	@DisplayName("ログイン前：一覧画面のテスト")
	public void test1() throws Exception{
		
		List<Article> articleList = articleService.selectAll();
		
		mockMvc.perform(get("/articles"))
			.andExpect(status().isOk())
			.andExpect(view().name("/headerLayout"))
			.andExpect(model().attribute("articleList", articleList))
			.andExpect(model().attribute("signupForm", new SignupForm()))
			.andExpect(model().attribute("articleForm", new ArticleForm()))
			.andExpect(model().attributeDoesNotExist("user"))
			.andExpect(model().attributeDoesNotExist("myBookmark"))
			.andExpect(model().attributeDoesNotExist("myFavorite"));
	}
	
	@Test
	@DisplayName("ログイン前：詳細画面のテスト")
	public void test2() throws Exception{
		
		int findArticleId = 1;
		
		Article article = articleService.selectOne(findArticleId);
		
		User user = userService.selectOne(article.getUserId());
		
		List<Comment> commentList = commentService.selectByArticleId(findArticleId);
		
		mockMvc.perform(get("/articles/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("/headerLayout"))
			.andExpect(model().attribute("article", article))
			.andExpect(model().attribute("user", user))
			.andExpect(model().attribute("commentList", commentList))
			.andExpect(model().attribute("signupForm", new SignupForm()))
			.andExpect(model().attribute("articleForm", new ArticleForm()))
			.andExpect(model().attribute("commentForm", new CommentForm()))
			.andExpect(model().attributeDoesNotExist("myBookmark"))
			.andExpect(model().attributeDoesNotExist("myFavorite"));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("ログイン後：一覧画面のテスト")
	public void test3() throws Exception{
		
		List<Article> articleList = articleService.selectAll();
		
		int loginUserId = 1;
		
		User user = userService.selectOne(loginUserId);
		
		List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUserId);
		
		List<Integer> myFavorites = favoriteService.selectArticleId(loginUserId);
		
		mockMvc.perform(get("/articles"))
			.andExpect(status().isOk())
			.andExpect(view().name("/headerLayout"))
			.andExpect(model().attribute("articleList", articleList))
			.andExpect(model().attribute("signupForm", new SignupForm()))
			.andExpect(model().attribute("articleForm", new ArticleForm()))
			.andExpect(model().attribute("user", user))
			.andExpect(model().attribute("myBookmarks", myBookmarks))
			.andExpect(model().attribute("myFavorites", myFavorites));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("ログイン後：詳細画面のテスト")
	public void test4() throws Exception{
		
		int findArticleId = 1;
		
		Article article = articleService.selectOne(findArticleId);
		
		User user = userService.selectOne(article.getUserId());
		
		int loginUserId = 1;
		
		List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUserId);
		
		List<Integer> myFavorites = favoriteService.selectArticleId(loginUserId);
		
		List<Comment> commentList = commentService.selectByArticleId(loginUserId);
		
		mockMvc.perform(get("/articles/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("/headerLayout"))
			.andExpect(model().attribute("article", article))
			.andExpect(model().attribute("user", user))
			.andExpect(model().attribute("commentList", commentList))
			.andExpect(model().attribute("signupForm", new SignupForm()))
			.andExpect(model().attribute("articleForm", new ArticleForm()))
			.andExpect(model().attribute("commentForm", new CommentForm()))
			.andExpect(model().attribute("myBookmarks", myBookmarks))
			.andExpect(model().attribute("myFavorites", myFavorites));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("新規投稿画面のテスト")
	public void test5() throws Exception{
		
		mockMvc.perform(get("/articles/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("/headerLayout"))
			.andExpect(model().attribute("articleForm", new ArticleForm()));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("新規投稿失敗のテスト")
	public void test6() throws Exception{
		
		var form = new ArticleForm();
		
		mockMvc.perform((post("/articles/new")).flashAttr("articleForm", form).with(csrf()))
		 	.andExpect(model().hasErrors())
	        .andExpect(model().attribute("articleForm", form))
	        .andExpect(view().name("/headerLayout"))
	        .andExpect(model().attributeExists("contents"));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("新規投稿成功のテスト")
	public void test7() throws Exception{
		
		var form = new ArticleForm();
		form.setTitle("タイトル");
		form.setLink("リンク");
		form.setSummary("概要");
		form.setBody("本文");
		
		mockMvc.perform((post("/articles/new")).flashAttr("articleForm", form).with(csrf()))
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/articles"));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("更新画面のテスト")
	public void test8() throws Exception{
		
		int findArticleId = 1;
		Article article = articleService.selectOne(findArticleId);
		
		var form = new ArticleForm();
		form.setTitle(article.getTitle());
		form.setLink(article.getLink());
		form.setSummary(article.getSummary());
		form.setBody(article.getBody());
		
		mockMvc.perform(get("/articles/1/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("/headerLayout"))
		.andExpect(model().attribute("articleForm", form));
	}@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("更新失敗のテスト")
	public void test9() throws Exception{
		
		var form = new ArticleForm();
		
		mockMvc.perform((post("/articles/edit"))
			.flashAttr("articleForm", form)
			.param("articleId", "1")
			.with(csrf()))
			 	.andExpect(model().hasErrors())
		        .andExpect(model().attribute("articleForm", form))
		        .andExpect(view().name("/headerLayout"))
		        .andExpect(model().attributeExists("contents"));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("更新成功のテスト")
	public void test10() throws Exception{
		
		var form = new ArticleForm();
		form.setTitle("タイトル");
		form.setLink("リンク");
		form.setSummary("概要");
		form.setBody("本文");
		
		mockMvc.perform((post("/articles/edit"))
			.flashAttr("articleForm", form)
			.param("articleId", "1")
			.with(csrf()))
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/articles/1"));
	}
	
	@Test
	@WithUserDetails(value="a@a", userDetailsServiceBeanName="UserDetailsServiceImpl")
	@DisplayName("削除成功のテスト")
	public void test11() throws Exception{
		
		mockMvc.perform((post("/articles/delete"))
			.param("articleId", "1")
			.with(csrf()))
				.andExpect(redirectedUrl("/articles"));
	}

}
