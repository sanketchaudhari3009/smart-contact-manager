package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;



@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	@ResponseBody
	
	public String test()
	{
		User user = new User();
		user.setName("Sanket Chaudhari");
		user.setEmail("sanket@dev.io");
		
		userRepository.save(user);
		return "Working";
	}
}
