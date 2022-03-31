package com.example.demo.domain.repository.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.entity.Category;

@Mapper
public interface CategoryMapper {
	
	public Optional<Category> findOne(String categoryName);

	public int insertOne(Category category);

}
