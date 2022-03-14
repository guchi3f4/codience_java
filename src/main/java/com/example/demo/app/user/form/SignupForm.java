package com.example.demo.app.user.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignupForm {
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
}
