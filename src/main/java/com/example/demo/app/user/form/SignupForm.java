package com.example.demo.app.user.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.example.demo.app.user.validate.UniqueEmail;

import lombok.Data;

@Data
public class SignupForm {
	
	@NotNull
	@Length(min = 2, max= 30, message = "2文字以上30文字以内で入力してください")
	private String name;
	
	@NotNull
	@Email(message = "不正なメールアドレスの形式です")
	@UniqueEmail
	private String email;
	
	@NotNull
	@Length(min = 6, message = "6文字以上にしてください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字で入力してください")
	private String password;
	
	@NotNull
	private String passwordConfirmation;
	
	@AssertTrue(message = "パスワードが一致しません")
	public boolean isPasswordValid() {
        if (password == null || password.isEmpty()) {
            return true;
        }

        return password.equals(passwordConfirmation);
    } 
}
