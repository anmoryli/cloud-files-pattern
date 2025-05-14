package com.anmory.cloudfile.cipher;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午9:18
 */

public abstract class CipherDecorator extends Cipher{
    private Cipher cipher;
    public CipherDecorator(Cipher cipher) {
        this.cipher = cipher;
    }

    public String encrypt(String plainText) {
        return cipher.encrypt(plainText);
    }
}
