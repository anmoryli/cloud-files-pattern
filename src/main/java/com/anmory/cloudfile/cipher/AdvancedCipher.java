package com.anmory.cloudfile.cipher;

/**
 * @author Anmory
 * @description 高级加密器，基于装饰者模式，在原有加密基础上应用模运算变换
 * @date 2025-05-13 下午9:27
 */
public class AdvancedCipher extends CipherDecorator {
    public AdvancedCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    public String encrypt(String plainText) {
        String encrypted = super.encrypt(plainText); // 调用父类的加密方法
        return mod(encrypted); // 应用模运算变换
    }

    /**
     * 对输入字符串应用模运算变换
     * 对每个字母字符，转换为 0-25 范围，应用模 26 运算后转换回字符
     * 非字母字符保持不变
     *
     * @param plainText 输入字符串
     * @return 变换后的字符串
     */
    public String mod(String plainText) {
        StringBuilder result = new StringBuilder();
        final int MOD = 26; // 模 26，适用于字母表 a-z

        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                // 将字符映射到 0-25，应用模运算，再转换回字符
                int index = c - base; // 当前字符相对于 base 的偏移
                index = index % MOD; // 模运算
                result.append((char) (base + index));
            } else {
                result.append(c); // 非字母字符不变
            }
        }

        return result.toString();
    }
}