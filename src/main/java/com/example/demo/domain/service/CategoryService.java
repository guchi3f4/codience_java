package com.example.demo.domain.service;

import java.util.Optional;

import com.example.demo.domain.entity.Category;

public interface CategoryService {
	
	public Optional<Category> findOne(String categoryName);
	
	public int insertOne(Category category);
	
}
