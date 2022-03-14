package com.example.demo.domain.entity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import lombok.Data;

@Data
public class User {
	
	private int userId;
	private String name;
	private String email;
	private String password;
	private String introduction;
	private byte[] profileImageId;
	private LocalDateTime created;
	private String role = "ROLE_GENERAL";
	
	public String getImgOut() throws IOException{
        return Base64.getEncoder().encodeToString(this.profileImageId);
    }
}
