package com.vyaparimitra.vyapari_mitra.model;

// Replace this:
// import javax.persistence.*;

// With this:
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(unique = true)
    private String mobile;

    private String pin;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    // Constructors
    public Owner() {}

    public Owner(String shopName, String ownerName, String mobile, String pin) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.mobile = mobile;
        this.pin = pin;
        this.registeredAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}