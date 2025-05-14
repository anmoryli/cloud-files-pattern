package com.anmory.cloudfile.ai;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午2:49
 */

public class ImgAiFactory extends AbstractAiFactory{
    public ImgAi getAi(String type) {
        if ("tongyi".equals(type)) {
            return new ImgAi();
        }
        return null;
    }
}
