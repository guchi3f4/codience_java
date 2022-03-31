package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Tag;

public interface TagDao {
	
	public int insertAll(List<Tag> tagList);
	
	public List<String> selectNames(List<String> tagNameList);
	
	public int deleteAll(List<String> tagNameList);
}
