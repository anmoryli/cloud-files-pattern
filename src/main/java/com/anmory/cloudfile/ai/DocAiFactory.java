package com.anmory.cloudfile.ai;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午2:48
 */

public class DocAiFactory extends AbstractAiFactory{
    @Override
    public DocAi getAi(String type) {
        if (type.equals("douban")) {
            return new DocAi();
        }
        return null;
    }
}
