package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get the file name of current / original file
        String originalFileName = file.getOriginalFilename();

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        // e.g., spider.jpg, randomId = 1234 --> 1234.jpg or spider.png, randomId = 1234 --> 1234.png
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName; // File.pathSeparator = "/", but it works on every OS unlike "/"

        // Check if path exists and create if not
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();     // make directory or create folder
        }

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Returning file name
        return fileName;
    }

}
