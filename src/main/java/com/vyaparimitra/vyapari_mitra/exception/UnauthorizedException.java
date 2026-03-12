package com.vyaparimitra.vyapari_mitra.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("कृपया प्रथम लॉगिन करा!");
    }
}