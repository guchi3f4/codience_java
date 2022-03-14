package com.example.demo.domain.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public int insertOne(User user) {
		
		String password = passwordEncoder.encode(user.getPassword());
		
		int num = jdbcTemplate.update(
			"INSERT INTO user(name, email, password, created) VALUES(?, ?, ?, ?)",
			user.getName(), user.getEmail(),password, user.getCreated()
		);
		
		return num;
	}

	@Override
	public User selectOne(int userId) {
		var sql = "SELECT * FROM user WHERE id = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId);
		
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

	@Override
	public List<User> selectMany() {
		var sql = "SELECT * FROM user";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<User> list = new ArrayList<User>();
		for(var result : resultList) {
			var user = new User();
			user.setUserId((int)result.get("id"));
			user.setName((String)result.get("name"));
			user.setEmail((String)result.get("email"));
			user.setPassword((String)result.get("password"));
			user.setIntroduction((String)result.get("introduction"));
			user.setProfileImageId((byte[])result.get("profile_image_id"));
			user.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			
			list.add(user);
		}
		return list;
	}

	@Override
	public int updateOne(User user) {
		System.out.println(user);
		int num = jdbcTemplate.update(
			"UPDATE user SET name = ?, introduction = ?, profile_image_id = ? WHERE id = ?",
			user.getName() ,user.getIntroduction(), user.getProfileImageId(), user.getUserId()
		);
		System.out.println("更新:" + num);
		
		return num;
	}
}
