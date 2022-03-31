package com.example.demo.app.favorite;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Favorite;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles/favorites")
public class FavoriteController {
	
	private final FavoriteService favoriteService;
	
	@GetMapping("/new/{id}")
	public ModelAndView create(@PathVariable("id") int articleId) {
		
		User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		var favorite = new Favorite();
		favorite.setUserId(loginUser.getUserId());
		favorite.setArticleId(articleId);
		
		int num = favoriteService.insertOne(favorite);
		if(num > 0) {
			System.out.println("favorite：登録成功");
		} else {
			System.out.println("favorite：登録失敗");
		}
		
//		return "redirect:/articles";
		
		List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
		
		var article = new Article();
		article.setArticleId(articleId);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/favorite/_favorite :: favorite");
		mav.addObject("myFavorites", myFavorites);
		mav.addObject("article", article);
		
		return mav;
		
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") int articleId) {
		
		User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Favorite favorite = favoriteService.selectBy(loginUser.getUserId(), articleId);
		
		int num = favoriteService.deleteOne(favorite.getFavoriteId());
		if(num > 0) {
			System.out.println("favorite：削除成功");
		} else {
			System.out.println("favorite：削除失敗");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/favorite/_favorite :: favorite");
		
		List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
		mav.addObject("myFavorites", myFavorites);
		
		var article = new Article();
		article.setArticleId(articleId);
		mav.addObject("article", article);
		
		return mav;
	}
	
}
