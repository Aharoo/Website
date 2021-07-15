package com.aharoo.controller;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import com.aharoo.image.Image;
import com.aharoo.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ApplicationUserService userService;
    private final ImageService imageService;

    @Autowired
    public FileUploadController(ApplicationUserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @PostMapping("/user-profile")
    public ModelAndView uploadProfileImage(@AuthenticationPrincipal ApplicationUser user, @RequestParam("file") MultipartFile file){

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            if (user.getFilename() != null && !user.getFilename().isEmpty()) {
                File fileToDelete = new File(uploadPath + user.getFilename());
                fileToDelete.delete();
            }

            String resultFileName = imageService.checkAndSaveImageToDirectory(file, uploadPath);
            ApplicationUser savedUser = userService.findById(user.getUser_id()).orElseThrow(()
                    -> new UsernameNotFoundException("User was not found"));
            savedUser.setFilename(resultFileName);
            userService.updateUser(savedUser);
        }
        return new ModelAndView("/user-profile");
    }

    @PostMapping("/")
    public ModelAndView uploadImageToMainView(@RequestParam("file") MultipartFile file,@RequestParam(required = false) String description){
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String resultFileName = imageService.checkAndSaveImageToDirectory(file, uploadPath);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            Image image = new Image();
            image.setFilename(resultFileName);
            image.setTimeOfCreation(dateFormat.format(new Date()));
            if (description.isEmpty())
                image.setDescription("Without description");
            else
                image.setDescription(description);
            imageService.addImage(image);
        }
        return new ModelAndView("/index");
    }
}
