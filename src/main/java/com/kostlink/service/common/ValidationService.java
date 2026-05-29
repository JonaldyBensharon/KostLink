package com.kostlink.service.common;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    /* Validasi string tidak boleh null atau kosong */
    public void requireNonEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /* Validasi nilai boolean harus true */
    public void requireTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /* Validasi angka harus positif */
    public void requirePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /* Validasi object tidak boleh null */
    public void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}