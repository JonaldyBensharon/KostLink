package com.kostlink.service.token;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UuidTokenGenerator implements TokenGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}