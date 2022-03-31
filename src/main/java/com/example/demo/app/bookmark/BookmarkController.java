package com.example.demo.app.bookmark;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Bookmark;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles/bookmarks")
public class BookmarkController {
	
	private final BookmarkService bookmarkService;
	
	@GetMapping("/new/{id}")
	public ModelAndView create(@PathVariable("id") int articleId) {
		
		User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		var bookmark = new Bookmark();
		bookmark.setUserId(loginUser.getUserId());
		bookmark.setArticleId(articleId);
		
		int num = bookmarkService.insertOne(bookmark);
		if(num > 0) {
			System.out.println(" bookmark：登録成功");
		} else {
			System.out.println(" bookmark：登録失敗");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/bookmark/_bookmark :: bookmark");
		
		List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
		mav.addObject("myBookmarks", myBookmarks);
		
		var article = new Article();
		article.setArticleId(articleId);
		mav.addObject("article", article);
		
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") int articleId) {
		
		User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Bookmark bookmark = bookmarkService.selectBy(loginUser.getUserId(), articleId);
		
		int num = bookmarkService.deleteOne(bookmark.getBookmarkId());
		if(num > 0) {
			System.out.println(" bookmark：削除成功");
		} else {
			System.out.println(" bookmark：削除失敗");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/bookmark/_bookmark :: bookmark");
		
		List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
		mav.addObject("myBookmarks", myBookmarks);
		
		var article = new Article();
		article.setArticleId(articleId);
		mav.addObject("article", article);
		
		return mav;
	}
}
