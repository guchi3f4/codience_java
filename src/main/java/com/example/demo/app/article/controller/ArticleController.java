package com.example.demo.app.article.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.app.article.form.ArticleForm;
import com.example.demo.app.comment.form.CommentForm;
import com.example.demo.app.user.form.SignupForm;
import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.ArticleTag;
import com.example.demo.domain.entity.Category;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.ArticleService;
import com.example.demo.domain.service.ArticleTagService;
import com.example.demo.domain.service.BookmarkService;
import com.example.demo.domain.service.CategoryService;
import com.example.demo.domain.service.CommentService;
import com.example.demo.domain.service.FavoriteService;
import com.example.demo.domain.service.TagService;
import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
	
	private final ArticleService articleService;
	
	private final UserService userService;
	
	private final BookmarkService bookmarkService;
	
	private final FavoriteService favoriteService;
	
	private final CommentService commentService;
	
	private final CategoryService categoryService;
	
	private final TagService tagService;
	
	private final ArticleTagService articleTagService;
	
	@GetMapping
	public String index(
		Model model,
		ArticleForm articleForm,
		SignupForm signupForm,
		@AuthenticationPrincipal User loginUser)
	{
		model.addAttribute("contents", "article/index :: articleIndex_contents");
		
		List<Article> articleList = articleService.selectAll();
		model.addAttribute("articleList", articleList);
		
		if(loginUser != null) {
			
			User user = userService.selectOne(loginUser.getUserId());
			model.addAttribute("user", user);
			
			List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myBookmarks", myBookmarks);
			
			System.out.println(myBookmarks);
			
			List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myFavorites", myFavorites);
			
			System.out.println(myFavorites);
		}
		
		return "/headerLayout";
	}
	
	@GetMapping("/{id}")
	public String detail(
		Model model,
		@PathVariable("id") int articleId,
		ArticleForm articleForm,
		SignupForm signupForm,
		CommentForm commentForm,
		@AuthenticationPrincipal User loginUser)
	{	
		model.addAttribute("contents", "article/detail :: articleDetail_contents");
		
		Article article = articleService.selectOne(articleId);
		model.addAttribute("article", article);
		
		User user = userService.selectOne(article.getUserId());
		model.addAttribute("user", user);
		
		if(loginUser != null) {
			
			List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myBookmarks", myBookmarks);
			
			List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myFavorites", myFavorites);
		}
		
		List<Comment> commentList = commentService.selectByArticleId(articleId);
		model.addAttribute("commentList", commentList);
		
		return "/headerLayout";
	}
	
	@GetMapping("/new")
	public String add(Model model, @ModelAttribute ArticleForm form) {
		model.addAttribute("contents", "article/form :: articleForm_contents");
		return "/headerLayout";
	}
	
	@PostMapping("/new")
	public String create(
		Model model,
		@ModelAttribute @Validated ArticleForm form,
		BindingResult bindingResult,
		@AuthenticationPrincipal User loginUser)
	{
		if(bindingResult.hasErrors()) {
			
			return add(model, form);
			
		} else {
			
			//カテゴリー
			Optional<Category> categoryOpt = categoryService.findOne(form.getCategoryName());
			if (categoryOpt.isEmpty()) {
				var category = new Category();
				category.setCategoryName(form.getCategoryName());
				categoryService.insertOne(category);
				categoryOpt = categoryService.findOne(form.getCategoryName());
			}
			
			//タグ
			String[] tagNames = form.getTagNames().split(",");
			List<String> tagNameList = new ArrayList<String>(Arrays.asList(tagNames));
			
			List<Tag> DuplicateTagList = tagService.findAllByDuplicateName(tagNameList);
			
			if(DuplicateTagList.size() != tagNames.length){
				for(var tag : DuplicateTagList) {
					tagNameList.remove(tagNameList.indexOf(tag.getTagName()));
				}
				List<Tag> tagList = new ArrayList<Tag>();
				for(var tagName : tagNameList) {
					var tag = new Tag();
					tag.setTagName(tagName);
					
					tagList.add(tag);
				}
				tagService.insertAll(tagList);
				
				DuplicateTagList = tagService.findAllByDuplicateName(Arrays.asList(tagNames));
			}
			
			var article = new Article();
			article.setUserId(loginUser.getUserId());
			categoryOpt.ifPresent(category -> article.setCategoryId(category.getCategoryId()));
			article.setTitle(form.getTitle());
			article.setLink(form.getLink());
			article.setSummary(form.getSummary());
			article.setBody(form.getBody());
			article.setCreated(LocalDateTime.now());
			article.setUpdated(LocalDateTime.now());
			
			articleService.insertOne(article);
			
			//記事_タグ 中間クラス
			Article lastArticle = articleService.selectLastRecord();
			
			List<ArticleTag> articleTagList = new ArrayList<ArticleTag>();
			for(var tag : DuplicateTagList) {
				var articleTag = new ArticleTag();
				articleTag.setArticleId(lastArticle.getArticleId());
				articleTag.setTagId(tag.getTagId());
				
				articleTagList.add(articleTag);
			}
			articleTagService.insertAll(articleTagList);
			
			return "redirect:/articles";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String edit(
		Model model,
		@ModelAttribute ArticleForm form,
		@PathVariable("id") int articleId,
		@AuthenticationPrincipal User loginUser)
	{	
		Article article = articleService.selectOne(articleId);
		
		if(loginUser != null && article.getUser().getUserId() == loginUser.getUserId()) {
			
			form.setTitle(article.getTitle());
			form.setLink(article.getLink());
			form.setSummary(article.getSummary());
			form.setBody(article.getBody());
			form.setCategoryName(article.getCategory().getCategoryName());
			
			model.addAttribute("articleId", articleId);
			model.addAttribute("contents", "article/form :: articleForm_contents");
			
			return "/headerLayout";
		} else {
			return "redirect:/articles";
		}
	}
	
	@PostMapping("/edit")
	public String update(
		Model model,
		@ModelAttribute @Validated ArticleForm form,
		BindingResult bindingResult,
		@RequestParam("articleId") int articleId,
		@AuthenticationPrincipal User loginUser)
	{
		if(bindingResult.hasErrors()){
			
			return edit(model, form, articleId, loginUser);
			
		} else {
			
			//カテゴリの登録
			Optional<Category> categoryOpt = categoryService.findOne(form.getCategoryName());
			if (categoryOpt.isEmpty()) {
				var category = new Category();
				category.setCategoryName(form.getCategoryName());
				categoryService.insertOne(category);
				categoryOpt = categoryService.findOne(form.getCategoryName());
			}
			
			//タグの登録
			String[] tagNames = form.getTagNames().split(",");
			List<String> tagNameList = new ArrayList<String>(Arrays.asList(tagNames));
			List<Tag> DuplicateTagList = tagService.findAllByDuplicateName(tagNameList);
			
			if(DuplicateTagList.size() != tagNames.length){
				for(var tag : DuplicateTagList) {
					tagNameList.remove(tagNameList.indexOf(tag.getTagName()));
				}
				List<Tag> tagList = new ArrayList<Tag>();
				for(var tagName : tagNameList) {
					var tag = new Tag();
					tag.setTagName(tagName);
					
					tagList.add(tag);
				}
				tagService.insertAll(tagList);
				
				DuplicateTagList = tagService.findAllByDuplicateName(Arrays.asList(tagNames));
			}
			
			var article = new Article();
			article.setArticleId(articleId);
			article.setUserId(loginUser.getUserId());
			categoryOpt.ifPresent(category -> article.setCategoryId(category.getCategoryId()));
			article.setTitle(form.getTitle());
			article.setLink(form.getLink());
			article.setSummary(form.getSummary());
			article.setBody(form.getBody());
			article.setCreated(LocalDateTime.now());
			article.setUpdated(LocalDateTime.now());
			
			articleService.updateOne(article);
			
			//記事_タグ 中間クラス
			Article lastArticle = articleService.selectLastRecord();
			List<Tag> oldTagList = tagService.findAllByArticleId(articleId);
			
			//新しい記事_タグを登録
			List<ArticleTag> newArticleTagList = new ArrayList<ArticleTag>();
			for(var newTag : DuplicateTagList) {
				if(oldTagList.contains(newTag) == false) {
					var newArticleTag = new ArticleTag();
					newArticleTag.setArticleId(lastArticle.getArticleId());
					newArticleTag.setTagId(newTag.getTagId());
					
					newArticleTagList.add(newArticleTag);
				}
			}
			if (newArticleTagList.size() > 0) {
				articleTagService.insertAll(newArticleTagList);
			}
			
			//古い記事_タグを削除
			List<Integer> oldTagIdList = new ArrayList<Integer>();
			for(var oldTag : oldTagList) {
				if (DuplicateTagList.contains(oldTag) == false) {
					oldTagIdList.add(oldTag.getTagId());
				}
			}
			if (oldTagIdList.size() > 0){
				articleTagService.deleteAll(lastArticle.getArticleId(), oldTagIdList);
			}
			
			return "redirect:/articles/" + articleId;
		}
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("articleId") int articleId) {
		
		articleService.deleteOne(articleId);
		
		return "redirect:/articles";
	}
}