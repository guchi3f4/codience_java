package com.example.demo.domain.service.SharedService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Category;
import com.example.demo.domain.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorySharedServiceImpl {
	
	private final CategoryService categoryService;
	
	public Optional<Category> insertCategory(Article article){
		
		Optional<Category> categoryOpt = categoryService.findOne(article.getCategory().getCategoryName());
		
		//カテゴリが存在しない場合登録、取得
		if (categoryOpt.isEmpty()) {
			var category = new Category();
			category.setCategoryName(article.getCategory().getCategoryName());
			categoryService.insertOne(category);
			
			categoryOpt = categoryService.findOne(article.getCategory().getCategoryName());
		}
		
		return categoryOpt;
	}
	
}
