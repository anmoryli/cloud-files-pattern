from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
from openai import OpenAI
import logging

# 配置日志
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# 初始化 FastAPI 应用
app = FastAPI(title="Image Analysis API", description="API for analyzing images using Dashscope's qwen-vl-plus model")

# 定义响应模型
class ImageAnalysisResponse(BaseModel):
    result: str
    model: str

# 初始化 OpenAI 客户端（Dashscope）
def get_openai_client():
    api_key = os.getenv("DASHSCOPE_API_KEY")
    if not api_key:
        raise ValueError("DASHSCOPE_API_KEY environment variable is not set")
    return OpenAI(
        api_key=api_key,
        base_url="https://dashscope.aliyuncs.com/compatible-mode/v1"
    )

@app.get("/analyze-image", response_model=ImageAnalysisResponse)
async def analyze_image(image_url: str, prompt: str = "描述一下这个图片"):
    """
    Analyze an image using Dashscope's qwen-vl-plus model.
    :param image_url: URL of the image to analyze (query parameter)
    :param prompt: Optional text prompt (query parameter, default: "这是什么")
    :return: ImageAnalysisResponse containing analysis result and model name
    """
    try:
        logger.info(f"Received request: image_url={image_url}, prompt={prompt}")

        # 验证 image_url 是否有效
        if not image_url.startswith(("http://", "https://")):
            logger.error(f"Invalid image URL: {image_url}")
            raise HTTPException(status_code=400, detail="Image URL must start with http:// or https://")

        # 初始化客户端
        client = get_openai_client()

        # 构建 messages
        messages = [
            {
                "role": "user",
                "content": [
                    {"type": "text", "text": prompt},
                    {"type": "image_url", "image_url": {"url": image_url}}
                ]
            }
        ]

        # 发送请求
        completion = client.chat.completions.create(
            model="qwen-vl-plus",
            messages=messages
        )

        # 获取结果
        result = completion.choices[0].message.content
        logger.info(f"Analysis completed: result={result}")

        return ImageAnalysisResponse(
            result=result,
            model=completion.model
        )

    except Exception as e:
        logger.error(f"Error processing request: {str(e)}")
        raise HTTPException(status_code=500, detail=f"Image analysis failed: {str(e)}")

@app.get("/health")
async def health_check():
    """Health check endpoint"""
    return {"status": "healthy"}