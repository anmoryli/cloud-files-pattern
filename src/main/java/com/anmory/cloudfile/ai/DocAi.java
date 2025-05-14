package com.anmory.cloudfile.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author Anmory
 * @description 文档AI分析类，用于从URL或本地文件提取内容并进行分析
 * @date 2025-05-13 下午2:41
 */
public class DocAi extends AbstractAi {

    @Override
    public String getAiResult(String content) {
        System.out.println("开始文档分析");
        try {
            // 判断 content 是否为 URL
            Path filePath;
            if (content.startsWith("http://") || content.startsWith("https://")) {
                // 下载远程文件到临时文件
                filePath = downloadFileFromUrl(content);
            } else {
                // 如果是本地路径，直接使用
                filePath = Paths.get(content);
            }

            // 验证文件是否存在
            if (!Files.exists(filePath)) {
                System.err.println("文件不存在: " + filePath);
                return "";
            }

            // 创建 OpenAI 客户端
            OpenAIClient client = OpenAIOkHttpClient.builder()
                    .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                    .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                    .build();

            // 创建文件上传参数
            FileCreateParams fileParams = FileCreateParams.builder()
                    .file(filePath)
                    .purpose(FilePurpose.of("file-extract"))
                    .build();

            // 上传文件
            FileObject fileObject = client.files().create(fileParams);
            String fileId = fileObject.id();

            // 创建聊天请求
            ChatCompletionCreateParams chatParams = ChatCompletionCreateParams.builder()
                    .addSystemMessage("fileid://" + fileId)
                    .addUserMessage("详细分析一下这个文章的内容")
                    .model("qwen-long")
                    .build();

            // 发送请求并获取响应
            ChatCompletion chatCompletion = client.chat().completions().create(chatParams);

            // 打印响应结果
            System.out.println(chatCompletion);

            // 如果是临时文件，删除
            if (content.startsWith("http://") || content.startsWith("https://")) {
                Files.deleteIfExists(filePath);
            }

            String ret = chatCompletion.choices().get(0).message().content().toString();
            // 把ret前缀的Optional[和最后的]去掉
            ret = ret.substring(ret.indexOf("[") + 1, ret.lastIndexOf("]"));
            return ret;
        } catch (Exception e) {
            System.err.println("AI分析失败: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 从 URL 下载文件到本地临时文件
     * @param fileUrl 文件的 URL 地址
     * @return 本地临时文件的 Path
     * @throws IOException 如果下载或保存文件失败
     */
    private Path downloadFileFromUrl(String fileUrl) throws IOException {
        // 创建临时文件
        String suffix = fileUrl.substring(fileUrl.lastIndexOf('.'));
        Path tempFile = Files.createTempFile("cloudfile_", suffix);

        // 下载文件
        try (InputStream in = new URL(fileUrl).openStream();
             OutputStream out = Files.newOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}