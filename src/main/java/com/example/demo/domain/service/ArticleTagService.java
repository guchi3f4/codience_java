package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.ArticleTag;

public interface ArticleTagService {
	
	public int insertAll(List<ArticleTag> articleTagList);
	
	public int deleteAll(int articleId, List<Integer> TagIdList);
}
