package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.service.FileStorageService;
import com.vyaparimitra.vyapari_mitra.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TransactionService transactionService;

    // Upload single file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "कृपया फाइल निवडा!");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            // Check file type (only images)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "कृपया फक्त इमेज फाइल अपलोड करा (JPEG, PNG, etc.)!");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            // Check file size (max 10MB)
            if (file.getSize() > 10 * 1024 * 1024) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "फाइल साइझ १०MB पेक्षा कमी असावी!");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            // Store file
            String fileName = fileStorageService.storeFile(file);

            // Generate URL to access the file
            String fileUrl = "/api/files/view/" + fileName;

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "फाइल यशस्वीरित्या अपलोड झाली! 📸");
            response.put("fileName", fileName);
            response.put("fileUrl", fileUrl);
            response.put("fileSize", file.getSize());
            response.put("contentType", contentType);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "फाइल अपलोड करताना त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Upload file for specific transaction
    @PostMapping("/upload/transaction/{transactionId}")
    public ResponseEntity<?> uploadTransactionFile(
            @PathVariable Long transactionId,
            @RequestParam("file") MultipartFile file) {
        try {
            // First upload the file
            ResponseEntity<?> uploadResponse = uploadFile(file);

            if (!uploadResponse.getStatusCode().is2xxSuccessful()) {
                return uploadResponse;
            }

            // Get the filename from response
            Map<String, Object> uploadResult = (Map<String, Object>) uploadResponse.getBody();
            String fileName = (String) uploadResult.get("fileName");

            // Update transaction with photo path
            var updatedTransaction = transactionService.updateTransactionPhoto(transactionId, fileName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "फाइल अपलोड झाली आणि व्यवहारात जोडली गेली! ✅");
            response.put("fileName", fileName);
            response.put("transaction", updatedTransaction);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // View/Download file
    @GetMapping("/view/{fileName:.+}")
    public ResponseEntity<?> viewFile(@PathVariable String fileName) {
        try {
            byte[] fileData = fileStorageService.loadFile(fileName);

            // Determine content type
            MediaType mediaType;
            if (fileName.toLowerCase().endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (fileName.toLowerCase().endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            } else {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(fileData);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "फाइल उघडताना त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Delete file
    @DeleteMapping("/delete/{fileName:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        try {
            boolean deleted = fileStorageService.deleteFile(fileName);

            if (deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "फाइल यशस्वीरित्या हटवली गेली! 🗑️");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "फाइल हटवता आली नाही!");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all files for a transaction
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getTransactionFiles(@PathVariable Long transactionId) {
        try {
            var transaction = transactionService.getTransactionById(transactionId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("transactionId", transactionId);

            if (transaction.getPhotoPath() != null && !transaction.getPhotoPath().isEmpty()) {
                String fileUrl = "/api/files/view/" + transaction.getPhotoPath();
                response.put("hasPhoto", true);
                response.put("fileName", transaction.getPhotoPath());
                response.put("fileUrl", fileUrl);
            } else {
                response.put("hasPhoto", false);
                response.put("message", "या व्यवहारासाठी कोणतीही फाइल नाही");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Multiple files upload (for future use)
    @PostMapping("/upload/multiple")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            Map<String, Object> results = new HashMap<>();
            results.put("success", true);
            results.put("totalFiles", files.length);

            String[] uploadedFiles = new String[files.length];
            String[] fileUrls = new String[files.length];

            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                // Check file type
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    continue;
                }

                // Check file size
                if (file.getSize() > 10 * 1024 * 1024) {
                    continue;
                }

                // Store file
                String fileName = fileStorageService.storeFile(file);
                uploadedFiles[i] = fileName;
                fileUrls[i] = "/api/files/view/" + fileName;
            }

            results.put("uploadedFiles", uploadedFiles);
            results.put("fileUrls", fileUrls);

            return new ResponseEntity<>(results, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "त्रुटी: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}