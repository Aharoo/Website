package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import com.aharoo.registration.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserValidator userValidator;
    private final ApplicationUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserValidator userValidator,
                          ApplicationUserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${message.password}")
    private String passwordMessage;
    @Value("${message.email}")
    private String emailMessage;
    @Value("${message.checkemail}")
    private String checkEmail;
    @Value("${message.emailChanged}")
    private String emailChanged;
    @Value("${message.passwordChanged}")
    private String passwordChanged;
    @Value("${message.passwordRecover}")
    private String passwordRecover;


    @PostMapping("/user-profile/edit-email")
    public ModelAndView editEmail(@AuthenticationPrincipal ApplicationUser user,
                                  Model model,
                                  @RequestParam String oldEmail,
                                  @RequestParam String newEmail){

        if (!oldEmail.equals(user.getEmail()) || !userValidator.isEmailValid(newEmail)) {
            model.addAttribute("emailMessage", emailMessage);
            return new ModelAndView("/user-profile");
        }

        user.setEmail(newEmail);
        userService.updateUser(user);
        model.addAttribute("emailChanged",emailChanged);

        return new ModelAndView("/user-profile");
    }

    @PostMapping("/user-profile/edit-password")
    public ModelAndView editPassword(@AuthenticationPrincipal ApplicationUser user,
                                     Model model,
                                     @RequestParam String email,
                                     @RequestParam String newPassword){
        if (email.isEmpty() || !user.getEmail().equals(email)){
            model.addAttribute("emailMessage",emailMessage);
            return new ModelAndView("/user-profile");
        }
        if (!userValidator.isPasswordValid(newPassword) && newPassword.isEmpty()) {
            model.addAttribute("passwordMessage", passwordMessage);
            return new ModelAndView("/user-profile");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);
        model.addAttribute("passwordChanged",passwordChanged);

        return new ModelAndView("/user-profile");
    }

    @PostMapping("/recover")
    public ModelAndView sendRecoveringEmail(Model model, @RequestParam String email){
        boolean isEmailExists = userService.findByEmail(email).isPresent();
        if (!isEmailExists){
            model.addAttribute("emailMessage",emailMessage);
            return new ModelAndView("/recoveringPassword");
        }

        userService.recoverPassword(email);
        model.addAttribute("passwordRecover",passwordRecover);
        return new ModelAndView("/login");
    }
}
