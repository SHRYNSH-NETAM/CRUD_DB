package com.SHRYNSH.crudAppDB.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGeneratorService {
    public UUID generateId() {
        return UUID.randomUUID();
    }

    public long uuidToLong(UUID uuid) {
        return uuid.getMostSignificantBits();
    }

    public String uuidToString(UUID uuid) {
        return uuid.toString();
    }
}
