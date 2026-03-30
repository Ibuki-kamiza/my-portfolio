package com.example.my_portfolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public String uploadProfileImage(MultipartFile file) throws IOException {
        return uploadImage(file, "profile");
    }

    public String uploadBlogImage(MultipartFile file) throws IOException {
        return uploadImage(file, "blog");
    }

    private String uploadImage(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null
            ? originalFilename.substring(originalFilename.lastIndexOf("."))
            : ".jpg";

        String filename = folder + "_" + UUID.randomUUID() + extension;
        Path uploadPath = Paths.get(uploadDir, folder);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return "/uploads/" + folder + "/" + filename;
    }
}