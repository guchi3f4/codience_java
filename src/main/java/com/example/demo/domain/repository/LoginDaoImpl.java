package com.example.demo.domain.repository;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LoginDaoImpl {
	
	private final JdbcTemplate jdbcTemplate;
	
	public User selectSessionUser(String email) {
		var sql = "SELECT * FROM user WHERE email = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
		
		var user = new User();
		user.setUserId((int)result.get("id"));
		user.setName((String)result.get("name"));
		user.setEmail((String)result.get("email"));
		user.setPassword((String)result.get("password"));
		user.setIntroduction((String)result.get("introduction"));
		user.setProfileImageId((byte[])result.get("profile_image_id"));
		user.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		
		return user;
	}
}
