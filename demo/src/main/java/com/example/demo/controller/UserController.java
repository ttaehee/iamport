package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.domain.User;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@RequestMapping(value="/user/now", method=RequestMethod.GET)
	public @ResponseBody String getNow(Model model) {
		return userServiceImpl.getNow();
	}
	
	@GetMapping("user/list")
	public @ResponseBody List<User> getUserList(){
		return userServiceImpl.getUserList();
	}

}
