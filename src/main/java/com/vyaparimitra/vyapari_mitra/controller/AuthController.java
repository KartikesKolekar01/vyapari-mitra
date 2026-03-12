package com.vyaparimitra.vyapari_mitra.controller;

import com.vyaparimitra.vyapari_mitra.dto.LoginRequestDTO;
import com.vyaparimitra.vyapari_mitra.dto.RegisterRequestDTO;
import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  // React साठी
public class AuthController {

    @Autowired
    private OwnerService ownerService;

    // Register - फक्त एकदाच
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            Owner owner = ownerService.register(registerRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "नोंदणी यशस्वी! 🎉");
            response.put("data", owner);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Owner owner = ownerService.login(loginRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "लॉगिन यशस्वी! 🚀");
            response.put("data", owner);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    // Check if owner exists
    @GetMapping("/check-owner")
    public ResponseEntity<?> checkOwner() {
        try {
            boolean exists = ownerService.isOwnerExists();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("exists", exists);

            if (exists) {
                response.put("message", "मालक नोंदणीकृत आहे");
            } else {
                response.put("message", "प्रथम नोंदणी करा");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get owner details
    @GetMapping("/owner-details")
    public ResponseEntity<?> getOwnerDetails() {
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
}