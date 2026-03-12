package com.vyaparimitra.vyapari_mitra.dto;


public class LoginRequestDTO {

    private String mobile;
    private String pin;

    // Constructors
    public LoginRequestDTO() {}

    public LoginRequestDTO(String mobile, String pin) {
        this.mobile = mobile;
        this.pin = pin;
    }

    // Getters and Setters
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