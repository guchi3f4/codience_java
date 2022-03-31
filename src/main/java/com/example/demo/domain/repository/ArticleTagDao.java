package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.ArticleTag;

public interface ArticleTagDao {
	
	public int insertAll(List<ArticleTag> articleTagList);
	
	public int selectIdby(int articleId, int tagId);
	
	public int deleteAll(List<Integer> articleTagIdList);

}
