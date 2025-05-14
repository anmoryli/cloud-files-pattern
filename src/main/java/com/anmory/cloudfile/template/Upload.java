package com.anmory.cloudfile.template;

import com.anmory.cloudfile.model.Users;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 上午1:26
 */

public class Upload extends TemplateMethod{
    @Override
    public String primitiveOperation1() {
        return "验证用户成功";
    }

    @Override
    public String primitiveOperation2() {
        return "加密文件成功";
    }

    @Override
    public String primitiveOperation3() {
        return "存储文件成功";
    }

    @Override
    public String primitiveOperation4() {
        return "存储数据库成功";
    }
}
