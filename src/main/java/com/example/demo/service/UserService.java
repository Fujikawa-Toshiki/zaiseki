package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.PasswordHasher;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;

@Service
public class UserService implements BaseService<User> {
	@Autowired
	private UserDao dao;

	@Override
	public List<User> findAll() {
		return dao.findAll();
	}

	@Override
	public User findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(User user) {
		updateSecurityContext(user);
		dao.save(user);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public User findByUsername(String user_name) throws DataNotFoundException {
		return dao.findByUserName(user_name);
	}

	public User auth(User user) {
		try {
			User foundUser = dao.findByUserName(user.getUsername());
			if (PasswordHasher.matches(user.getPassword(), foundUser.getPassword())) {
				foundUser.setAuth(true);
				return foundUser;
			}
		} catch (DataNotFoundException e) {
		}
		user.setAuth(false);
		return user;
	}

	public boolean isUnique(String user_name) {
		try {
			dao.findByUserName(user_name);
			return false;
		} catch (DataNotFoundException e) {
			return true;
		}
	}
	
	/*
	 * SpringSecurity側の更新 
	 */
	private void updateSecurityContext(User user) {
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles("ADMIN")
				.build();
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(new UsernamePasswordAuthenticationToken(
				userDetails,
				userDetails.getPassword(),
				userDetails.getAuthorities()));
	}
}
