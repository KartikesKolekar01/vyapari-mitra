package com.vyaparimitra.vyapari_mitra.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s %s सह सापडला नाही: '%s'", resourceName, fieldName, fieldValue));
    }
}