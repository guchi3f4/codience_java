package com.example.demo.app.user.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserForm {
	
	@NotNull
	@Size(min = 2, max = 50)
	private String name;
	
	@Size(min= 2, max = 200)
	private String introduction;
	
//	private byte[] profileImageId;
	MultipartFile profileImageId;
}
