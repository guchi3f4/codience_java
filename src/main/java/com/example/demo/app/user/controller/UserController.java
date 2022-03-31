package com.example.demo.app.user.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.article.form.ArticleForm;
import com.example.demo.app.user.form.SignupForm;
import com.example.demo.app.user.form.UserForm;
import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.BookmarkService;
import com.example.demo.domain.service.FavoriteService;
import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	private final BookmarkService bookmarkService;
	
	private final FavoriteService favoriteService;
	
	@GetMapping("/{id}/edit")
	public String edit(
		Model model,
		@PathVariable("id") int userId,
		@ModelAttribute UserForm form,
		@AuthenticationPrincipal User loginUser) {
		
		if(loginUser != null && userId == loginUser.getUserId()) {
			
			model.addAttribute("contents", "user/edit :: userEdit_contents");
			
			User user = userService.selectOne(userId);
			
			form.setName(user.getName());
			form.setIntroduction(user.getIntroduction());
			
			model.addAttribute("userId", userId);
			
			Path path = Paths.get("MyBatisCodience/src/main/resources/profileImageId");
			System.out.println("パス:" + path.toAbsolutePath());
			
			return "/headerLayout";
		} else {
			return "redirect:/articles";
		}
	}
	
	@PostMapping(value = "edit", params = "update")
	public String update(
		Model model,
		@ModelAttribute @Validated UserForm form,
		BindingResult bindingResult,
		@RequestParam("userId") int userId,
		@AuthenticationPrincipal User loginUser) throws IOException
	{		model.addAttribute("contents", "user/detail :: userDetail_contents");
		model.addAttribute("userInfo", "user/_userInfo :: userInfo_component");
		
		if(bindingResult.hasErrors()) {
			
			return edit(model, userId, form, loginUser);
			
		} else {
			User user = new User();
			user.setUserId(userId);
			user.setName(form.getName());
			user.setIntroduction(form.getIntroduction());
			
			MultipartFile multipartFile = form.getProfileImageId();
			if(multipartFile.isEmpty()) {
				user.setProfileImageId(null);
			} else {
				int dotPlace = multipartFile.getOriginalFilename().indexOf(".");
				String fileType = multipartFile.getOriginalFilename().substring(dotPlace);
				String fileName = "user_icon" + user.getUserId() + fileType;
				
				Path uploadfile = Paths.get("profileImageId/" + fileName);
				System.out.println(uploadfile.toAbsolutePath());
				try (OutputStream os = Files.newOutputStream(uploadfile)) {
					byte[] bytes = multipartFile.getBytes();
					os.write(bytes);
				} catch (IOException ex) {
					System.err.println(ex);
				}
				user.setProfileImageId(fileName);
			}
			
			userService.updateOne(user);
			
			return "redirect:/users/" + userId;
		}
		
	}
	
	@GetMapping()
	public String index(Model model) {
		
		model.addAttribute("contents", "user/index :: userIndex_contents");
		model.addAttribute("userInfo", "user/_userInfo :: userInfo_component");
		
		List<User> list = userService.selectMany();
		model.addAttribute("userList", list);
		
		return "/headerLayout";
	}
	
	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable("id") int userId) {
		model.addAttribute("contents", "user/detail :: userDetail_contents");
		model.addAttribute("userInfo", "user/_userInfo :: userInfo_component");
		
		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		
		return "/headerLayout";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		
		
		model.addAttribute("now", LocalDateTime.now().toString() + " init");
		
		return "/admin";
	}
	
	//該当ユーザー記事投稿一覧画面
	@GetMapping("/{id}/posts")
	public String postIndex(
		Model model,
		@PathVariable("id") int userId,
		SignupForm signupForm,
		ArticleForm articleForm,
		@AuthenticationPrincipal User loginUser)
	{
		model.addAttribute("contents", "user/postIndex :: postIndex_contents");
		
		List<Article> articleList = userService.selectPostArticles(userId);
		model.addAttribute("articleList", articleList);
		
		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		
		if(loginUser != null) {
			
			List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myBookmarks", myBookmarks);
			
			List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myFavorites", myFavorites);
		}
		
		return "/headerLayout";
	}
	
	//該当ユーザーブックマーク記事一覧画面
	@GetMapping("/{id}/bookmarks")
	public String bookmarkIndex(
		Model model,
		@PathVariable("id") int userId,
		SignupForm signupForm,
		ArticleForm articleForm,
		@AuthenticationPrincipal User loginUser)
	{
		model.addAttribute("contents", "user/bookmarkIndex :: bookmarkIndex_contents");
		
		List<Article> articleList = userService.selectBookmarkArticle(userId);
		model.addAttribute("articleList", articleList);
		
		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		
		if(loginUser != null) {
			
			List<Integer> myBookmarks = bookmarkService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myBookmarks", myBookmarks);
			
			List<Integer> myFavorites = favoriteService.selectArticleId(loginUser.getUserId());
			model.addAttribute("myFavorites", myFavorites);
		}
		
		return "/headerLayout";
	}
}
