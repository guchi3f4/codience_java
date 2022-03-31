//package com.example.demo.domain.repository;
//
//import java.util.Map;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.domain.entity.ArticleTag;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class ArticleTagDaoImple implements ArticleTagDao {
//	
//	private JdbcTemplate jdbcTemplate;
//
//	@Override
//	public int insertOne(ArticleTag articleTag) {
//		
//		int num = jdbcTemplate.update(
//			"INSERT INTO article_tag(article_id, tag_id) VALUES(?, ?)",
//			articleTag.getArticleId(), articleTag.getTagId()
//		);
//		
//		return num;
//	}
//	
//	@Override
//	public int selectIdby(int articleId, int tagId) {
//		
//		var sql = "SELECT id FROM article_tag WHERE article_id = ? AND tag_id = ?";
//		
//		Map<String, Object> result = jdbcTemplate.queryForMap(sql, articleId, tagId);
//		int resultId = (int)result.get("id");
//		
//		return resultId;
//	
//	}
//	
//	@Override
//	public int deleteOne(int articleTagId) {
//		
//		int num = jdbcTemplate.update("DELETE FROM aticle_tag WHERE id =?", articleTagId);
//		
//		return num;
//	}
//}
