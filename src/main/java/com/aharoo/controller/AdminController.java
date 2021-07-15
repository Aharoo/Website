package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import com.aharoo.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final ApplicationUserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AdminController(ApplicationUserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	public List<ApplicationUser> getAllUsers(){
		return userService.loadAllUsers();
	}

	@GetMapping(path = "/users/{id}")
	public ApplicationUser getUserById(@PathVariable("id") Integer id){
		return userService.findById(id).get();
	}

	@PostMapping("/users/registration/user")
	public ModelAndView addUser(ApplicationUser user){
		boolean isPresent = userService.findByEmail(user.getEmail()).isPresent();
		if (isPresent) throw new IllegalStateException("Email is already exists");

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(ApplicationUserRole.USER);
		user.setEnabled(true);
		userService.signUpUser(user);
		return new ModelAndView("/admin");
	}

	@PostMapping("/users/registration/admin")
	public ModelAndView addAdmin(ApplicationUser user) {
		boolean isPresent = userService.findByEmail(user.getEmail()).isPresent();
		if (isPresent) throw new IllegalStateException("Email is already exists");

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(ApplicationUserRole.ADMIN);
		user.setEnabled(true);
		userService.signUpUser(user);
		return new ModelAndView("/admin");
	}
}
