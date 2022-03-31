package com.example.demo.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Article {
		
	private int articleId;
	private int userId;
	private int categoryId;
	private String title;
	private String link;
	private String summary;
	private String body;
	private LocalDateTime created;
	private LocalDateTime updated;
	private User user;
	private Category category;
	private List<Tag> tags;
}
