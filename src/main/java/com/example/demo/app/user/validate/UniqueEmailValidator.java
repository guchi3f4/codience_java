package com.example.demo.app.user.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	
	private final UserService userService;

	@Override
	public void initialize(UniqueEmail constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {;
		String email = userService.selectEmail(value);
		if(email == null) {
			return true;
		}
		return false;
	}
}
