package com.anmory.cloudfile.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午2:47
 */

@Service
public class TextAi extends AbstractAi{
    @Autowired
    AiAss aiAss;
    @Override
    public String getAiResult(String content) {
        return aiAss.getText(content);
    }
}
