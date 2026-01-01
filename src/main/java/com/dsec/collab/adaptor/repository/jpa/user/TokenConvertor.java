package com.dsec.collab.adaptor.repository.jpa.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter
public class TokenConvertor implements AttributeConverter<String, String> {
    // take a column and convert it using token encryptor
    private TokenEncryptionService encryptionService;

    public TokenConvertor(TokenEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String convertToDatabaseColumn(String token) {
        return (token == null) ? null : encryptionService.encrypt(token);
    }

    @Override
    public String convertToEntityAttribute(String dbTokenData) {
        return (dbTokenData == null) ? null : encryptionService.decrypt(dbTokenData);
    }
}