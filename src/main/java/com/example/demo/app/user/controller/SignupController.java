package com.example.demo.app.user.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.app.user.form.SignupForm;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public String add(Model model, @ModelAttribute SignupForm form) {
		
		model.addAttribute("contents", "user/signup :: signupForm_contents");
		
		return "/headerLayout";
	}
	
	@PostMapping("/signup")
	public String create(Model model, @ModelAttribute @Validated SignupForm form, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return add(model, form);
			
		} else {
			System.out.println(form);
			var user = new User();
			user.setName(form.getName());
			user.setEmail(form.getEmail());
			user.setPassword(form.getPassword());
			user.setCreated(LocalDateTime.now());
			
			userService.insertOne(user);
			
			return "redirect:/users";
		}
	}
}
