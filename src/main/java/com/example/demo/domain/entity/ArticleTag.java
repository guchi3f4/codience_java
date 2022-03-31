package com.example.demo.domain.entity;

import lombok.Data;

@Data
public class ArticleTag {
 
	private int articleTagId;
	
	private int articleId;
	
	private int tagId;
	
}
