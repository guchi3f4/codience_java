package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.entity.Bookmark;

public interface BookmarkDao {
	
	public int insertOne(Bookmark bookmark);
	
	public int deleteOne(int bookmarkId);
	
	public List<Integer> selectArticleId(int userId);
	
	public Bookmark selectBy(int userId, int articleId);
}
