package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Bookmark;
import com.example.demo.domain.repository.BookmarkDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
	
	private final BookmarkDao dao;

	@Override
	public int insertOne(Bookmark bookmark) {
		
		return dao.insertOne(bookmark);
	}

	@Override
	public int deleteOne(int bookmarkId) {
	
		return dao.deleteOne(bookmarkId);
	}

	@Override
	public List<Integer> selectArticleId(int userId) {
		
		return dao.selectArticleId(userId);
	}

	@Override
	public Bookmark selectBy(int userId, int articleId) {
		
		return dao.selectBy(userId, articleId);
	}
}
