package com.vyaparimitra.vyapari_mitra.exception;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {

    private Map<String, String> errors;
    private List<String> errorList;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, List<String> errorList) {
        super(message);
        this.errorList = errorList;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}