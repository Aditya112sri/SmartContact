package com.smartcontact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.aspectj.apache.bcel.util.ClassPath;
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

import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;
import com.smartcontact.helper.Message;
import com.smartcontact.repository.UserRepo;
import com.smartcontact.services.HomeServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepo repo;
	
	@ModelAttribute
	public void commoData(Model m, Principal p) {
		String name = p.getName();
		
		m.addAttribute("title","User-Dashboard");
		User user = repo.getUserByUsername(name);
		
		m.addAttribute("user",user);
	}
	
	
	@GetMapping("/index")
	public String dashBoard(Model model, Principal principal ) {
		// Pricipal :This interface represents the abstract notion of a principal, which can be used to represent any entity, such as an individual, acorporation, and a login id.
		
		
		model.addAttribute("title","User-Dashboard");
		
		
	
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add_contact")
	public String addContactHandler(Model model) {
		model.addAttribute("title","Add Contact-Smart Contact Manager");
		model.addAttribute("contact",new Contact());
		return "normal/add_contact";
	}
	
	
	@PostMapping("/process-contact")
	public String processAddContact(@ModelAttribute("contact")
						Contact contact,
						@RequestParam("profileImg") MultipartFile file,
						Principal principal,
						HttpSession session
						)
						 {
		try {
			
			//file uploading
			if(file.isEmpty()) {
				System.out.println("File is empty.");
			}else {
				//file is not empty
				contact.setImageUrl(file.getOriginalFilename());
				
				//create path where file upload
				File uFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(uFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				//use Files class in  java.nio.*package
				//Files.copy(file.inputStream, complete path,Option when file not exists)
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Uploaded..");
			}
			
			String name = principal.getName();
			User user = this.repo.getUserByUsername(name);
			contact.setUser(user);
			user.getContact().add(contact);
			this.repo.save(user);
			session.setAttribute("message", new Message("Contact added successfully!!", "success"));
			System.out.println("User contact:"+contact);
			
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong..", "danger"));
			e.printStackTrace();
		}
		return "normal/add_contact";
	}
	
}














