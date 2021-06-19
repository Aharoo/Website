package com.aharoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

	@GetMapping
	public String getIndexView(){return "index.html";}

	@GetMapping("anime")
	public String getAnimeView(){
		return "anime-list.html";
	}

	@GetMapping("gallery")
	public String getGalleryView(){return "gallery.html";}

	@GetMapping("registration")
	public String getRegistrationView(){return "registration.html";}

	@GetMapping("login")
	public String getLoginView(){return "login.html";}

}
