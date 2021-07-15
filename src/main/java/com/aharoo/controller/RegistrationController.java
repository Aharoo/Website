package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.registration.RegistrationService;
import com.aharoo.registration.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private final RegistrationService registrationService;
	private final UserValidator userValidator;

	@Autowired
	public RegistrationController(RegistrationService registrationService,
								  UserValidator userValidator) {
		this.registrationService = registrationService;
		this.userValidator = userValidator;
	}

	@Value("${message.username}")
	private String usernameMessage;
	@Value("${message.password}")
	private String passwordMessage;
	@Value("${message.email}")
	private String emailMessage;
	@Value("${message.checkemail}")
	private String checkEmail;


	@PostMapping("/signup")
	public ModelAndView register(ApplicationUser user,Model model){
		if (!userValidator.isUsernameValid(user.getUsername())) {
			model.addAttribute("usernameMessage", usernameMessage);
			return new ModelAndView("/registration");
		}
		if (!userValidator.isPasswordValid(user.getPassword())) {
			model.addAttribute("passwordMessage", passwordMessage);
			return new ModelAndView("/registration");
		}
		if (!userValidator.isEmailValid(user.getEmail())) {
			model.addAttribute("emailMessage", emailMessage);
			return new ModelAndView("/registration");
		}

		registrationService.register(user);
		model.addAttribute("checkemail",checkEmail);

		return new ModelAndView("/registration");
	}



	@GetMapping(path = "confirm")
	public ModelAndView confirm(@RequestParam("token") String token) {
		registrationService.confirmToken(token);
		return new ModelAndView("redirect:/login");
	}

}
