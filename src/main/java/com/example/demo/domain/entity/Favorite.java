package com.example.demo.domain.entity;

import lombok.Data;

@Data
public class Favorite {
	
	private int FavoriteId;
	
	private int userId;
	
	private int articleId;
	
	private User user;
	
	private Article article;
}
