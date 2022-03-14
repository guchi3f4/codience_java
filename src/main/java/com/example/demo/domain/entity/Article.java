package com.example.demo.domain.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Article {
		
	private int articleId;
	private int userId;
	private String title;
	private String link;
	private String summary;
	private String body;
	private LocalDateTime created;
	private LocalDateTime updated;
	private User user;
}
