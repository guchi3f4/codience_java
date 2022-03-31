package com.example.demo.domain.service.SharedService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.entity.Article;
import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TagSharedServiceImpl {
	
	private final TagService tagService;

	public List<Tag> inserTag(Article article, List<String> tagNameList) {
		
		//該当タグネームがすでに登録してあるタグオブジェクトを取得
		List<Tag> DuplicateTagList = tagService.findAllByDuplicateName(tagNameList);
		
		//該当タグ全てが登録済みになっていなければ新規登録
		if(DuplicateTagList.size() != tagNameList.size()){
			
			ArrayList<String> newTagNameList = new ArrayList<String>(tagNameList);
			for(var tag : DuplicateTagList) {
				newTagNameList.remove(newTagNameList.indexOf(tag.getTagName()));
			}
			List<Tag> tagList = new ArrayList<Tag>();
			for(var tagName : newTagNameList) {
				var tag = new Tag();
				tag.setTagName(tagName);
				
				tagList.add(tag);
			}
			tagService.insertAll(tagList);
			
			//該当タグ全てが登録済みになったことを確認、取得
			DuplicateTagList = tagService.findAllByDuplicateName(tagNameList);
		}
		
		return DuplicateTagList;
		
	}
}
