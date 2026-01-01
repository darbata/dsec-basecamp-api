package com.dsec.collab.adaptor.repository.jpa.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class TokenEncryptionService {



    private final TextEncryptor encryptor;

    public TokenEncryptionService(
            @Value("${encryption.token.password}") String tokenPassword,
            @Value("${encryption.token.salt}") String tokenSalt
    ) {
        this.encryptor = Encryptors.text(tokenPassword, tokenSalt);
    }

    public String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public String decrypt(String cipherText) {
        return encryptor.decrypt(cipherText);
    }
}