package com.learn.springboot_mongodb.service;

import com.learn.springboot_mongodb.collection.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    String addPhoto(String originalFilename, MultipartFile image) throws IOException;

    Photo getPhoto(String id);
}
