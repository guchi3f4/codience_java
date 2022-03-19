package com.example.demo.app.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(Model model) {
		
		model.addAttribute("contents", "/user/login :: login_contents");
		
		return "/headerLayout";
	}
}
