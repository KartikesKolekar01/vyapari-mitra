package com.vyaparimitra.vyapari_mitra.service;

import com.vyaparimitra.vyapari_mitra.dto.LoginRequestDTO;
import com.vyaparimitra.vyapari_mitra.dto.RegisterRequestDTO;
import com.vyaparimitra.vyapari_mitra.model.Owner;

public interface OwnerService {

    // Register new owner (फक्त एकदाच)
    Owner register(RegisterRequestDTO registerRequest);

    // Login owner
    Owner login(LoginRequestDTO loginRequest);

    // Check if owner exists
    boolean isOwnerExists();

    // Get owner details
    Owner getOwnerDetails();

    // Update PIN
    Owner updatePin(String oldPin, String newPin);

    // Update shop details
    Owner updateShopDetails(String shopName, String ownerName);

    // ✅ Add this method - Get currently logged in owner
    Owner getCurrentOwner();
}