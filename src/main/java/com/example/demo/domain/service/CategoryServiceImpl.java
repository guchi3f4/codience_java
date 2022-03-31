package com.example.demo.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Category;
import com.example.demo.domain.repository.mybatis.CategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryMapper categoryMapper;

	@Override
	public Optional<Category> findOne(String categoryName) {
		
		return categoryMapper.findOne(categoryName);
	}

	@Override
	public int insertOne(Category category) {
		
		return categoryMapper.insertOne(category);
	}
}
