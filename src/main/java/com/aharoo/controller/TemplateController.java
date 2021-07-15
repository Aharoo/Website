package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import com.aharoo.image.Image;
import com.aharoo.image.ImageService;
import com.aharoo.model.PriceCheckerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TemplateController {

	private final ImageService imageService;
	private final PriceCheckerService priceCheckerService;

	@GetMapping
	public String getIndexView(Model model){
		Iterable<Image> images = imageService.findAllImages();
		model.addAttribute("images",images);
		return "index";
	}

	@GetMapping("anime")
	public String getAnimeView(){
		return "anime-list";
	}

	@GetMapping("user-profile")
	public String getProfileView(@AuthenticationPrincipal ApplicationUser user, Model model){
		model.addAttribute("username","Greetings, " + user.getUsername());
		model.addAttribute("email","Your email is " + user.getEmail());
		model.addAttribute("image",user.getFilename());
		return "user-profile";
	}

	@GetMapping("user-profile/crypto")
	public String getCryptoPriceView(@AuthenticationPrincipal ApplicationUser user,Model model) throws IOException {
		model.addAttribute("username","Greetings, " + user.getUsername());
		model.addAttribute("email","Your email is " + user.getEmail());
		model.addAttribute("image",user.getFilename());
		model.addAttribute("bitcoinPrice","$ " + priceCheckerService.getBitcoinPrice());
		model.addAttribute("euroPrice","€ " + priceCheckerService.getEuroPrice());
		model.addAttribute("poundPrice","£ " + priceCheckerService.getPoundPrice());
		return "cryptoPrice";
	}

	@GetMapping("registration")
	public String getRegistrationView(){return "registration";}

	@GetMapping("login")
	public String getLoginView(){return "login";}

	@GetMapping("admin")
	public String getAdminView(){return "admin";}

	@GetMapping("recover")
	public String getRecoverPage(){return "recoveringPassword";}

}
