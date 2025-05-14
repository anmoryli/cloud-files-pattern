package com.anmory.cloudfile.cipher;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午9:19
 */

public class ComplexCipher extends CipherDecorator{
    public ComplexCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    public String encrypt(String plainText) {
        String encrypted = super.encrypt(plainText);
        return reverse(encrypted);
    }

    public String reverse(String plainText) {
        StringBuilder reversed = new StringBuilder();
        for (int i = plainText.length() - 1; i >= 0; i--) {
            reversed.append(plainText.charAt(i));
        }
        return reversed.toString();
    }
}
