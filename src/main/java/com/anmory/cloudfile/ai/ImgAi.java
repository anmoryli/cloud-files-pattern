package com.anmory.cloudfile.ai;
import java.util.Optional;

/**
 * @author Anmory
 * @description 图像AI分析类，用于分析图像内容
 * @date 2025-05-13 下午2:46
 */

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import java.util.Optional;

/**
 * @author Anmory
 * @description 图像AI分析类，用于通过 FastAPI 接口分析图像内容
 * @date 2025-05-13 下午2:46
 */
public class ImgAi extends AbstractAi {
    private static final String API_URL = "http://175.24.205.213:8000/analyze-image";

    @Override
    public String getAiResult(String url) {
        String prompt = "描述一下这张图片";
        try {
            // 验证输入
            if (url == null || url.isBlank()) {
                System.err.println("图像 URL 不能为空");
                return null;
            }
            // 如果 prompt 为空，使用默认值
            String effectivePrompt = (prompt == null || prompt.isBlank()) ? "描述一下这张图片" : prompt;

            // 编码查询参数
            String encodedImageUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);
            String encodedPrompt = URLEncoder.encode(effectivePrompt, StandardCharsets.UTF_8);

            // 构建 URL
            String requestUrl = API_URL + "?image_url=" + encodedImageUrl + "&prompt=" + encodedPrompt;
            System.out.println("请求 URL: " + requestUrl);

            // 创建 HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // 构建 GET 请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .GET()
                    .build();

            // 发送请求
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 检查状态码
            if (response.statusCode() != 200) {
                System.err.println("API 请求失败，状态码: " + response.statusCode() + ", 响应: " + response.body());
                return null;
            }

            // 解析 JSON 响应
            Gson gson = new Gson();
            ResponseModel responseModel = gson.fromJson(response.body(), ResponseModel.class);
            System.out.println("API 响应: " + responseModel.result);

            // 返回结果
            return responseModel.result;

        } catch (Exception e) {
            System.err.println("图像分析失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // 响应模型
    private static class ResponseModel {
        String result;
        String model;
    }
}