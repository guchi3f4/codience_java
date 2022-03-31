package com.example.demo.domain.service.SharedService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.ArticleTag;
import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.service.ArticleTagService;
import com.example.demo.domain.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleTagSharedServiceImpl {
	
	private final ArticleTagService articleTagService;
	
	private final TagService tagService;
	
	public void insertArticleTag(List<Tag> tagList, Article lastArticle) {
		
		List<ArticleTag> articleTagList = new ArrayList<ArticleTag>();
		for(var tag : tagList) {
			var articleTag = new ArticleTag();
			articleTag.setArticleId(lastArticle.getArticleId());
			articleTag.setTagId(tag.getTagId());

			articleTagList.add(articleTag);
		}
		
		articleTagService.insertAll(articleTagList);
	}
	
	public void updateArticleTag(Article article, List<Tag> tagList) {
		
		List<Tag> oldTagList = tagService.findAllByArticleId(article.getArticleId());
		
		//新しい記事_タグを登録
		List<ArticleTag> newArticleTagList = new ArrayList<ArticleTag>();
		for(var newTag : tagList) {
			if(oldTagList.contains(newTag) == false) {
				var newArticleTag = new ArticleTag();
				newArticleTag.setArticleId(article.getArticleId());
				newArticleTag.setTagId(newTag.getTagId());
				
				newArticleTagList.add(newArticleTag);
			}
		}
		if (newArticleTagList.size() > 0) {
			articleTagService.insertAll(newArticleTagList);
		}
		
		//古い記事_タグを削除
		List<Integer> oldTagIdList = new ArrayList<Integer>();
		for(var oldTag : oldTagList) {
			if (tagList.contains(oldTag) == false) {
				oldTagIdList.add(oldTag.getTagId());
			}
		}
		if (oldTagIdList.size() > 0){
			articleTagService.deleteAll(article.getArticleId(), oldTagIdList);
		}
		
	}
	
}
