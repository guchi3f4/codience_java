package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Tag;

public interface TagService {
	
	public List<Tag> findAllByDuplicateName(List<String> tagNameList);
	
	public int insertAll(List<Tag> tagList);
	
	public List<Tag> findAllByArticleId(int articleId);
}
