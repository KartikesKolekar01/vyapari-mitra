package com.vyaparimitra.vyapari_mitra.security;

import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OwnerDetailsService implements UserDetailsService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByMobile(mobile)
                .orElseThrow(() -> new UsernameNotFoundException("मोबाईल नंबर सापडला नाही: " + mobile));

        return User.builder()
                .username(owner.getMobile())
                .password(owner.getPin())  // BCrypt encoder नंतर add करू
                .roles("OWNER")
                .build();
    }
}