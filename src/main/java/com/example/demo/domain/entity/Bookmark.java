package com.example.demo.domain.entity;

import lombok.Data;

@Data
public class Bookmark {
	
	private int BookmarkId;
	
	private int userId;
	
	private int articleId;
	
	private User user;
	
	private Article article;
}
