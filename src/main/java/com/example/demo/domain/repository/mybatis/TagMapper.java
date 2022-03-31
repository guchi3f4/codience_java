package com.example.demo.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.entity.Tag;

@Mapper
public interface TagMapper {

	public List<Tag> findAllByDuplicateName(List<String> tagNameList);
	
	public int insertAll(List<Tag> tagList);
	
	public List<Tag> findAllByArticleId(int articleId);
}
