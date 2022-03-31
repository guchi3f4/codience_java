package com.example.demo.app.article.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ArticleForm {
	
	private int userId;
	
	@NotNull
	private String title;
	
	@NotNull
	private String link;
	
	private String summary;
	
	private String body;
	
	@NotNull
	private String categoryName;
	
	@NotNull
	private String tagNames;
}
