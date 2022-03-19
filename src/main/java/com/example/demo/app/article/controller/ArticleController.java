package com.example.demo.app.article.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
import com.example.demo.app.user.form.SignupForm;
import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.ArticleService;
import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
	
	private final ArticleService articleService;
	
	private final UserService userService;
	
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
		
		model.addAttribute("user", loginUser);
		
		
		return "/headerLayout";
	}
	
	@GetMapping("/{id}")
	public String detail(
		Model model,
		@PathVariable("id") int articleId,
		ArticleForm articleForm,
		SignupForm signupForm)
	{	
		model.addAttribute("contents", "article/detail :: articleDetail_contents");
		
		Article article = articleService.selectOne(articleId);
		model.addAttribute("article", article);
		
		User user = userService.selectOne(article.getUser().getUserId());
		model.addAttribute("user", user);
		
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
			var article = new Article();
			article.setUserId(loginUser.getUserId());
			article.setTitle(form.getTitle());
			article.setLink(form.getLink());
			article.setSummary(form.getSummary());
			article.setBody(form.getBody());
			article.setCreated(LocalDateTime.now());
			article.setUpdated(LocalDateTime.now());
			
			articleService.insertOne(article);
			
			return "redirect:/articles";
		}
	}
	
	@GetMapping("/{id}/edit")
	public String edit(
		Model model,
		@ModelAttribute ArticleForm form,
		@PathVariable("id") int articleId)
	{
		model.addAttribute("contents", "article/form :: articleForm_contents");
		
		Article article = articleService.selectOne(articleId);
		form.setTitle(article.getTitle());
		form.setLink(article.getLink());
		form.setSummary(article.getSummary());
		form.setBody(article.getBody());
		
		model.addAttribute("articleId", articleId);
		
		return "/headerLayout";
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
			
			return edit(model, form, articleId);
			
		} else {
			var article = new Article();
			article.setArticleId(articleId);
			article.setUserId(loginUser.getUserId());
			article.setTitle(form.getTitle());
			article.setLink(form.getLink());
			article.setSummary(form.getSummary());
			article.setBody(form.getBody());
			article.setCreated(LocalDateTime.now());
			article.setUpdated(LocalDateTime.now());
			
			articleService.updateOne(article);
			
			return "redirect:/articles/" + articleId;
		}
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("articleId") int articleId) {
		
		articleService.deleteOne(articleId);
		
		return "redirect:/articles";
	}
}