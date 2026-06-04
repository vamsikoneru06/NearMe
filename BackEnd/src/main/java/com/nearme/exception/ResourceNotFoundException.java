package com.nearme.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, Long id) {
        super(resourceType + " with id " + id + " not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
