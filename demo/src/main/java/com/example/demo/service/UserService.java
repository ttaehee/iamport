package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.User;

public interface UserService {
	
	public String getNow();
	public List<User> getUserList();

}
