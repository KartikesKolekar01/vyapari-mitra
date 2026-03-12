package com.vyaparimitra.vyapari_mitra.security;

import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinAuthProvider {

    @Autowired
    private OwnerRepository ownerRepository;

    public boolean authenticate(String mobile, String pin) {
        return ownerRepository.findByMobile(mobile)
                .map(owner -> owner.getPin().equals(pin))
                .orElse(false);
    }

    public Owner getOwnerByMobile(String mobile) {
        return ownerRepository.findByMobile(mobile)
                .orElse(null);
    }

    public boolean validatePin(String pin) {
        // PIN 4 अंकी असावा
        return pin != null && pin.length() == 4 && pin.matches("\\d+");
    }

    public boolean validateMobile(String mobile) {
        // Mobile 10 अंकी असावा
        return mobile != null && mobile.length() == 10 && mobile.matches("\\d+");
    }
}