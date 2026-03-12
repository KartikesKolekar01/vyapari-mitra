package com.vyaparimitra.vyapari_mitra.service.impl;

import com.vyaparimitra.vyapari_mitra.dto.LoginRequestDTO;
import com.vyaparimitra.vyapari_mitra.dto.RegisterRequestDTO;
import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.repository.OwnerRepository;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private HttpSession session;  // ✅ Inject HttpSession

    @Override
    public Owner getCurrentOwner() {
        // Get owner from session
        Owner owner = (Owner) session.getAttribute("owner");
        if (owner == null) {
            throw new RuntimeException("कोणताही मालक लॉगिन केलेला नाही! कृपया प्रथम लॉगिन करा.");
        }
        return owner;
    }

    @Override
    public Owner register(RegisterRequestDTO registerRequest) {

        // Check if owner already exists
        if (isOwnerExists()) {
            throw new RuntimeException("मालक आधीच नोंदणीकृत आहे! फक्त एकच मालक असू शकतो.");
        }

        // Validate PIN (4 digits)
        String pin = registerRequest.getPin();
        if (pin == null || pin.length() != 4 || !pin.matches("\\d+")) {
            throw new RuntimeException("पिन ४ अंकी असणे आवश्यक आहे!");
        }

        // Validate mobile (10 digits)
        String mobile = registerRequest.getMobile();
        if (mobile == null || mobile.length() != 10 || !mobile.matches("\\d+")) {
            throw new RuntimeException("मोबाईल नंबर १० अंकी असणे आवश्यक आहे!");
        }

        // Create new owner
        Owner owner = new Owner();
        owner.setShopName(registerRequest.getShopName());
        owner.setOwnerName(registerRequest.getOwnerName());
        owner.setMobile(registerRequest.getMobile());
        owner.setPin(registerRequest.getPin());

        return ownerRepository.save(owner);
    }

    @Override
    public Owner login(LoginRequestDTO loginRequest) {

        String mobile = loginRequest.getMobile();
        String pin = loginRequest.getPin();

        // Find owner by mobile
        Owner owner = ownerRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("मोबाईल नंबर सापडला नाही!"));

        // Check PIN
        if (!owner.getPin().equals(pin)) {
            throw new RuntimeException("चुकीचा पिन!");
        }

        // ✅ Store owner in session
        session.setAttribute("owner", owner);

        return owner;
    }

    @Override
    public boolean isOwnerExists() {
        return ownerRepository.count() > 0;
    }

    @Override
    public Owner getOwnerDetails() {
        // Return current logged in owner
        return getCurrentOwner();
    }

    @Override
    public Owner updatePin(String oldPin, String newPin) {

        Owner owner = getCurrentOwner();

        // Verify old pin
        if (!owner.getPin().equals(oldPin)) {
            throw new RuntimeException("जुना पिन चुकीचा आहे!");
        }

        // Validate new pin
        if (newPin == null || newPin.length() != 4 || !newPin.matches("\\d+")) {
            throw new RuntimeException("नवीन पिन ४ अंकी असणे आवश्यक आहे!");
        }

        owner.setPin(newPin);
        Owner updatedOwner = ownerRepository.save(owner);

        // Update session
        session.setAttribute("owner", updatedOwner);

        return updatedOwner;
    }

    @Override
    public Owner updateShopDetails(String shopName, String ownerName) {

        Owner owner = getCurrentOwner();

        if (shopName != null && !shopName.trim().isEmpty()) {
            owner.setShopName(shopName);
        }

        if (ownerName != null && !ownerName.trim().isEmpty()) {
            owner.setOwnerName(ownerName);
        }

        Owner updatedOwner = ownerRepository.save(owner);

        // Update session
        session.setAttribute("owner", updatedOwner);

        return updatedOwner;
    }
}