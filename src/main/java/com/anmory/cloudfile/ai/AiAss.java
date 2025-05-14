package com.anmory.cloudfile.ai;

import com.anmory.cloudfile.model.Files;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午3:51
 */

@Service
public class AiAss {
    @Autowired
    OpenAiChatModel openAiChatModel;
    public String getAss(String content, List<Files> files) {
        String promptProcess = "你需要根据" + files + "里面的内容返回一个包含" + content +"文件的url的地址，" +
                "例如，如果files是这样的：" +
                "[\n" +
                "    {\n" +
                "        \"id\": 10,\n" +
                "        \"userId\": 4,\n" +
                "        \"name\": \"教学智能体.md\",\n" +
                "        \"size\": \"10702\",\n" +
                "        \"type\": \"application/octet-stream\",\n" +
                "        \"path\": \"/usr/local/nginx/files/cloud-files/教学智能体.md\",\n" +
                "        \"createdAt\": \"2025-05-13T00:05:21.000+00:00\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 12,\n" +
                "        \"userId\": 4,\n" +
                "        \"name\": \"嵌入式Linux系统设计实践教程.pdf\",\n" +
                "        \"size\": \"8156264\",\n" +
                "        \"type\": \"application/pdf\",\n" +
                "        \"path\": \"/usr/local/nginx/files/cloud-files/嵌入式Linux系统设计实践教程.pdf\",\n" +
                "        \"createdAt\": \"2025-05-13T00:06:46.000+00:00\"\n" +
                "    }\n" +
                "]" +
                "然后content是这样的：" +
                "帮我分析一下嵌入式linux这个pdf的内容，那么你就应该返回一个内容，格式固定，例如这样：" +
                "http://175.24.205.213:88/usr/local/nginx/files/cloud-files/嵌入式Linux系统设计实践教程.pdf" +
                "你返回的格式必须是类似上面这样拼接的结构" +
                "必须是严格的字符串，不带任何格式，不能用markdown，不准附加其他任何内容" +
                "上面的是个示例，你需要根据用户传入的content进行返回" +
                "，你应该只返回和文档有关的，如果用户问你的是图片或者其他类型，那么你应该说你不能返回该类型";
        return ChatClient.create(openAiChatModel)
                .prompt(promptProcess)
                .user(content)
                .call()
                .content();
    }

    public String getAssImg(String content, List<Files> files) {
        String promptProcess = "你需要根据" + files + "里面的内容返回一个包含" + content +"文件的url的地址，" +
                "例如，如果files是这样的：" +
                "[\n" +
                "    {\n" +
                "        \"id\": 10,\n" +
                "        \"userId\": 4,\n" +
                "        \"name\": \"教学智能体.md\",\n" +
                "        \"size\": \"10702\",\n" +
                "        \"type\": \"application/octet-stream\",\n" +
                "        \"path\": \"/usr/local/nginx/files/cloud-files/教学智能体.md\",\n" +
                "        \"createdAt\": \"2025-05-13T00:05:21.000+00:00\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 12,\n" +
                "        \"userId\": 4,\n" +
                "        \"name\": \"嵌入式Linux系统设计实践教程.jpg\",\n" +
                "        \"size\": \"8156264\",\n" +
                "        \"type\": \"application/pdf\",\n" +
                "        \"path\": \"/usr/local/nginx/files/cloud-files/嵌入式Linux系统设计实践教程.jpg\",\n" +
                "        \"createdAt\": \"2025-05-13T00:06:46.000+00:00\"\n" +
                "    }\n" +
                "]" +
                "然后content是这样的：" +
                "帮我分析一下嵌入式linux这个图片的内容，那么你就应该返回一个内容，格式固定，例如这样：" +
                "http://175.24.205.213:88/usr/local/nginx/files/cloud-files/嵌入式Linux系统设计实践教程.pdf" +
                "你返回的格式必须是类似上面这样拼接的结构" +
                "必须是严格的字符串，不带任何格式，不能用markdown，不准附加其他任何内容" +
                "上面的是个示例，你需要根据用户传入的content进行返回" +
                "，你应该只返回和图片有关的，如果用户问你的是文档或者其他类型，那么你应该说你不能返回该类型";
        return ChatClient.create(openAiChatModel)
                .prompt(promptProcess)
                .user(content)
                .call()
                .content();
    }

    public String getText(String content) {
        String promptProcess = "你是一个有用的智能助手，帮助回答人们的问题，你拥有所有的知识库的知识";
        return ChatClient.create(openAiChatModel)
                .prompt(promptProcess)
                .user(content)
                .call()
                .content();
    }
}
