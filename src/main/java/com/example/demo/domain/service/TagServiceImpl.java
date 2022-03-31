package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.repository.mybatis.TagMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagMapper tagMapper;

	@Override
	public List<Tag> findAllByDuplicateName(List<String> tagNameList) {
		
		return tagMapper.findAllByDuplicateName(tagNameList);
	}

	@Override
	public int insertAll(List<Tag> tagList) {
		
		return tagMapper.insertAll(tagList);
	}

	@Override
	public List<Tag> findAllByArticleId(int articleId) {
		
		return tagMapper.findAllByArticleId(articleId);
	}
	
}
