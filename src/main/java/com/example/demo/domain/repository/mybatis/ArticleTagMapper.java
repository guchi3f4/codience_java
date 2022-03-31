package com.example.demo.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.entity.ArticleTag;

@Mapper
public interface ArticleTagMapper {
	
	public int insertAll(List<ArticleTag> articleTagList);
	
	public int deleteAll(@Param("articleId") int articleId, @Param("TagIdList") List<Integer> TagIdList);
}
