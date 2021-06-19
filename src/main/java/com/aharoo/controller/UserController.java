package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class UserController {

	private final RegistrationService registrationService;

	@PostMapping("/signup")
	public ModelAndView register(ApplicationUser user){
		registrationService.register(user);
		return new ModelAndView("redirect:/registration");
	}

	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}

}
