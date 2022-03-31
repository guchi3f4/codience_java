package com.example.demo.app.user.validate;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface UniqueEmail {
	
	String message() default "すでに登録済みのメールアドレスです";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	@Documented
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface List {
		UniqueEmail[] value();
	}
}
