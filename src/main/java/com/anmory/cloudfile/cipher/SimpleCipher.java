package com.anmory.cloudfile.cipher;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午9:16
 */

public class SimpleCipher extends Cipher{
    @Override
    public String encrypt(String plainText) {
        StringBuilder encrypted = new StringBuilder();
        int shift = 6;

        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) (base + ((c - base + shift) % 26)));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}
