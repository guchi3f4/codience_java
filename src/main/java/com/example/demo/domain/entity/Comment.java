package com.example.demo.domain.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {
	
	private int commentId;
	
	private int userId;
	
	private int articleId;
	
	private String comment;
	
	private LocalDateTime created;
	
	private User user;
	
	private Article article; 
}
