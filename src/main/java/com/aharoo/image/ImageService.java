package com.aharoo.image;

import com.aharoo.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public void addImage(Image image) {
        imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public String checkAndSaveImageToDirectory(MultipartFile file, String uploadPath) {
        String resultFileName = "";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();

        String uuidOfFile = UUID.randomUUID().toString();
        resultFileName = uuidOfFile + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(uploadPath + "/" + resultFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFileName;
    }
}

