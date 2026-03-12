package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import com.vyaparimitra.vyapari_mitra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "*")
public class SettingsController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private FileStorageService fileStorageService;

    // Get shop details
    @GetMapping("/shop")
    public ResponseEntity<?> getShopDetails() {
        try {
            Owner owner = ownerService.getOwnerDetails();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", owner);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // Update shop details
    @PutMapping("/shop")
    public ResponseEntity<?> updateShopDetails(
            @RequestParam(required = false) String shopName,
            @RequestParam(required = false) String ownerName) {
        try {
            Owner owner = ownerService.updateShopDetails(shopName, ownerName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "दुकानाची माहिती अद्यावत केली! ✅");
            response.put("data", owner);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Update PIN
    @PutMapping("/pin")
    public ResponseEntity<?> updatePin(
            @RequestParam String oldPin,
            @RequestParam String newPin) {
        try {
            Owner owner = ownerService.updatePin(oldPin, newPin);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "पिन यशस्वीरित्या बदलला! 🔐");
            response.put("data", owner);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Get storage info
    @GetMapping("/storage")
    public ResponseEntity<?> getStorageInfo() {
        try {
            Map<String, Object> storageInfo = new HashMap<>();

            // You can implement actual storage size calculation
            storageInfo.put("totalPhotos", 0); // TODO: Count photos
            storageInfo.put("storageUsed", "0 MB");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", storageInfo);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Backup data
    @PostMapping("/backup")
    public ResponseEntity<?> createBackup() {
        try {
            // TODO: Implement backup functionality
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "बॅकअप यशस्वीरित्या तयार झाला! 💾");
            response.put("backupTime", java.time.LocalDateTime.now().toString());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}