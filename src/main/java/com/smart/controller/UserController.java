package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
		String userName = principal.getName();
		System.out.println("username"+userName);
		
		User user = userRepository.getUserByUserName(userName);
		
		System.out.println("user "+user);
		
		model.addAttribute("user", user);
	}


	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal)
	{
		
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal )
	{
		
		try
		{
		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);
		
		//processing uploading file
		if(file.isEmpty())
		{
			System.out.println("File is empty");
		}
		else
		{
			contact.setImage(file.getOriginalFilename());
			
			File saveFile = new ClassPathResource("static/img").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("Image is uploaded");
		}
		
		
		user.getContacts().add(contact);
		contact.setUser(user);
		
		
		
		
		
		this.userRepository.save(user);
		
		System.out.println("DATA"+contact);
		System.out.println("added to DB");
		}
		catch(Exception e)
		{
			System.out.println("ERROR " +e.getMessage());
			e.printStackTrace();
		}
		return "normal/add_contact_form";
	}
	
}
