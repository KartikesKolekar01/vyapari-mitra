package com.vyaparimitra.vyapari_mitra.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    // Store file
    String storeFile(MultipartFile file);

    // Load file
    byte[] loadFile(String fileName);

    // Delete file
    boolean deleteFile(String fileName);

    // Get file path
    String getFilePath(String fileName);
}