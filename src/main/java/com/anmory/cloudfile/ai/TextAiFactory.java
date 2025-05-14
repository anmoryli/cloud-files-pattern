package com.anmory.cloudfile.ai;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午2:50
 */

public class TextAiFactory extends AbstractAiFactory{
    @Override
    public TextAi getAi(String type) {
        if (type.equals("deepseek")) {
            return new TextAi();
        }
        return null;
    }
}
