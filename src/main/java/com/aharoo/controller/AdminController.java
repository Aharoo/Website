package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final ApplicationUserService userService;

	@GetMapping("/users")
	public List<ApplicationUser> getAllUsers(){
		return userService.loadAllUsers();
	}

	@GetMapping(path = "/users/{id}")
	public ApplicationUser getUserById(@PathVariable("id") Integer id){
		Optional<ApplicationUser> user =  userService.findById(id);
		return user.get();
	}
}
