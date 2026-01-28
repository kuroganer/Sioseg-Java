package com.sioseg.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    
    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    
    @Override
    public String encode(CharSequence rawPassword) {
        return bcryptEncoder.encode(rawPassword);
    }
    
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("DEBUG: Tentando autenticar com senha: " + rawPassword);
        System.out.println("DEBUG: Hash no banco: " + encodedPassword);
        
        // Converte $2y$ para $2a$ se necessário (compatibilidade PHP)
        String normalizedHash = encodedPassword;
        if (encodedPassword.startsWith("$2y$")) {
            normalizedHash = "$2a$" + encodedPassword.substring(4);
            System.out.println("DEBUG: Hash normalizado: " + normalizedHash);
        }
        
        // Usa BCrypt padrão para verificar
        boolean result = bcryptEncoder.matches(rawPassword, normalizedHash);
        System.out.println("DEBUG: Resultado da verificação: " + result);
        return result;
    }
}