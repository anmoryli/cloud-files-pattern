package com.anmory.cloudfile.template;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 上午1:25
 */

public abstract class TemplateMethod {
    public abstract String primitiveOperation1();
    public abstract String primitiveOperation2();
    public abstract String primitiveOperation3();
    public abstract String primitiveOperation4();

    public final void templateMethod() {
        primitiveOperation1();
        primitiveOperation2();
        primitiveOperation3();
        primitiveOperation4();
    }
}
