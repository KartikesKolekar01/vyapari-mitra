package com.vyaparimitra.vyapari_mitra.dto;

public class RegisterRequestDTO {

    private String shopName;
    private String ownerName;
    private String mobile;
    private String pin;

    // Constructors
    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String shopName, String ownerName, String mobile, String pin) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.mobile = mobile;
        this.pin = pin;
    }

    // Getters and Setters
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
}