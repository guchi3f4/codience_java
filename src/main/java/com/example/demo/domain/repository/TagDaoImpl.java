//package com.example.demo.domain.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.domain.entity.Tag;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class TagDaoImpl implements TagDao {
//	
//	private final JdbcTemplate jdbcTemplate;
//
//	@Override
//	public int insertAll(List<Tag> tagList) {
//		
//		StringBuilder sql = new StringBuilder("INSERT INTO tag(name) VALUES");
//		
//		for(var tag : tagList) {	
//			sql.append("('").append(tag.getName()).append("'),");
//		}
//		sql.deleteCharAt(sql.length() - 1);
//		System.out.println(sql);
//		
//		int num = jdbcTemplate.update(sql.toString());
//		
//		return  num;
//	}
//
//	@Override
//	public List<String> selectNames(List<String> tagNameList) {
//		
//		StringBuilder sql = new StringBuilder("SELECT name FROM tag WHERE name IN(");
//		
//		for(var tagName : tagNameList) {
//			sql.append("'").append(tagName).append("',");
//		}
//		sql.deleteCharAt(sql.length() - 1).append(")");
//		System.out.println(sql);
//		
//		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString());
//		List<String> list = new ArrayList<String>();
//		
//		for(var result : resultList) {
//			String tagName = (String)result.get("name");
//			list.add(tagName);
//		}
//		
//		return list;
//	}
//
//	@Override
//	public int deleteAll(List<String> tagNameList) {
//		
//		StringBuilder sql = new StringBuilder("DELETE FROM tag WHERE name IN(");
//		for(var tagName : tagNameList) {
//			sql.append("'").append(tagName).append("',");
//		}
//		sql.deleteCharAt(sql.length() - 1).append(")");
//		System.out.println(sql);
//		
//		int num = jdbcTemplate.update(sql.toString());
//		
//		return num;
//	}
//	
//}
