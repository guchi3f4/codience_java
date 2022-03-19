package com.example.demo.app.user.controller;

import java.io.IOException;
import java.util.List;

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

import com.example.demo.app.user.form.UserForm;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int userId, @ModelAttribute UserForm form) {
		
		model.addAttribute("contents", "user/edit :: userEdit_contents");
		
		User user = userService.selectOne(userId);
		form.setName(user.getName());
		form.setIntroduction(user.getIntroduction());
		
		model.addAttribute("userId", userId);
		
		return "/headerLayout";
	}
	
	@PostMapping(value = "edit", params = "update")
	public String update(
		Model model,
		@ModelAttribute @Validated UserForm form,
		BindingResult bindingResult,
		@RequestParam("userId") int userId) throws IOException
	{
		model.addAttribute("contents", "user/detail :: userDetail_contents");
		model.addAttribute("userInfo", "user/_userInfo :: userInfo_component");
		
		if(bindingResult.hasErrors()) {
			
			return edit(model, userId, form);
			
		} else {
			User user = new User();
			user.setUserId(userId);
			user.setName(form.getName());
			user.setIntroduction(form.getIntroduction());
			MultipartFile image = form.getProfileImageId();
			if(image.isEmpty()) {
				user.setProfileImageId(null);
			} else {
				user.setProfileImageId(image.getBytes());
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
		
		model.addAttribute("contents", "admin :: admin_contents");
		
		return "/headerLayout";
	}
}
