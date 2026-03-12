package com.vyaparimitra.vyapari_mitra.service.impl;

import com.vyaparimitra.vyapari_mitra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) {

        // Create upload directory if not exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Store file
        Path targetLocation = Paths.get(uploadDir + File.separator + fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("फाइल सेव करताना त्रुटी: " + e.getMessage());
        }
    }

    @Override
    public byte[] loadFile(String fileName) {

        try {
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("फाइल लोड करताना त्रुटी: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String fileName) {

        try {
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("फाइल डिलीट करताना त्रुटी: " + e.getMessage());
        }
    }

    @Override
    public String getFilePath(String fileName) {
        return uploadDir + File.separator + fileName;
    }
}