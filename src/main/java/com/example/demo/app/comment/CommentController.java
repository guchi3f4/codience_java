package com.example.demo.app.comment;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.app.article.controller.ArticleController;
import com.example.demo.app.article.form.ArticleForm;
import com.example.demo.app.comment.form.CommentForm;
import com.example.demo.app.user.form.SignupForm;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles/comments")
public class CommentController {

	
	private final CommentService commentService;
	
	private final ArticleController articleController;
	
	@PostMapping("/new")
	public String create(
		Model model,
		@Validated CommentForm commentForm,
		BindingResult bindingResult,
		ArticleForm articleForm,
		SignupForm signupForm,
		@RequestParam("articleId") int articleId,
		@AuthenticationPrincipal User loginUser)
	{
		
		if(bindingResult.hasErrors()) {
			return articleController.detail(model, articleId, articleForm, signupForm, commentForm, loginUser);
		} else {
			
			var comment = new Comment();
			comment.setUserId(loginUser.getUserId());
			comment.setArticleId(articleId);
			comment.setComment(commentForm.getComment());
			comment.setCreated(LocalDateTime.now());
			
			int num = commentService.insert(comment);
			if(num > 0) {
				System.out.println("comment：登録成功");
			} else {
				System.out.println("comment：登録失敗");
			}
			
			return "redirect:/articles/" + articleId;
		}
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("commentId") int commentId, @RequestParam("articleId") int articleId) {
		
		int num = commentService.delete(commentId);
		if(num > 0) {
			System.out.println("comment：削除成功");
		} else {
			System.out.println("comment：削除失敗");
		}
		
		return "redirect:/articles/" + articleId;
	}
}
