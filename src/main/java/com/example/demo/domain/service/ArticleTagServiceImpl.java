package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.ArticleTag;
import com.example.demo.domain.repository.mybatis.ArticleTagMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleTagServiceImpl implements ArticleTagService {
	
	private final ArticleTagMapper articleTagMapper;

	@Override
	public int insertAll(List<ArticleTag> articleTagList) {
		
		return articleTagMapper.insertAll(articleTagList);
	}

	@Override
	public int deleteAll(int articleId, List<Integer> TagIdList) {
		
		return articleTagMapper.deleteAll(articleId, TagIdList);
	}
	
}
