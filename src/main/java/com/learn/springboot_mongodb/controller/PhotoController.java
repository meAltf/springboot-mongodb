package com.learn.springboot_mongodb.controller;

import com.learn.springboot_mongodb.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/save")
    public String addPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        String id = photoService.addPhoto(image.getOriginalFilename(), image);
        return id;
    }

}
